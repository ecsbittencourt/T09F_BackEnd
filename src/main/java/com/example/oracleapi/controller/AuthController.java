package com.example.oracleapi.controller;


import com.example.oracleapi.dto.UserDTO;
import com.example.oracleapi.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> verificarLogin(@RequestBody UserDTO dto) throws SQLException {
        return ResponseEntity.ok(
            authService.auth(dto)
        );
    }

    @PostMapping("/nome")
    public ResponseEntity<UserDTO> criarUsuario(@RequestBody UserDTO dto) throws SQLException {
        return ResponseEntity.ok(
                authService.criarUsuario(dto)
        );
    }
}
