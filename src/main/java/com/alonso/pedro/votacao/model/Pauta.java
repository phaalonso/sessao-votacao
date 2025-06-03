package com.alonso.pedro.votacao.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record Pauta(
        @Id Long id ,
        String title,
        String description,
        LocalDateTime creationDate,
        LocalDateTime updateDate
) {
}
