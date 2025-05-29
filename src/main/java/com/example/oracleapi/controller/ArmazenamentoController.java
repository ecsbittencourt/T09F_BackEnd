package com.example.oracleapi.controller;

import com.example.oracleapi.dto.ArmazenamentoDTO;
import com.example.oracleapi.service.ArmazenamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/armazenamentos")
public class ArmazenamentoController {

    @Autowired
    private ArmazenamentoService service;

    @GetMapping
    public ResponseEntity<List<ArmazenamentoDTO>> listar() throws SQLException {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody ArmazenamentoDTO dto) {
        try {
            service.inserir(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao salvar armazenamento: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody ArmazenamentoDTO dto) {
        try {
            service.atualizar(id, dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao atualizar armazenamento: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        try {
            service.deletar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao excluir armazenamento: " + e.getMessage());
        }
    }
}
