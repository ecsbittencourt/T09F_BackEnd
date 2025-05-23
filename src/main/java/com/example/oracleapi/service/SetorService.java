package com.example.oracleapi.service;

import com.example.oracleapi.AcaoExameDTO;
import com.example.oracleapi.dto.SetorDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class SetorService {

    @Autowired
    private DataSource dataSource;

    public SetorDTO criarSetor(SetorDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_SETOR(?)}")) {
            stmt.setString(1, dto.getNome());
            stmt.execute();
        }
        return buscarPorNome(dto.getNome());
    }

    public SetorDTO buscarPorNome(String nome) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_SETOR_POR_NOME(?, ?)}")) {
            stmt.setString(1, nome);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(2)) {
                if (rs.next()) {
                    SetorDTO dto = new SetorDTO();
                    dto.setNome(rs.getString("NOME"));
                    dto.setId(rs.getInt("ID"));
                    return dto;
                }
            }
        }
        return null;
    }
}
