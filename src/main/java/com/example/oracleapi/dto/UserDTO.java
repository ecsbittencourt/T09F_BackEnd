package com.example.oracleapi.dto;

public record UserDTO(
        Integer id,
        String nome,
        String digestSenha
) {
}
