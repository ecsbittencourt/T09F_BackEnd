package com.example.oracleapi.service;

import com.example.oracleapi.AcaoExameDTO;
import com.example.oracleapi.dto.TipoEstoqueDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TipoEstoqueService {


    private DataSource dataSource;

    public TipoEstoqueService(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public List<TipoEstoqueDTO> buscarTiposEstoque() throws SQLException {
        List<TipoEstoqueDTO> lista = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_TIPO_ESTOQUE(?)}")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    lista.add(new TipoEstoqueDTO(rs.getObject(1, Integer.class), rs.getObject(2, String.class)));
                }
            }
        }

        return lista;
    }
    public TipoEstoqueDTO inserirTipoEstoque(TipoEstoqueDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_TIPO_ESTOQUE(?)}")) {
            stmt.setString(1, dto.nome());
            stmt.execute();
        }
        return dto;
    }


    public TipoEstoqueDTO buscarPorId(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_TIPO_ESTOQUE_POR_ID(?, ?)}")) {
            stmt.setInt(1, id);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(2)) {
                if (rs.next()) {
                    return new TipoEstoqueDTO(rs.getInt("id"),rs.getString("nome"));
                }
            }
        }
        return null;
    }
    public TipoEstoqueDTO atualizarTipoEstoque(int id, TipoEstoqueDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_ATUALIZAR_TIPO_ESTOQUE(?, ?)}")) {
            stmt.setInt(1, id);
            stmt.setString(2, dto.nome());
            stmt.execute();
        }
        return buscarPorId(dto.id());
    }


    public void deletarTipoEstoque(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_DELETAR_TIPO_ESTOQUE(?)}")) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
            throw ex;
        }
    }
}
