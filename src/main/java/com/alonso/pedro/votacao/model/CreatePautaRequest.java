package com.alonso.pedro.votacao.model;

import jakarta.validation.constraints.NotBlank;

public record CreatePautaRequest(
        @NotBlank(message = "Titulo é obrigatório") String title,
        @NotBlank(message = "Descrição é obrigatório") String description
) {
}
