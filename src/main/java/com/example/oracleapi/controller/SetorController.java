package com.example.oracleapi.controller;

import com.example.oracleapi.dto.SetorDTO;
import com.example.oracleapi.exception.NomeSetorInvalidoException;
import com.example.oracleapi.service.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/setores")
public class SetorController {

    @Autowired
    private SetorService setorService;

    @GetMapping
    public ResponseEntity<List<SetorDTO>> listarSetores() throws SQLException {
        return ResponseEntity.ok(setorService.listarSetores());
    }

    @PostMapping
    public ResponseEntity<SetorDTO> criarSetor(@RequestBody SetorDTO setorDTO) throws SQLException {
        if (setorDTO.getNome() == null || setorDTO.getNome().trim().isEmpty()) {
            throw new NomeSetorInvalidoException();
        }
        return ResponseEntity.ok(setorService.criarSetor(setorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarSetor(@PathVariable int id, @RequestBody SetorDTO dto) throws SQLException {
        setorService.atualizarSetor(id, dto.getNome());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSetor(@PathVariable int id) throws SQLException {
        setorService.deletarSetor(id);
        return ResponseEntity.ok().build();
    }
}
