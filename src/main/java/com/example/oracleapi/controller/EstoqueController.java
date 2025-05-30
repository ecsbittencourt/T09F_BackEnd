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
    public ResponseEntity<List<EstoqueDTO>> listarEstoques() throws SQLException{

        return ResponseEntity.ok(estoqueService.listarEstoques());

    }

    @GetMapping("{id}")
    public ResponseEntity<EstoqueDTO> buscarEstoque(@PathVariable Integer id) throws SQLException{
        return ResponseEntity.ok(estoqueService.buscarEstoque(id));
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

    @PutMapping("{id}")
    public ResponseEntity<EstoqueDTO> atualizarEstoque(@PathVariable Integer id, @RequestBody EstoqueDTO dto) throws SQLException{
        if(!id.equals(dto.id())) throw new RuntimeException("Erro! Ids não correspondem.");
        return ResponseEntity.ok(estoqueService.atualizarEstoque(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarEstoque(@PathVariable Integer id) throws SQLException {
        estoqueService.deletarEstoque(id);
        return ResponseEntity.noContent().build();
    }
}
