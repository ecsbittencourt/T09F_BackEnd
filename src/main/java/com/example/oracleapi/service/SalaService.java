package com.example.oracleapi.service;

import com.example.oracleapi.dto.SalaDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<SalaDTO> listarSalas() throws SQLException {
        List<SalaDTO> salas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_LISTAR_SALAS(?)}")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    salas.add(new SalaDTO(
                            rs.getInt("ID"),
                            rs.getInt("NUMERO"),
                            rs.getInt("ID_SETOR")
                    ));
                }
            }
        }
        return salas;
    }

    public SalaDTO buscarPorId(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_SALA_POR_ID(?, ?)}")) {

            stmt.setInt(1, id);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(2)) {
                if (rs.next()) {
                    return new SalaDTO(
                            rs.getInt("ID"),
                            rs.getInt("NUMERO"),
                            rs.getInt("ID_SETOR")
                    );
                }
            }
        }
        return null; // retorna null se não achar
    }

    public void editarSala(int id, SalaDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_ATUALIZAR_SALA(?, ?, ?)}")) {

            stmt.setInt(1, id);
            stmt.setInt(2, dto.getNumero());
            stmt.setInt(3, dto.getIdSetor());

            stmt.execute();
        }
    }

    public void deletarSala(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_DELETAR_SALA(?)}")) {

            stmt.setInt(1, id);
            stmt.execute();
        }
    }
}
