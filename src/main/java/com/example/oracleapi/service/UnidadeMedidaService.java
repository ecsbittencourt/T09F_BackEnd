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

    public UnidadeMedidaDTO criarUnidadeMedida(UnidadeMedidaDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_UNIDADE_MEDIDA(?)}")) {
            stmt.setString(1, dto.getMedida());
            stmt.execute();
        }
        return buscarPorNome(dto.getMedida());
    }

    public List<UnidadeMedidaDTO> listarUnidadesMedida() throws SQLException {
        List<UnidadeMedidaDTO> unidades = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_LISTAR_UNIDADES_MEDIDA(?)}")) {
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                unidades.add(new UnidadeMedidaDTO(rs.getString("MEDIDA"), rs.getInt("ID")));
            }
        }
        return unidades;
    }

    public UnidadeMedidaDTO buscarPorNome(String nome) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_UNIDADE_POR_NOME(?, ?)}")) {
            stmt.setString(1, nome);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(2)) {
                if (rs.next()) {
                    return new UnidadeMedidaDTO(rs.getString("MEDIDA"), rs.getInt("ID"));
                }
            }
        }
        return null;
    }
}
