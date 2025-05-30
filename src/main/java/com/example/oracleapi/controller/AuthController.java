package com.example.oracleapi.controller;


import com.example.oracleapi.dto.UserDTO;
import com.example.oracleapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> verificarLogin(@RequestBody UserDTO dto) throws SQLException {
        UserDTO authenticatedUser = authService.auth(dto);
        if(Objects.isNull(authenticatedUser)) {
            throw new RuntimeException("Erro! Não foi possível autenticar");
        }
        return ResponseEntity.ok(
            authenticatedUser
        );
    }

    @PostMapping("/usuario")
    public ResponseEntity<UserDTO> criarUsuario(@RequestBody UserDTO dto) throws SQLException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                authService.criarUsuario(dto)
        );
    }
}
