package com.example.oracleapi.controller;

import com.example.oracleapi.dto.SalaDTO;
import com.example.oracleapi.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<String> criarSala(@RequestBody SalaDTO salaDTO) {
        try {
            salaService.criarSala(salaDTO);
            return ResponseEntity.ok("Sala criada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao criar sala: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SalaDTO>> listarSalas() {
        try {
            return ResponseEntity.ok(salaService.listarSalas());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarSala(@PathVariable int id, @RequestBody SalaDTO dto) {
        try {
            salaService.editarSala(id, dto);
            return ResponseEntity.ok("Sala atualizada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao editar sala: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarSala(@PathVariable int id) {
        try {
            salaService.deletarSala(id);
            return ResponseEntity.ok("Sala deletada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao deletar sala: " + e.getMessage());
        }
    }
}