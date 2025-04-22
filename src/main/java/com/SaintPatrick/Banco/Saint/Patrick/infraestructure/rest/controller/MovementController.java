package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.controller;

import com.SaintPatrick.Banco.Saint.Patrick.application.service.TransactionService;
import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Transaction;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.input.TransactionDTOMapper;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.transaction.TransactionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movements")
public class MovementController {
    private final TransactionService transactionServ;
    private final TransactionDTOMapper mapper;

    public MovementController(TransactionService transactionServ, TransactionDTOMapper mapper) {
        this.transactionServ = transactionServ;
        this.mapper = mapper;
    }


    @GetMapping("/all")
    public ResponseEntity<List<TransactionDTO>> getAllMovements(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cardNumber = auth.getName();

        List<Transaction> allMovements = transactionServ.getAllMovements(cardNumber);
        List<TransactionDTO> response = allMovements.stream().map(transaction -> mapper.toDTO(transaction,cardNumber)).toList();

        return ResponseEntity.ok(response);
    }
}
