package com.example.oracleapi.service;

import com.example.oracleapi.dto.SetorDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<SetorDTO> listarSetores() throws SQLException {
        List<SetorDTO> setores = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_LISTAR_SETORES(?)}")) {
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                setores.add(new SetorDTO(rs.getString("NOME"), rs.getInt("ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao listar setores: " + e.getMessage());
        }
        return setores;
    }


    public void atualizarSetor(int id, String nome) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_ATUALIZAR_SETOR(?, ?)}")) {
            stmt.setInt(1, id);
            stmt.setString(2, nome);
            stmt.execute();
        }
    }

    public void deletarSetor(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_DELETAR_SETOR(?)}")) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public SetorDTO buscarPorNome(String nome) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_SETOR_POR_NOME(?, ?)}")) {
            stmt.setString(1, nome);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(2)) {
                if (rs.next()) {
                    return new SetorDTO(rs.getString("NOME"), rs.getInt("ID"));
                }
            }
        }
        return null;
    }
}
