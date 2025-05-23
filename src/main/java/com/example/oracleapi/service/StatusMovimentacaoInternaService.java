package com.example.oracleapi.service;

import com.example.oracleapi.dto.StatusMovimentacaoInternaDTO;
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
public class StatusMovimentacaoInternaService {

    private final DataSource dataSource;

    public StatusMovimentacaoInternaService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<StatusMovimentacaoInternaDTO> buscarTiposMedicamentos() throws SQLException {
        List<StatusMovimentacaoInternaDTO> lista = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_STATUS_MOVIMENTACAO_INTERNA(?)}")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    lista.add(new StatusMovimentacaoInternaDTO(
                            rs.getObject(1, Integer.class),
                            rs.getObject(2, String.class)
                    ));
                }
            }
        }

        return lista;
    }

    public StatusMovimentacaoInternaDTO inserirStatusMovimentacaoInterna(StatusMovimentacaoInternaDTO dto) throws SQLException{

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_STATUS_MOVIMENTACAO_INTERNA(?)}")) {
            stmt.setString(1, dto.nome());
            stmt.execute();

            CallableStatement stmt2 = conn.prepareCall("{call T09F_BUSCAR_STATUS_MOVIMENTACAO_INTERNA_POR_STATUS(?, ?)}");
            stmt2.registerOutParameter(2, OracleTypes.CURSOR);
            stmt2.setString(1, dto.nome());
            stmt2.execute();

            try (ResultSet rs = (ResultSet) stmt2.getObject(2)) {
                if (rs.next()) {
                    return (new StatusMovimentacaoInternaDTO(
                            rs.getObject(1, Integer.class),
                            rs.getObject(2, String.class)
                    ));
                }
            }
        }
        return null;
    }

    public StatusMovimentacaoInternaDTO atualizarStatusMovimentacaoInterna(StatusMovimentacaoInternaDTO dto) throws SQLException{

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_ATUALIZAR_STATUS_MOVIMENTACAO_INTERNA(?, ?)}")) {
            stmt.setInt(1, dto.id());
            stmt.setString(2, dto.nome());
            stmt.execute();
        }
        return buscarStatusMovimentacaoPorId(dto.id());
    }

    public StatusMovimentacaoInternaDTO buscarStatusMovimentacaoPorId(Integer id) throws SQLException {

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_STATUS_MOVIMENTACAO_INTERNA_POR_ID(?, ?)}")) {

            stmt.setInt(1, id);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(2)) {
                if (rs.next()) {
                    return (new StatusMovimentacaoInternaDTO(
                            rs.getObject(1, Integer.class),
                            rs.getObject(2, String.class)
                    ));
                }
            }
        }

        return null;
    }

    public void deleteStatusMovimentacaoInterna(Integer id) throws SQLException{
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_DELETAR_STATUS_MOVIMENTACAO_INTERNA(?)}")) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
            throw ex;
        }
    }
}
