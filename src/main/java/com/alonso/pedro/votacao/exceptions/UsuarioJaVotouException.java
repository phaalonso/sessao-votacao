package com.alonso.pedro.votacao.exceptions;

public class UsuarioJaVotouException extends RuntimeException {
    public UsuarioJaVotouException(String message) {
        super(message);
    }
}
