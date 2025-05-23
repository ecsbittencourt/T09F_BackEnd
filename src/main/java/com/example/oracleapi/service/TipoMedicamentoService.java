package com.example.oracleapi.service;

import com.example.oracleapi.dto.TipoEstoqueDTO;
import com.example.oracleapi.dto.TipoMedicamentoDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TipoMedicamentoService {

    private DataSource dataSource;

    public TipoMedicamentoService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<TipoMedicamentoDTO> buscarTiposMedicamentos() throws SQLException {
        List<TipoMedicamentoDTO> lista = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_TIPO_MEDICAMENTO(?)}")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    lista.add(new TipoMedicamentoDTO(
                            rs.getObject(1, Integer.class),
                            rs.getObject(2, String.class),
                            rs.getObject(3, Integer.class),
                            rs.getObject(4, String.class)
                    ));
                }
            }
        }

        return lista;
    }

    public TipoMedicamentoDTO buscarTipoMedicamentoPorId(Integer id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_TIPO_MEDICAMENTO_POR_ID(?, ?)}")) {
            stmt.setInt(1, id);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(2)) {
                if (rs.next()) {
                    return new TipoMedicamentoDTO(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getInt("id_unidade_medida"),
                            rs.getString("nome_unidade_medida"));
                }
            }
        }
        return null;
    }



    public TipoMedicamentoDTO inserirTipoMedicamento(TipoMedicamentoDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_TIPO_MEDICAMENTO(?, ?)}")) {
            stmt.setString(1, dto.nome());
            stmt.setInt(2, dto.idUnidadeMedida());
            stmt.execute();
        }
        return dto;
    }

    public void deletarTipoMedicamento(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_DELETAR_TIPO_MEDICAMENTO(?)}")) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public TipoMedicamentoDTO atualizarTipoMedicamento(TipoMedicamentoDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_ATUALIZAR_TIPO_MEDICAMENTO(?, ?, ?)}")) {
            stmt.setInt(1, dto.id());
            stmt.setString(2, dto.nome());
            stmt.setInt(3, dto.idUnidadeMedida());
            stmt.execute();
        }
        return buscarTipoMedicamentoPorId(dto.id());
    }
}
