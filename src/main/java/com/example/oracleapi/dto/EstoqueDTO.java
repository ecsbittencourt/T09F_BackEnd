package com.example.oracleapi.dto;

public record EstoqueDTO(
        Integer id,
        String nome,
        Integer idTipoEstoque,
        String nomeTipoEstoque,
        Integer idSala,
        Integer numeroSala,
        Integer idSetor,
        String nomeSetor
) {
}
