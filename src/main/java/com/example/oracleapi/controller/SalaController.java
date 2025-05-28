package com.example.oracleapi.controller;

import com.example.oracleapi.dto.SalaDTO;
import com.example.oracleapi.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

// Caso queira liberar CORS diretamente no controller (opcional)
// Ajuste a origem conforme seu frontend
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RestController
@RequestMapping("/api/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<SalaDTO> criarSala(@RequestBody SalaDTO salaDTO) throws SQLException {
        salaService.criarSala(salaDTO);
        return ResponseEntity.status(201).body(salaDTO);
    }

    @GetMapping
    public ResponseEntity<List<SalaDTO>> listarSalas() throws SQLException {
        return ResponseEntity.ok(salaService.listarSalas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> buscarPorId(@PathVariable int id) throws SQLException {
        SalaDTO sala = salaService.buscarPorId(id);
        if (sala != null) {
            return ResponseEntity.ok(sala);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editarSala(@PathVariable int id, @RequestBody SalaDTO dto) throws SQLException {
        salaService.editarSala(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSala(@PathVariable int id) throws SQLException {
        salaService.deletarSala(id);
        return ResponseEntity.noContent().build();
    }
}
