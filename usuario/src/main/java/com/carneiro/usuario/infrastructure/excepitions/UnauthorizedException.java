package com.carneiro.usuario.infrastructure.excepitions;

import javax.naming.AuthenticationException;

public class UnauthorizedException extends AuthenticationException {
    public UnauthorizedException(String message) {
        super(message);
    }
    public UnauthorizedException(String message, Throwable throwable){
        super(message);
    }
}
