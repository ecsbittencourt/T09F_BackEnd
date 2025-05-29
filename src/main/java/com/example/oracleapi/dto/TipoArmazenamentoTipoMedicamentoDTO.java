package com.example.oracleapi.dto;

public record TipoArmazenamentoTipoMedicamentoDTO(
        Integer id,
        Integer idTipoArmazenamento,
        String nomeTipoArmazenamento,
        Integer idTipoMedicamento,
        String nomeTipoMedicamento,
        Integer quantidade
) {
}
