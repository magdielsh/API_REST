package com.control.visitas.security;

import com.control.visitas.dtos.AuthRequestDTO;
import com.control.visitas.dtos.AuthResponseDTO;
import com.control.visitas.dtos.RegisterRequestDTO;
import com.control.visitas.exceptions.BusinessException;
import com.control.visitas.exceptions.EmailAlreadyExistsException;
import com.control.visitas.models.entities.RefreshToken;
import com.control.visitas.models.entities.Role;
import com.control.visitas.models.entities.UserEntity;
import com.control.visitas.models.entities.VerificationToken;
import com.control.visitas.models.enums.ERole;
import com.control.visitas.repository.UserRepository;
import com.control.visitas.repository.VerificationTokenRepository;
import com.control.visitas.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO login(AuthRequestDTO request) {
        // authenticate() hace el trabajo completo
        // 1. Carga el usuario por email (via UserDetailsService)
        // 2. Compara el password con BCrypt
        // 3. Si falla → lanza BadCredentialsException automáticamente
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Si llegamos aquí, las credenciales son correctas
        // Cargamos el usuario de la BD para generar los tokens
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Generamos el Access Token (JWT, 15 minutos)
        String accessToken = jwtService.generateToken(user);

        // Generamos y guardamos el Refresh Token en BD (UUID, 7 días)
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public AuthResponseDTO refreshToken(String requestRefreshToken) {
        // Buscamos el refresh token en BD
        return refreshTokenService.findByToken(requestRefreshToken)
                // Verificamos que no haya expirado (si expiró, lanza excepción)
                .map(refreshTokenService::verifyExpiration)
                // Obtenemos el usuario dueño del token
                .map(RefreshToken::getUsers)
                // Generamos un nuevo Access Token para ese usuario
                .map(user -> {
                    String newAccessToken = jwtService.generateToken(user);
                    return AuthResponseDTO.builder()
                            .accessToken(newAccessToken)
                            // Devolvemos el MISMO refresh token (no lo renovamos)
                            .refreshToken(requestRefreshToken)
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Refresh token no encontrado en BD"));
    }

    public void logout(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Eliminamos el refresh token de BD — el usuario queda desconectado
        refreshTokenService.deleteByUser(user);
        // El Access Token no podemos invalidarlo (es stateless)
        // Por eso le damos duración corta (15 min) — expira solo
    }

    // ─────────────────────────────────────────────────────────────────
// REGISTRO
// ─────────────────────────────────────────────────────────────────
    public String register(RegisterRequestDTO request) {

        // 1. Verificar que el email no esté ya registrado
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(request.getEmail());
            // ↑ GlobalExceptionHandler lo captura → 409 Conflict
        }

        Set<Role> role = new HashSet<>();
        role.add(Role.builder().name(ERole.valueOf("USER")).build());

        // 2. Crear el usuario con enabled=false
        UserEntity user = UserEntity.builder()
                .username(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(role)
                // ↑ Por defecto todos los registrados son USER
                .enabled(false)
                // ↑ No puede hacer login hasta verificar el email
                .build();

        userRepository.save(user);

        // 3. Crear el token de verificación
        VerificationToken verificationToken = VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(Instant.now().plus(24, ChronoUnit.HOURS))
                // ↑ El link del email expira en 24 horas
                .build();

        verificationTokenRepository.save(verificationToken);

        // 4. Enviar el email con el link
        emailService.sendVerificationEmail(user.getEmail(), verificationToken.getToken());

        return "Registro exitoso. Revisa tu email para activar tu cuenta.";
    }

    // ─────────────────────────────────────────────────────────────────
// VERIFICACIÓN DE EMAIL
// ─────────────────────────────────────────────────────────────────
    @Transactional
    public String verifyEmail(String token) {

        // 1. Buscar el token en BD
        VerificationToken verificationToken = verificationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new BusinessException(
                        "Token de verificación inválido o ya fue usado."));

        // 2. Verificar que no haya expirado
        if (verificationToken.getExpiryDate().isBefore(Instant.now())) {
            verificationTokenRepository.delete(verificationToken);
            // ↑ Limpiamos el token expirado de la BD
            throw new BusinessException(
                    "El enlace de verificación expiró. Solicita uno nuevo.");
        }

        // 3. Activar la cuenta del usuario
        UserEntity user = verificationToken.getUser();
        user.setEnabled(true);
        // ↑ Ahora isEnabled() retorna true → Spring Security permite el login
        userRepository.save(user);

        // 4. Eliminar el token — ya cumplió su propósito
        verificationTokenRepository.delete(verificationToken);

        return "Cuenta verificada exitosamente. Ya puedes iniciar sesión.";
    }

    // ─────────────────────────────────────────────────────────────────
    // REENVIO DE EMAIL
    // ─────────────────────────────────────────────────────────────────
    @Transactional
    public String resendVerificationEmail(String email) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con Email: " + email));

        if (user.isEnabled()) {
            throw new BusinessException("Esta cuenta ya está verificada.");
        }

        verificationTokenRepository.deleteByUser(user);

        // 4. Crear nuevo token con 24h de vigencia
        VerificationToken newToken = VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(Instant.now().plus(24, ChronoUnit.HOURS))
                .build();

        verificationTokenRepository.save(newToken);

        emailService.sendVerificationEmail(user.getEmail(), newToken.getToken());

        return "Email de verificación reenviado. Revisa tu bandeja de entrada.";
    }
}
