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
                    EstoqueDTO dto = new EstoqueDTO();
                    dto.setId(rs.getInt("ID"));
                    dto.setNome(rs.getString("NOME"));
                    dto.setId_t09f_tipo_estoque(rs.getInt("ID_T09F_TIPO_ESTOQUE"));
                    dto.setId_t09f_setor(rs.getInt("ID_T09F_SETOR"));
                    dto.setId_t09f_sala(rs.getInt("ID_T09F_SALA"));
                    estoques.add(dto);
                }
            }
        }
        return estoques;
    }

    public void criarEstoque(EstoqueDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_ESTOQUE(?, ?, ?, ?)}")) {

            stmt.setString(1, dto.getNome());
            stmt.setInt(2, dto.getId_t09f_tipo_estoque());
            stmt.setInt(3, dto.getId_t09f_setor());
            stmt.setInt(4, dto.getId_t09f_sala());

            stmt.execute();
        }
    }

    public List<Object> listarTiposEstoque() throws SQLException {
        // Exemplo genérico, ajuste para seu DTO específico de tipo estoque
        List<Object> tipos = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_LISTAR_TIPO_ESTOQUE(?)}")) {
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    tipos.add(new Object() {
                        public int id = rs.getInt("ID");
                        public String nome = rs.getString("NOME");
                    });
                }
            }
        }
        return tipos;
    }

    public List<Object> listarSetores() throws SQLException {
        List<Object> setores = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_LISTAR_SETORES(?)}")) {
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    setores.add(new Object() {
                        public int id = rs.getInt("ID");
                        public String nome = rs.getString("NOME");
                    });
                }
            }
        }
        return setores;
    }

    public List<Object> listarSalas() throws SQLException {
        List<Object> salas = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_LISTAR_SALAS(?)}")) {
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    salas.add(new Object() {
                        public int id = rs.getInt("ID");
                        public int numero = rs.getInt("NUMERO");
                        public int idSetor = rs.getInt("ID_T09F_SETOR");
                    });
                }
            }
        }
        return salas;
    }
}
