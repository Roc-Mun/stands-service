package com.food.demo.exception;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException e) {

        var fieldError = e.getBindingResult().getFieldError();

        String message = fieldError != null
                ? fieldError.getDefaultMessage()
                : "Error de validación";

        log.warn("Error de validación: {}", message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "error", "VALIDATION_ERROR",
                        "message", message
                ));
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(
            RecursoNoEncontradoException e) {

        log.warn("Recurso no encontrado: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "error", "NOT_FOUND",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(EstadoInvalidoException.class)
    public ResponseEntity<Map<String, String>> handleBusiness(
            EstadoInvalidoException e) {

        log.warn("Error de negocio: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "error", "BUSINESS_ERROR",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(ServicioNoDisponibleException.class)
    public ResponseEntity<Map<String, String>> handleService(
            ServicioNoDisponibleException e) {

        log.error("Servicio externo no disponible: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "error", "SERVICE_UNAVAILABLE",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatus(
            ResponseStatusException e) {

        String message = e.getReason() != null
                ? e.getReason()
                : "Error en la solicitud";

        log.warn("Error HTTP controlado: status={}, message={}",
                e.getStatusCode(),
                message);

        return ResponseEntity.status(e.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "error", e.getStatusCode().toString(),
                        "message", message
                ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(
            RuntimeException e) {

        log.warn("Error de ejecución controlado: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "error", "BAD_REQUEST",
                        "message", e.getMessage() != null
                                ? e.getMessage()
                                : "Solicitud inválida"
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception e) {

        log.error("Error inesperado capturado por GlobalExceptionHandler", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "error", "INTERNAL_ERROR",
                        "message", "Error inesperado en el sistema"
                ));
    }
}