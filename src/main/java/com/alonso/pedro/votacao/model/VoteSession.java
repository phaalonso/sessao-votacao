package com.alonso.pedro.votacao.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record VoteSession(
        @Id Long id,
        Long pautaId,
        Long positiveVotes,
        Long negativeVotes,
        Long totalVotes,
        LocalDateTime sessionStart,
        LocalDateTime sessionEnd
) {
}
