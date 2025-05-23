package com.example.oracleapi.controller;

import com.example.oracleapi.dto.SetorDTO;
import com.example.oracleapi.exception.NomeSetorInvalidoException;
import com.example.oracleapi.service.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/setores")
public class SetorController {

    @Autowired
    private SetorService setorService;

    @PostMapping
    public ResponseEntity<SetorDTO> criarSetor(@RequestBody SetorDTO setorDTO) throws SQLException {
        if (setorDTO.getNome() == null || setorDTO.getNome().trim().isEmpty()) {
            throw new NomeSetorInvalidoException();
        }
            return ResponseEntity.ok(setorService.criarSetor(setorDTO));
    }
}
