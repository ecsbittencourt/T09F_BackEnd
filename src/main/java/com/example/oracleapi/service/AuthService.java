package com.example.oracleapi.service;

import com.example.oracleapi.dto.UserDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AuthService {

    private final DataSource dataSource;

    public AuthService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UserDTO auth(UserDTO dto) throws SQLException {
        return null;
    }

    public UserDTO criarUsuario(UserDTO dto) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{call T09F_INSERIR_USUARIO(?, ?)}")) {
            stmt.setString(1, dto.nome());
            stmt.setString(2, dto.digestSenha());
            stmt.execute();

            CallableStatement stmt2 = conn.prepareCall("{call T09F_BUSCAR_USUARIO_POR_NOME(?, ?)}");
            stmt2.registerOutParameter(2, OracleTypes.CURSOR);
            stmt2.setString(1, dto.nome());
            stmt2.execute();

            try (ResultSet rs = (ResultSet) stmt2.getObject(2)) {
                if (rs.next()) {
                    return (new UserDTO(
                            rs.getObject(1, Integer.class),
                            rs.getObject(2, String.class),
                            rs.getObject(3, String.class)
                    ));
                }
            }
        }
        return null;
    }
}
