package com.example.oracleapi.service;

import com.example.oracleapi.dto.FornecedorDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FornecedorService {

    @Autowired
    private DataSource dataSource;

    public void criarFornecedor(FornecedorDTO fornecedorDTO) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_FORNECEDOR(?, ?, ?, ?)}")) {

            stmt.setString(1, fornecedorDTO.getNome());
            stmt.setString(2, fornecedorDTO.getTelefone());
            stmt.setString(3, fornecedorDTO.getEmail());
            stmt.setString(4, fornecedorDTO.getCnpj());

            stmt.execute();
        }
    }

    public List<FornecedorDTO> listarFornecedores() throws SQLException {
        List<FornecedorDTO> fornecedores = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_LISTAR_FORNECEDORES(?)}")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    fornecedores.add(new FornecedorDTO(
                            rs.getInt("ID"),
                            rs.getString("NOME"),
                            rs.getString("TELEFONE"),
                            rs.getString("EMAIL"),
                            rs.getString("CNPJ")
                    ));
                }
            }
        }
        return fornecedores;
    }

    public void editarFornecedor(int id, FornecedorDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_ATUALIZAR_FORNECEDOR(?, ?, ?, ?, ?)}")) {

            stmt.setInt(1, id);
            stmt.setString(2, dto.getNome());
            stmt.setString(3, dto.getTelefone());
            stmt.setString(4, dto.getEmail());
            stmt.setString(5, dto.getCnpj());

            stmt.execute();
        }
    }

    public void deletarFornecedor(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_DELETAR_FORNECEDOR(?)}")) {

            stmt.setInt(1, id);
            stmt.execute();
        }
    }
}