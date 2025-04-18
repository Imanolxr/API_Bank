package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.controller;

import com.SaintPatrick.Banco.Saint.Patrick.application.service.CardService;
import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Card;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.input.CardResponseMapper;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.card.CardRequestMapper;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.card.CardRequestDTO;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.card.CardResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/card")
public class CardController {
    private final CardService cardServ;
    private final CardResponseMapper cardResponseMapper;
    private final CardRequestMapper dtoMapper;

    public CardController(CardService cardServ, CardResponseMapper cardResponseMapper, CardRequestMapper dtoMapper) {
        this.cardServ = cardServ;
        this.cardResponseMapper = cardResponseMapper;
        this.dtoMapper = dtoMapper;
    }


    @PostMapping("/new")
    public ResponseEntity<Void> newCard(@RequestBody CardRequestDTO newCardDTO){
        Card card = dtoMapper.toModel(newCardDTO);
        cardServ.newCard(card);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<CardResponseDTO> getCardByNumber(@PathVariable String cardNumber){
        Optional<Card> card = cardServ.findByCardNumber(cardNumber);

        // Si la tarjeta existe, se mapea y se retorna el DTO
        return ResponseEntity.ok(cardResponseMapper.toResponse(card.get()));
    }
}
