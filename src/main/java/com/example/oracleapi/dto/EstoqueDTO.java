package com.example.oracleapi.dto;

public class EstoqueDTO {
    private int id;
    private String nome;
    private int id_t09f_tipo_estoque;
    private int id_t09f_setor;
    private int id_t09f_sala;

    public EstoqueDTO() {}

    public EstoqueDTO(int id, String nome, int idTipoEstoque, int idSetor, int idSala) {
        this.id = id;
        this.nome = nome;
        this.id_t09f_tipo_estoque = idTipoEstoque;
        this.id_t09f_setor = idSetor;
        this.id_t09f_sala = idSala;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getId_t09f_tipo_estoque() { return id_t09f_tipo_estoque; }
    public void setId_t09f_tipo_estoque(int id_t09f_tipo_estoque) { this.id_t09f_tipo_estoque = id_t09f_tipo_estoque; }

    public int getId_t09f_setor() { return id_t09f_setor; }
    public void setId_t09f_setor(int id_t09f_setor) { this.id_t09f_setor = id_t09f_setor; }

    public int getId_t09f_sala() { return id_t09f_sala; }
    public void setId_t09f_sala(int id_t09f_sala) { this.id_t09f_sala = id_t09f_sala; }
}
