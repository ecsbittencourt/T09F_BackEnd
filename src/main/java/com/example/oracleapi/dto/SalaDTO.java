package com.example.oracleapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SalaDTO {

    private int id;

    private int numero;


    private int idSetor;

    public SalaDTO() {}

    public SalaDTO(int id, int numero, int idSetor) {

        this.id = id;

        this.numero = numero;

        this.idSetor = idSetor;

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getNumero() { return numero; }

    public void setNumero(int numero) { this.numero = numero; }

    public int getIdSetor() { return idSetor; }

    public void setIdSetor(int idSetor) { this.idSetor = idSetor; }

}

