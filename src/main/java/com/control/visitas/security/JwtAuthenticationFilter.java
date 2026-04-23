package com.control.visitas.security;

import com.control.visitas.dtos.ErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // ↑ OncePerRequestFilter garantiza que este filtro se ejecuta
    //   UNA SOLA VEZ por petición, sin importar redirecciones internas

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,   // La petición HTTP entrante
            @NonNull HttpServletResponse response, // La respuesta HTTP
            @NonNull FilterChain filterChain        // La cadena de filtros siguiente
    ) throws ServletException, IOException {

        // ── PASO A: Extraer el header Authorization ───────────────────
        final String authHeader = request.getHeader("Authorization");
        // El cliente envía: Authorization: Bearer eyJhbGci...

        // Si no hay header o no empieza con "Bearer ", dejamos pasar sin autenticar
        // Spring Security manejará el acceso según la configuración de SecurityConfig
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // ← Importante: salimos del método aquí
        }

        // ── PASO B: Extraer el token (quitamos "Bearer " del inicio) ──
        final String jwt = authHeader.substring(7);
        // "Bearer eyJhbGci..." → "eyJhbGci..."
        //  0123456 7

        try{
        // ── PASO C: Extraer el username (email) del token ─────────────
        final String userEmail = jwtService.extractUsername(jwt);

        // ── PASO D: Autenticar si hay email y no está ya autenticado ──
        // SecurityContextHolder.getContext().getAuthentication() != null
        // significa que ya fue autenticado en este request (evitamos doble trabajo)
        if (userEmail != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            // Buscamos el usuario en la base de datos
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Verificamos que el token sea válido para este usuario
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Creamos el objeto de autenticación que Spring Security entiende
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,         // El principal (el usuario)
                                null,                // Las credenciales (null porque ya validamos con JWT)
                                userDetails.getAuthorities() // Los roles/permisos del usuario
                        );

                // Agregamos detalles adicionales de la petición (IP, session, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // ── PASO E: Registrar la autenticación en el contexto ──
                // Este es el momento donde Spring Security "sabe" que el usuario está autenticado
                // Cualquier @PreAuthorize o hasRole() que venga después lo usará
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // ── PASO F: Continuar con el siguiente filtro/controller ───────
        filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            // ↑ Token expirado — capturamos específicamente para dar mensaje claro
            sendErrorResponse(response, request, HttpStatus.UNAUTHORIZED,
                    "El access token expiró. Usa el refresh token.");

        } catch (SignatureException ex) {
            // ↑ Firma inválida — token alterado o firmado con otra clave
            sendErrorResponse(response, request, HttpStatus.UNAUTHORIZED,
                    "Token inválido: firma incorrecta.");

        } catch (MalformedJwtException ex) {
            // ↑ Token con formato roto — no tiene 3 partes, no es Base64, etc.
            sendErrorResponse(response, request, HttpStatus.UNAUTHORIZED,
                    "Token con formato inválido.");

        } catch (Exception ex) {
            // ↑ Cualquier otro error JWT no contemplado
            sendErrorResponse(response, request, HttpStatus.UNAUTHORIZED,
                    "Error al procesar el token.");
        }
    }

    private void sendErrorResponse(HttpServletResponse response,
                                   HttpServletRequest request,
                                   HttpStatus status,
                                   String message) throws IOException {
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .errorCode(status.toString())
                .message(message)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        // ↑ Serializa tu ErrorResponse a JSON reutilizando el mismo DTO del handler
    }
}


