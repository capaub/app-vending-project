package org.capaub.security.controller;

import org.capaub.security.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> jwtToken(
            @RequestParam String grantType,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam boolean withRefreshToken,
            @RequestParam(required = false) String refreshToken) {

        try {
            Map<String, String> tokens = authService
                    .authenticate(
                            grantType,
                            username,
                            password,
                            withRefreshToken,
                            refreshToken);
            return new ResponseEntity<>(tokens, HttpStatus.OK);

        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(Map.of(
                    "errorMessage",
                    e.getMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
    }
}