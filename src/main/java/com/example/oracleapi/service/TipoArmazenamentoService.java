package com.example.oracleapi.service;

import com.example.oracleapi.dto.StatusMovimentacaoInternaDTO;
import com.example.oracleapi.dto.TipoArmazenamentoDTO;
import com.example.oracleapi.dto.TipoArmazenamentoTipoMedicamentoDTO;
import com.example.oracleapi.request.CapacidadeDTO;
import com.example.oracleapi.request.PostTipoArmazenamentoRequest;
import oracle.jdbc.OracleTypes;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Service
public class TipoArmazenamentoService {

    private final DataSource dataSource;

    public TipoArmazenamentoService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<TipoArmazenamentoDTO> buscarTipoArmazenamento() throws SQLException {
        List<TipoArmazenamentoDTO> lista = new ArrayList<>();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_TIPO_ARMAZENAMENTO(?)}")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    lista.add(new TipoArmazenamentoDTO(rs.getObject(1, Integer.class), rs.getObject(2, String.class)));
                }
            }
        }

        return lista;
    }

    public TipoArmazenamentoDTO buscarTipoArmazenamentoPorId(Integer id) throws SQLException {
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_TIPO_ARMAZENAMENTO_POR_ID(?, ?)}")) {

            stmt.setInt(1, id);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(2)) {
                if (rs.next()) {
                    return (new TipoArmazenamentoDTO(rs.getObject(1, Integer.class), rs.getObject(2, String.class)));
                }
            }
        }

        return null;
    }

    public void deletarTipoArmazenamento(Integer id) throws SQLException {

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall("{call T09F_DELETAR_TIPO_ARMAZENAMENTO(?)}")) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
            throw ex;
        }

    }

    public TipoArmazenamentoDTO inserirTipoArmazenamento(PostTipoArmazenamentoRequest request) throws SQLException {
        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);
            CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_TIPO_ARMAZENAMENTO(?)}");
            stmt.setString(1, request.nome());
            stmt.execute();
            CallableStatement stmt2 = conn.prepareCall("{call T09F_BUSCAR_TIPO_ARMAZENAMENTO_POR_NOME(?,?)}");
            stmt2.setString(1, request.nome());
            stmt2.registerOutParameter(2, OracleTypes.CURSOR);
            stmt2.execute();
            TipoArmazenamentoDTO saved;
            try (ResultSet rs = (ResultSet) stmt2.getObject(2)) {
                if (rs.next()) {
                    saved = new TipoArmazenamentoDTO(rs.getObject(1, Integer.class), rs.getObject(2, String.class));
                } else {
                    throw new SQLException();
                }
            }
            for (CapacidadeDTO capacidade : request.capacidades()) {
                CallableStatement stmt3 = conn.prepareCall("{call T09F_INSERIR_TIPO_ARMAZENAMENTO_TIPO_MEDICAMENTO(?, ?, ?)}");
                stmt3.setInt(1, saved.id());
                stmt3.setInt(2, capacidade.id());
                stmt3.setInt(3, capacidade.quantidade());
                stmt3.execute();
            }
            conn.commit();


        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
        return null;
    }

    public List<TipoArmazenamentoTipoMedicamentoDTO> buscarTipoArmazenamentoTipoMedicamento(Integer idTipoArmazenamento) throws SQLException{
        List<TipoArmazenamentoTipoMedicamentoDTO> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall("{call T09F_BUSCAR_TIPO_ARMAZENAMENTO_TIPO_MEDICAMENTO_POR_ID_TIPO_ARMAZENAMENTO(?, ?)}")) {

            stmt.setInt(1, idTipoArmazenamento);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(2)) {
                while (rs.next()) {
                    lista.add(
                            new TipoArmazenamentoTipoMedicamentoDTO(
                                    rs.getInt(1),
                                    rs.getInt(2),
                                    rs.getString(3),
                                    rs.getInt(4),
                                    rs.getString(5),
                                    rs.getInt(6)
                            )
                    );
                }
                return lista;
            }
        }

    }

    public TipoArmazenamentoDTO atualizarTipoArmazenamento(PostTipoArmazenamentoRequest request) throws SQLException{
        Connection conn = dataSource.getConnection();
        try {
            CallableStatement stmt = conn.prepareCall("{call T09F_ATUALIZAR_TIPO_ARMAZENAMENTO(?, ?)}");
            conn.setAutoCommit(false);
            stmt.setInt(1, request.id());
            stmt.setString(2, request.nome());
            stmt.execute();

            for(CapacidadeDTO capacidade : request.capacidades()) {
                CallableStatement stmt2 = conn.prepareCall("{call T09F_BUSCAR_TIPO_ARMAZENAMENTO_TIPO_MEDICAMENTO_POR_IDS(?,?,?)}");
                stmt2.setInt(1, request.id());
                stmt2.setInt(2,capacidade.id());
                stmt2.registerOutParameter(3, OracleTypes.CURSOR);
                stmt2.execute();

                try(ResultSet rs = (ResultSet)stmt2.getObject(3)) {
                    if (!rs.next()) {
                        // Nova capacidade
                        CallableStatement stmt3 = conn.prepareCall("{call T09F_INSERIR_TIPO_ARMAZENAMENTO_TIPO_MEDICAMENTO(?,?,?)}");
                        stmt3.setInt(1, request.id());
                        stmt3.setInt(2, capacidade.id());
                        stmt3.setInt(3, capacidade.quantidade());
                        stmt3.execute();
                    } else {
                        // Atualizar capacidade
                        CallableStatement stmt3 = conn.prepareCall("{call T09F_ATUALIZAR_TIPO_MEDICAMENTO_TIPO_ARMAZENAMENTO(?,?,?)}");
                        stmt3.setInt(1, request.id());
                        stmt3.setInt(2, capacidade.id());
                        stmt3.setInt(3, capacidade.quantidade());
                        stmt3.execute();
                    }
                }
            }
            List<TipoArmazenamentoTipoMedicamentoDTO> listaDesatualizadaBanco = buscarTipoArmazenamentoTipoMedicamento(request.id());
            for(TipoArmazenamentoTipoMedicamentoDTO registro : listaDesatualizadaBanco) {
                if(request.capacidades().stream().noneMatch(capacidade -> registro.idTipoMedicamento().equals(capacidade.id()))) {
                    // deletar o registro
                    System.out.println("AAAAA: " + registro.toString());
                    CallableStatement stmt4 = conn.prepareCall("{call T09F_DELETAR_TIPO_MEDICAMENTO_TIPO_ARMAZENAMENTO(?)}");
                    stmt4.setInt(1, registro.id());
                    stmt4.execute();
                }

            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
        finally {
            conn.close();
        }
        return buscarTipoArmazenamentoPorId(request.id());
    }
}

