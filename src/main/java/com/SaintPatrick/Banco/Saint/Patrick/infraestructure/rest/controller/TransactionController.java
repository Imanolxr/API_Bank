package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.controller;

import com.SaintPatrick.Banco.Saint.Patrick.application.service.TransactionService;
import com.SaintPatrick.Banco.Saint.Patrick.domain.exception.UnauthorizedAccessException;
import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Transaction;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.input.TransactionDTOMapper;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.transaction.NewTransactionDTO;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.transaction.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionServ;
    private final TransactionDTOMapper mapper;

    public TransactionController(TransactionService transactionServ, TransactionDTOMapper mapper) {
        this.transactionServ = transactionServ;
        this.mapper = mapper;
    }


    @Operation(summary = "Crear una nueva transacción", description = "Realiza una transferencia entre dos tarjetas.")
    @PostMapping("/new")
    public ResponseEntity<TransactionDTO> newTransaction(@RequestBody NewTransactionDTO dto){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedCardNumber = auth.getName();


        Transaction transaction = transactionServ.createTransaction(dto.getDestinyCardNumber(), dto.getAmount(), loggedCardNumber);

        return ResponseEntity.ok(mapper.toDTO(transaction,loggedCardNumber));
    }

    @Operation(summary = "Mostrar Listado de Transacciones", description = "Muestra las Transacciones hechas en el ultimo mes, o en el mes que se le pase como parametro.")
    @GetMapping("/monthList")
    public ResponseEntity<List<TransactionDTO>> getMonthList(@RequestParam(value = "month", required = false) Integer month){

        if(month == null){
            month = LocalDate.now().getMonthValue();
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cardNumber = auth.getName();

        List<Transaction> monthList = transactionServ.getMonthList(month, cardNumber);

        List<TransactionDTO> response = monthList.stream().map(transaction -> mapper.toDTO(transaction,cardNumber)).toList();
        return ResponseEntity.ok(response);
    }
}
