package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.controller;

import com.SaintPatrick.Banco.Saint.Patrick.application.service.AuthService;
import com.SaintPatrick.Banco.Saint.Patrick.application.service.CardService;
import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Card;
import com.SaintPatrick.Banco.Saint.Patrick.domain.model.User;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.input.CardResponseMapper;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.user.UserRequestMapper;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.security.JwtUtil;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.Login.CardLoginRequest;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.card.CardResponseDTO;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.user.UserRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final CardService cardServ;
    private final UserRequestMapper userMapper;
    private final CardResponseMapper cardResponseMapper;

    public AuthController(AuthService authService, JwtUtil jwtUtil, CardService cardServ, UserRequestMapper userMapper, CardResponseMapper cardResponseMapper) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.cardServ = cardServ;
        this.userMapper = userMapper;
        this.cardResponseMapper = cardResponseMapper;
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

    @GetMapping("/me")
    public ResponseEntity<?> getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedCardNumber = auth.getName();
        Card card = cardServ.findByCardNumber(loggedCardNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarjeta no encontrada"));

        User cardUser = card.getUser();
        UserRequestDTO  response = userMapper.toDto(cardUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/card")
    public ResponseEntity <?> getCard(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedCardNumber = auth.getName();
        Card card = cardServ.findByCardNumber(loggedCardNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarjeta no encontrada"));
        CardResponseDTO response = cardResponseMapper.toResponse(card);
        return ResponseEntity.ok(response);

    }
}
