package com.example.oracleapi.controller;

import com.example.oracleapi.dto.TipoEstoqueDTO;
import com.example.oracleapi.service.TipoEstoqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-estoque")
public class TipoEstoqueController {

    private final TipoEstoqueService tipoEstoqueService;

    public TipoEstoqueController(TipoEstoqueService tipoEstoqueService) {
        this.tipoEstoqueService = tipoEstoqueService;
    }

    @GetMapping
    public ResponseEntity<List<TipoEstoqueDTO>> getTipoEstoque() throws SQLException {
        return ResponseEntity.ok(tipoEstoqueService.buscarTiposEstoque());
    }

    @PostMapping
    public ResponseEntity<TipoEstoqueDTO> postTipoEstoque(@RequestBody TipoEstoqueDTO dto) throws SQLException {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoEstoqueService.inserirTipoEstoque(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoEstoqueDTO> putTipoEstoque(@PathVariable Integer id, @RequestBody TipoEstoqueDTO dto) throws SQLException{
        if(!id.equals(dto.id())) {
           throw new RuntimeException("Path id is different from body id");
        }
        return ResponseEntity.status(HttpStatus.OK).body(tipoEstoqueService.atualizarTipoEstoque(
                id, dto
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TipoEstoqueDTO> deleteTipoEstoque(@PathVariable Integer id) throws SQLException{
        tipoEstoqueService.deletarTipoEstoque(id);
        return ResponseEntity.noContent().build();
    }

}
