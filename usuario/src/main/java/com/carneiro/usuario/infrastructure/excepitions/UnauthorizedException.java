package com.carneiro.usuario.infrastructure.excepitions;


public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
    public UnauthorizedException(String message, Throwable throwable){
        super(message);
    }
}
