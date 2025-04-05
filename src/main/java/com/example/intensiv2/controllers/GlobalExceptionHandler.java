package com.example.intensiv2.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Класс глобальной обработки исключений.
 * Этот класс перехватывает исключения, возникающие в процессе обработки запросов в приложении, и обрабатывает их централизованно.
 * В зависимости от типа исключения, возвращается соответствующий ответ с кодом состояния и сообщением.
 * Используется для глобальной обработки ошибок в приложении.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Обрабатывает все общие исключения {@link Exception}.
     * В случае возникновения непредвиденной ошибки возвращается сообщение об ошибке с кодом 500.
     *
     * @param ex исключение, которое было перехвачено
     * @return {@link ResponseEntity} с кодом 500 (Internal Server Error) и сообщением об ошибке
     */
    @Operation(summary = "Handle general exceptions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("status", "error");
        error.put("message", "An unexpected error occurred: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Обрабатывает исключения {@link NoSuchElementException}.
     * Возвращает ответ с кодом 404, если запрашиваемый элемент не был найден в базе данных.
     *
     * @param ex исключение, которое было перехвачено
     * @return {@link ResponseEntity} с кодом 404 (Not Found) и сообщением об ошибке
     */
    @Operation(summary = "Handle NoSuchElementException")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Element not found")
    })
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("status", "error");
        error.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
