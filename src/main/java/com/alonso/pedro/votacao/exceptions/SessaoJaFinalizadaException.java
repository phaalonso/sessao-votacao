package com.alonso.pedro.votacao.exceptions;

public class SessaoJaFinalizadaException extends RuntimeException{
    public SessaoJaFinalizadaException(String message) {
        super(message);
    }

    public SessaoJaFinalizadaException(String message, Throwable cause) {
        super(message, cause);
    }
}
