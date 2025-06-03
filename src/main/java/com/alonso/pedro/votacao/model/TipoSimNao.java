package com.alonso.pedro.votacao.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoSimNao {
    SIM("Sim"),
    NAO("Não");

    @JsonValue
    private final String value;

    TipoSimNao(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
