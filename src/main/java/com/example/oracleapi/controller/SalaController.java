package com.example.oracleapi.controller;

import com.example.oracleapi.dto.SalaDTO;
import com.example.oracleapi.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.status(500).body("Erro ao criar sala: " + e.getMessage());
        }
    }
}