package com.example.oracleapi.controller;

import com.example.oracleapi.dto.UnidadeMedidaDTO;
import com.example.oracleapi.service.UnidadeMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/unidades-medida")
public class UnidadeMedidaController {

    @Autowired
    private UnidadeMedidaService service;

    @GetMapping
    public ResponseEntity<List<UnidadeMedidaDTO>> listar() throws SQLException {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody UnidadeMedidaDTO dto) throws SQLException {
        service.inserir(dto.getMedida());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable int id, @RequestBody UnidadeMedidaDTO dto) throws SQLException {
        service.atualizar(id, dto.getMedida());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) throws SQLException {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}
