package com.alonso.pedro.votacao.model;

import org.springframework.data.annotation.Id;

public record Vote(
        @Id Long id,
        Boolean isPositiveVote
) {
}
