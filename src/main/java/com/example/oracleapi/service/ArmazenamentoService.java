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

    /*public List<ArmazenamentoDTO> listar() throws SQLException {
        List<ArmazenamentoDTO> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_LISTAR_ARMAZENAMENTO(?)}")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            ResultSet rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                lista.add(new ArmazenamentoDTO(
                        rs.getInt("ID"),
                        rs.getString("CODIGO"),
                        rs.getInt("ID_SALA"),
                        rs.getInt("ID_TIPO_ARMAZENAMENTO")
                ));
            }
        }
        return lista;
    }*/

    public void inserir(ArmazenamentoDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_ARMAZENAMENTO(?, ?, ?)}")) {

            stmt.setString(1, dto.getCodigo());
            stmt.setInt(2, dto.getIdSala());
            stmt.setInt(3, dto.getIdTipoArmazenamento());

            stmt.execute();
        }
    }




    public void atualizar(int id, ArmazenamentoDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_ATUALIZAR_ARMAZENAMENTO(?, ?, ?)}")) {

            stmt.setString(2, dto.getCodigo());
            stmt.setInt(4, dto.getIdSala());
            stmt.setInt(5, dto.getIdTipoArmazenamento());
        }
    }

    public void deletar(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_DELETAR_ARMAZENAMENTO(?)}")) {

            stmt.setInt(1, id);
            stmt.execute();
        }
    }
}
