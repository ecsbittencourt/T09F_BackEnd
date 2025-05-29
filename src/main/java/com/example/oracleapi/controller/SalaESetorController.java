package com.example.oracleapi.controller;

import com.example.oracleapi.dto.SalaESetorDTO;
import com.example.oracleapi.service.SalaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/salas-e-setores")
public class SalaESetorController {

    private final SalaService salaService;

    public SalaESetorController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public ResponseEntity<List<SalaESetorDTO>> listarSalasERespectivosSetores() throws SQLException {
        return ResponseEntity.ok(
                salaService.listarSalasERespectivosSetores()
        );
    }
}
