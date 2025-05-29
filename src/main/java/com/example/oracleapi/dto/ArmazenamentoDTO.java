package com.example.oracleapi.dto;

public class ArmazenamentoDTO {
    private String codigo;
    private int idSala;
    private int idTipoArmazenamento;

    public ArmazenamentoDTO() {}

    public ArmazenamentoDTO(String codigo, int idSala, int idTipoArmazenamento) {
        this.codigo = codigo;
        this.idSala = idSala;
        this.idTipoArmazenamento = idTipoArmazenamento;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public int getIdSala() { return idSala; }
    public void setIdSala(int idSala) { this.idSala = idSala; }

    public int getIdTipoArmazenamento() { return idTipoArmazenamento; }
    public void setIdTipoArmazenamento(int idTipoArmazenamento) { this.idTipoArmazenamento = idTipoArmazenamento; }
}
