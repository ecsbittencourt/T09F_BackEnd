package com.example.oracleapi.service;

import com.example.oracleapi.dto.EstoqueDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    private DataSource dataSource;

    public List<EstoqueDTO> listarEstoques() throws SQLException {
        List<EstoqueDTO> estoques = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_LISTAR_ESTOQUES(?)}")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    EstoqueDTO dto = new EstoqueDTO(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getString(4),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getInt(7),
                            rs.getString(8)
                    );
                    estoques.add(dto);
                }
            }
        }
        return estoques;
    }

    public void criarEstoque(EstoqueDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_ESTOQUE(?, ?, ?)}")) {

            stmt.setString(1, dto.nome());
            stmt.setInt(2, dto.idTipoEstoque());
            stmt.setInt(3, dto.idSala());

            stmt.execute();
        }
    }

    public EstoqueDTO buscarEstoque(Integer id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_ESTOQUE_POR_ID(?,?)}")) {
            stmt.setInt(1, id);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(2)) {
                if (rs.next()) {
                    return new EstoqueDTO(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getString(4),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getInt(7),
                            rs.getString(8)
                    );

                }
            }
        }
        return null;
    }

    public EstoqueDTO atualizarEstoque(EstoqueDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_ATUALIZAR_ESTOQUE(?, ?,?,?)}")) {
            stmt.setInt(1, dto.id());
            stmt.setString(2, dto.nome());
            stmt.setInt(3, dto.idTipoEstoque());
            stmt.setInt(4, dto.idSala());
            stmt.execute();
        }
        return buscarEstoque(dto.id());
    }

    public void deletarEstoque(Integer id) throws SQLException {

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_DELETAR_ESTOQUE(?)}")) {

            stmt.setInt(1, id);
            stmt.execute();

        }
    }
}
