package com.example.oracleapi.dto;

public class SetorDTO {
    private String nome;
    private int id;

    public SetorDTO() {}

    public SetorDTO(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
