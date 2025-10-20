package org.wt.com.expense_sharing_app.common;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.wt.com.expense_sharing_app.DTO.APIResponseDTO;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        APIResponseDTO<Object> response = APIResponseDTO.builder()
                .errorCode("500")
                .errorMessage("Internal server error: " + ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        APIResponseDTO<Object> response = APIResponseDTO.builder()
                .errorCode("400")
                .errorMessage("Runtime error: " + ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex) {
        APIResponseDTO<Object> response = APIResponseDTO.builder()
                .errorCode("404")
                .errorMessage("Entity not found: " + ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<?> handleSQLException(Exception ex) {
        APIResponseDTO<Object> response = APIResponseDTO.builder()
                .errorCode("500")
                .errorMessage("Database error: " + ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
