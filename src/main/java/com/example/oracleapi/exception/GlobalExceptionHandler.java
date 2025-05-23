package com.example.oracleapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiError> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request){
        logger.error("SQL Integrity Constraint Violation Exception: " + ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiError(HttpStatus.CONFLICT.value(), "Operação impossível devido à violação de restrições de integridade do banco", request.getContextPath())
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiError(HttpStatus.BAD_REQUEST.value(),
                        "Método HTTP não suportado.",
                        request.getContextPath())
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiError> handleSQLException(SQLException ex, WebRequest request) {
        logger.error("SQLException: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Erro no banco! ",
                        request.getContextPath())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        logger.error(ex.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiError(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Erro desconhecido: " + ex.getMessage(),
                        request.getContextPath()
                )
        );
    }
}
