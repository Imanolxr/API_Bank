package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.controller;

import com.SaintPatrick.Banco.Saint.Patrick.application.service.AuthService;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.security.JwtUtil;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.Login.CardLoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CardLoginRequest loginRequest){
        boolean authenticated = authService.authenticate(loginRequest.getCardNumber(), loginRequest.getPin());

        if (authenticated){
            String token = jwtUtil.generateToken(loginRequest.getCardNumber());
            return ResponseEntity.ok().body(Map.of("token", token));
        } else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Número de tarjeta o PIN incorrecto");
        }

    }
}
