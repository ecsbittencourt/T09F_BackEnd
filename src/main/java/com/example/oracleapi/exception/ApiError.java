package com.example.oracleapi.exception;


public record ApiError(
        Integer statusCode,
        String message,
        String path
) {

}
