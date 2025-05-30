package com.example.oracleapi.dto;

public record ArmazenamentoDTO(
        Integer id,
        String codigo,
        Integer idTipoArmazenamento,
        String nomeTipoArmazenamento,
        Integer idSala,
        Integer numeroSala,
        Integer idSetor,
        String nomeSetor,
        Integer idMedicamento,
        String nomeMedicamento,
        Integer quantidade
) {}
