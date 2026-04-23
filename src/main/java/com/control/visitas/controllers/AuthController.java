package com.control.visitas.controllers;

import com.control.visitas.dtos.AuthRequestDTO;
import com.control.visitas.dtos.AuthResponseDTO;
import com.control.visitas.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // Recibe: { "refreshToken": "uuid-del-token" }
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDTO> refreshToken(
            @RequestBody Map<String, String> request
    ) {
        return ResponseEntity.ok(
                authService.refreshToken(request.get("refreshToken"))
        );
    }

    // POST /api/auth/logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestParam String email
    ) {
        authService.logout(email);
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }
}
