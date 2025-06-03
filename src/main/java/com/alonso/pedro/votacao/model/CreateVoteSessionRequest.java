package com.alonso.pedro.votacao.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateVoteSessionRequest(
        @NotNull(message = "Campo deve ser preenchido") @Min(value = 1, message = "O número deve ser no mínimo 1") Long pautaId,
        @NotNull(message = "Campo deve ser preenchido") @Min(value = 1, message = "Deve durar no mínimo 1 minuto") Long durationInMinutes
) {
}
