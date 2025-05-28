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
    private UnidadeMedidaService unidadeMedidaService;

    @PostMapping
    public ResponseEntity<UnidadeMedidaDTO> criarUnidade(@RequestBody UnidadeMedidaDTO dto) throws SQLException {
        if (dto.getMedida() == null || dto.getMedida().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(unidadeMedidaService.criarUnidadeMedida(dto));
    }

    @GetMapping
    public ResponseEntity<List<UnidadeMedidaDTO>> listar() throws SQLException {
        return ResponseEntity.ok(unidadeMedidaService.listarUnidadesMedida());
    }
}
