package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.exception;

import com.SaintPatrick.Banco.Saint.Patrick.domain.exception.InvalidCardException;
import com.SaintPatrick.Banco.Saint.Patrick.domain.exception.NotFoundException;
import com.SaintPatrick.Banco.Saint.Patrick.domain.exception.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex, HttpServletRequest request){
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex, HttpServletRequest request) {
        return buildResponse("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI());
    }

    @ExceptionHandler(InvalidCardException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCard(InvalidCardException ex, HttpServletRequest request){
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedAccessException ex, HttpServletRequest request){
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN, request.getRequestURI());
    }


    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status, String path) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);
        return new ResponseEntity<>(body, status);
    }
}
