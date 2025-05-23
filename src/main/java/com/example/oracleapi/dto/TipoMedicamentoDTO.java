package com.example.oracleapi.dto;

public record TipoMedicamentoDTO(
        Integer id,
        String nome,
        Integer idUnidadeMedida,
        String nomeUnidadeMedida
) {}
