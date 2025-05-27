package com.example.oracleapi.dto;

public class FornecedorDTO {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String cnpj;

    public FornecedorDTO() {}

    public FornecedorDTO(int id, String nome, String telefone, String email, String cnpj) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cnpj = cnpj;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
}