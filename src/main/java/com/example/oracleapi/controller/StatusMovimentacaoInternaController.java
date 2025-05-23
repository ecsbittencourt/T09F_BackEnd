package com.example.oracleapi.controller;


import com.example.oracleapi.dto.StatusMovimentacaoInternaDTO;
import com.example.oracleapi.service.StatusMovimentacaoInternaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/status-movimentacao-interna")
public class StatusMovimentacaoInternaController {

    private final StatusMovimentacaoInternaService statusMovimentacaoInternaService;

    public StatusMovimentacaoInternaController(StatusMovimentacaoInternaService statusMovimentacaoInternaService) {
        this.statusMovimentacaoInternaService = statusMovimentacaoInternaService;
    }

    @GetMapping
    public ResponseEntity<List<StatusMovimentacaoInternaDTO>> getStatusMovimentacaoInterna() throws SQLException {
        return ResponseEntity.ok().body(statusMovimentacaoInternaService.buscarTiposMedicamentos());
    }

    @PostMapping
    public ResponseEntity<StatusMovimentacaoInternaDTO> postStatusMovimentacaoInterna(@RequestBody StatusMovimentacaoInternaDTO dto) throws SQLException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                statusMovimentacaoInternaService.inserirStatusMovimentacaoInterna(dto)
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<StatusMovimentacaoInternaDTO> putStatusMovimentacaoInterna(@PathVariable Integer id, @RequestBody StatusMovimentacaoInternaDTO dto) throws SQLException{
        if(!id.equals(dto.id())) {
            throw new RuntimeException("Erro! Identificadores passados não correspondem.");
        }
        return ResponseEntity.ok(
                statusMovimentacaoInternaService.atualizarStatusMovimentacaoInterna(dto)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStatusMovimentacaoInterna(@PathVariable Integer id) throws SQLException{
        statusMovimentacaoInternaService.deleteStatusMovimentacaoInterna(id);
        return ResponseEntity.noContent().build();
    }
}
