package com.example.oracleapi.dto;

public class UnidadeMedidaDTO {
    private int id;
    private String medida;

    public UnidadeMedidaDTO() {}

    public UnidadeMedidaDTO(String medida, int id) {
        this.medida = medida;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }
}
