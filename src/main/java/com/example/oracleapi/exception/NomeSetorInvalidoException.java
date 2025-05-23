package com.example.oracleapi.exception;

public class NomeSetorInvalidoException extends RuntimeException {
    public NomeSetorInvalidoException(){
        super("Nome do setor inválido");
    }
    public NomeSetorInvalidoException(String message){
        super(message);
    }
}
