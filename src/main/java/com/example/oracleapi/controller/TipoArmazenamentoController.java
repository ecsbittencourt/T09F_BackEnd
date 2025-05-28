package com.example.oracleapi.controller;


import com.example.oracleapi.dto.TipoArmazenamentoDTO;
import com.example.oracleapi.dto.TipoArmazenamentoTipoMedicamentoDTO;
import com.example.oracleapi.request.PostTipoArmazenamentoRequest;
import com.example.oracleapi.service.TipoArmazenamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-armazenamento")
public class TipoArmazenamentoController {

    private final TipoArmazenamentoService tipoArmazenamentoService;

    public TipoArmazenamentoController(TipoArmazenamentoService tipoArmazenamentoService) {
        this.tipoArmazenamentoService = tipoArmazenamentoService;
    }

    @GetMapping
    public ResponseEntity<List<TipoArmazenamentoDTO>> getTipoArmazenamento() throws SQLException {
        return ResponseEntity.ok(
                tipoArmazenamentoService.buscarTipoArmazenamento()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<TipoArmazenamentoDTO> getTipoArmazenamentoPorId(@PathVariable Integer id) throws SQLException {
        return ResponseEntity.ok(
                tipoArmazenamentoService.buscarTipoArmazenamentoPorId(id)
        );
    }

    @GetMapping("{id}/tipos-medicamento")
    public ResponseEntity<List<TipoArmazenamentoTipoMedicamentoDTO>> getTipoArmazenamentoTipoMedicamento(@PathVariable Integer id) throws SQLException{
        return ResponseEntity.ok(
                tipoArmazenamentoService.buscarTipoArmazenamentoTipoMedicamento(id)
        );
    }

    @PostMapping
    public ResponseEntity<TipoArmazenamentoDTO> postTipoArmazenamento(@RequestBody PostTipoArmazenamentoRequest request) throws SQLException{
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoArmazenamentoService.inserirTipoArmazenamento(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTipoArmazenamento(@PathVariable Integer id) throws SQLException {
        tipoArmazenamentoService.deletarTipoArmazenamento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<TipoArmazenamentoDTO> putTipoArmazenamento(@PathVariable Integer id, @RequestBody PostTipoArmazenamentoRequest request) throws SQLException{
        if(!id.equals(request.id())) {
            throw new RuntimeException("Ids não correspondem!");
        }
        return ResponseEntity.ok(
                tipoArmazenamentoService.atualizarTipoArmazenamento(request)
        );

    }
}
