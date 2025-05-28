package com.example.oracleapi.controller;

import com.example.oracleapi.dto.FornecedorDTO;
import com.example.oracleapi.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<String> criarFornecedor(@RequestBody FornecedorDTO fornecedorDTO) {
        try {
            fornecedorService.criarFornecedor(fornecedorDTO);
            return ResponseEntity.ok("Fornecedor criado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao criar fornecedor: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<FornecedorDTO>> listarFornecedores() {
        try {
            return ResponseEntity.ok(fornecedorService.listarFornecedores());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarFornecedor(@PathVariable int id, @RequestBody FornecedorDTO dto) {
        try {
            fornecedorService.editarFornecedor(id, dto);
            return ResponseEntity.ok("Fornecedor atualizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao editar fornecedor: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarFornecedor(@PathVariable int id) {
        try {
            fornecedorService.deletarFornecedor(id);
            return ResponseEntity.ok("Fornecedor deletado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao deletar fornecedor: " + e.getMessage());
        }
    }
}