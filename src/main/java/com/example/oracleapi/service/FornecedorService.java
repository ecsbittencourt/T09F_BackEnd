package com.example.oracleapi.service;

import com.example.oracleapi.dto.FornecedorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class FornecedorService {

    @Autowired
    private DataSource dataSource;

    public void criarFornecedor(FornecedorDTO fornecedorDTO) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call INSERIR_FORNECEDOR(?, ?, ?, ?)}")) {

            stmt.setString(1, fornecedorDTO.getNome());
            stmt.setString(2, fornecedorDTO.getTelefone());
            stmt.setString(3, fornecedorDTO.getEmail());
            stmt.setString(4, fornecedorDTO.getCnpj());

            stmt.execute();
        }
    }
}