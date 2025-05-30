package com.example.oracleapi.controller;

import com.example.oracleapi.dto.ArmazenamentoDTO;
import com.example.oracleapi.service.ArmazenamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("{id}")
    public ResponseEntity<ArmazenamentoDTO> buscarPorId(@PathVariable Integer id) throws SQLException {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ArmazenamentoDTO> inserir(@RequestBody ArmazenamentoDTO dto) throws SQLException{
        return ResponseEntity.status(HttpStatus.CREATED).body(
                service.inserir(dto)
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<ArmazenamentoDTO> atualizar(@PathVariable int id, @RequestBody ArmazenamentoDTO dto) throws SQLException{
            return ResponseEntity.ok(service.atualizar(id, dto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) throws SQLException{
            service.deletar(id);
            return ResponseEntity.ok().build();

    }
}
