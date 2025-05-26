package com.example.oracleapi.controller;

import com.example.oracleapi.dto.FornecedorDTO;

import com.example.oracleapi.service.FornecedorService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/fornecedores")

public class FornecedorController {

    @Autowired

    private FornecedorService fornecedorService;

    @PostMapping

    public ResponseEntity<String> criarFornecedor(@RequestBody FornecedorDTO fornecedorDTO) {

        try {

            fornecedorService.criarFornecedor(fornecedorDTO);

            return ResponseEntity.ok("Fornecedor criado com sucesso!");

        } catch (Exception e) {

            e.printStackTrace(); // para debug

            return ResponseEntity.status(500).body("Erro ao criar fornecedor: " + e.getMessage());

        }

    }

}

