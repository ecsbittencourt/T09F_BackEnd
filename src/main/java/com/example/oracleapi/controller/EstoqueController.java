package com.example.oracleapi.controller;

import com.example.oracleapi.dto.EstoqueDTO;
import com.example.oracleapi.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/estoques")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public ResponseEntity<List<EstoqueDTO>> listarEstoques() {
        try {
            return ResponseEntity.ok(estoqueService.listarEstoques());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> criarEstoque(@RequestBody EstoqueDTO dto) {
        try {
            estoqueService.criarEstoque(dto);
            return ResponseEntity.ok("Estoque criado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao criar estoque: " + e.getMessage());
        }
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<Object>> listarTiposEstoque() {
        try {
            return ResponseEntity.ok(estoqueService.listarTiposEstoque());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/setores")
    public ResponseEntity<List<Object>> listarSetores() {
        try {
            return ResponseEntity.ok(estoqueService.listarSetores());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/salas")
    public ResponseEntity<List<Object>> listarSalas() {
        try {
            return ResponseEntity.ok(estoqueService.listarSalas());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
