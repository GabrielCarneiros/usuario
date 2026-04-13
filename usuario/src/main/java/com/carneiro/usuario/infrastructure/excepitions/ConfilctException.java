package com.carneiro.usuario.infrastructure.excepitions;

public class ConfilctException extends RuntimeException {
    public ConfilctException(String mensagem){
        super(mensagem);
    }
    public  ConfilctException(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
