package com.alonso.pedro.votacao.model;

import jakarta.validation.constraints.NotNull;

public record CreateVoteRequest(
        @NotNull Long associadoId,
        @NotNull TipoSimNao voto
) {
}
