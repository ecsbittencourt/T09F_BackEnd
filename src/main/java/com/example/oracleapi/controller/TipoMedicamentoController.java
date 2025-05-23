package com.example.oracleapi.controller;

import com.example.oracleapi.dto.TipoMedicamentoDTO;
import com.example.oracleapi.service.TipoMedicamentoService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-medicamentos")
public class TipoMedicamentoController {

    private final TipoMedicamentoService tipoMedicamentoService;

    public TipoMedicamentoController(TipoMedicamentoService tipoMedicamentoService) {
        this.tipoMedicamentoService = tipoMedicamentoService;
    }

    @GetMapping
    public ResponseEntity<List<TipoMedicamentoDTO>> getTipoMedicamento() throws SQLException {
        return ResponseEntity.ok(
                tipoMedicamentoService.buscarTiposMedicamentos()
        );
    }

    @PostMapping
    public ResponseEntity<TipoMedicamentoDTO> postTipoMedicamento(@RequestBody TipoMedicamentoDTO dto) throws SQLException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                tipoMedicamentoService.inserirTipoMedicamento(dto)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTipoMedicamento(@PathVariable Integer id) throws SQLException{
        tipoMedicamentoService.deletarTipoMedicamento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<TipoMedicamentoDTO> putTipoMedicamento(@PathVariable Integer id, @RequestBody TipoMedicamentoDTO dto) throws SQLException{
        if(!id.equals(dto.id())) throw new RuntimeException("Erro! Ids não correspondem.");
        return ResponseEntity.ok().body(tipoMedicamentoService.atualizarTipoMedicamento(dto));
    }
}
