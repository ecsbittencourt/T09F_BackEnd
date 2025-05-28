package com.example.oracleapi.service;

import com.example.oracleapi.dto.UnidadeMedidaDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnidadeMedidaService {

    @Autowired
    private DataSource dataSource;

    public List<UnidadeMedidaDTO> listar() throws SQLException {
        List<UnidadeMedidaDTO> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_LISTAR_UNIDADE_MEDIDA(?)}")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            ResultSet rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                lista.add(new UnidadeMedidaDTO(rs.getInt("ID"), rs.getString("MEDIDA")));
            }
        }
        return lista;
    }

    public void inserir(String medida) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_UNIDADE_MEDIDA(?)}")) {

            stmt.setString(1, medida);
            stmt.execute();
        }
    }

    public void atualizar(int id, String medida) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_ATUALIZAR_UNIDADE_MEDIDA(?, ?)}")) {

            stmt.setInt(1, id);
            stmt.setString(2, medida);
            stmt.execute();
        }
    }

    public void deletar(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_DELETAR_UNIDADE_MEDIDA(?)}")) {

            stmt.setInt(1, id);
            stmt.execute();
        }
    }
}
