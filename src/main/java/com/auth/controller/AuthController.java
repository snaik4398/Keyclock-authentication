package com.auth.controller;

import com.auth.dto.AuthRequest;
import com.auth.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KeycloakService keycloakService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody AuthRequest authRequest) {
        AccessTokenResponse token = keycloakService.getToken(
            authRequest.getUsername(),
            authRequest.getPassword()
        );
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");
        boolean isValid = keycloakService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }
}