package com.example.oracleapi.service;

import com.example.oracleapi.dto.ArmazenamentoDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArmazenamentoService {

    @Autowired
    private DataSource dataSource;

    public List<ArmazenamentoDTO> listar() throws SQLException {
        List<ArmazenamentoDTO> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_LISTAR_ARMAZENAMENTO(?)}")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            ResultSet rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                lista.add(new ArmazenamentoDTO(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getInt(11)
                ));
            }
        }
        return lista;
    }

    public ArmazenamentoDTO inserir(ArmazenamentoDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_ARMAZENAMENTO(?, ?, ?)}")) {

            stmt.setString(1, dto.codigo());
            stmt.setInt(2, dto.idSala());
            stmt.setInt(3, dto.idTipoArmazenamento());
            stmt.execute();

            CallableStatement stmt2 = conn.prepareCall("{call T09F_BUSCAR_ARMAZENAMENTO_POR_CODIGO(?,?)}");
            stmt2.setString(1, dto.codigo());
            stmt2.registerOutParameter(2, OracleTypes.CURSOR);
            stmt2.execute();
            try(ResultSet rs = (ResultSet) stmt2.getObject(2)) {
                if(rs.next()) {
                    return new ArmazenamentoDTO(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getString(4),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getInt(7),
                            rs.getString(8),
                            rs.getInt(9),
                            rs.getString(10),
                            rs.getInt(11)
                    );
                }
            }
        }
        return null;
    }

    public ArmazenamentoDTO atualizar(int id, ArmazenamentoDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_ATUALIZAR_ARMAZENAMENTO(?, ?, ?, ?)}")) {

            stmt.setInt(1, id);
            stmt.setString(2, dto.codigo());
            stmt.setInt(3, dto.idSala());
            stmt.setInt(4, dto.idTipoArmazenamento());

            stmt.execute();
            return buscarPorId(id);
        }
    }

    public ArmazenamentoDTO buscarPorId(int id) throws SQLException{
        try(Connection conn = dataSource.getConnection();
        CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_ARMAZENAMENTO_POR_ID(?,?)}")) {
            stmt.setInt(1, id);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            try(ResultSet rs = (ResultSet) stmt.getObject(2)) {
                if (rs.next()) return new ArmazenamentoDTO(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getInt(11)
                );
            }

        }
        return null;
    }

    public void deletar(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_DELETAR_ARMAZENAMENTO(?)}")) {

            stmt.setInt(1, id);
            stmt.execute();
        }
    }
}
