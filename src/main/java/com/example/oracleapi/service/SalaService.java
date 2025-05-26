package com.example.oracleapi.service;

import com.example.oracleapi.dto.SalaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class SalaService {

    @Autowired
    private DataSource dataSource;

    public void criarSala(SalaDTO salaDTO) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_SALA(?, ?)}")) {

            stmt.setInt(1, salaDTO.getNumero());
            stmt.setInt(2, salaDTO.getIdSetor());

            stmt.execute();
        }
    }
}