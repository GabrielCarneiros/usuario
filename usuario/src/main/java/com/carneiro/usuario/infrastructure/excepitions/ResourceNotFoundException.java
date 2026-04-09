package com.carneiro.usuario.infrastructure.excepitions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String messagem) {
        super(messagem);
    }

    public ResourceNotFoundException(String messagem, Throwable throwable) {
        super(messagem, throwable);
    }
}
