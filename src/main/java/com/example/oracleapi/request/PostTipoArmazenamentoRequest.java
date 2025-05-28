package com.example.oracleapi.request;

import java.util.List;


public record PostTipoArmazenamentoRequest(
        Integer id,
        String nome,
        List<CapacidadeDTO> capacidades
) {
}
