package com.alonso.pedro.votacao.repository;

import com.alonso.pedro.votacao.model.CreateVoteRequest;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VoteRepository {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public VoteRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    private static final String QUERY_SAVE_VOTE = """
            INSERT INTO vote (vote_session_id, associate_id, vote)
            VALUES (:voteSessionId, :associateId, :vote)
            """;

    public void save(Long sessionId, CreateVoteRequest request) {
        var params = new MapSqlParameterSource()
                .addValue("voteSessionId", sessionId)
                .addValue("associateId", request.associadoId())
                .addValue("vote", request.voto().toString());

        var rows = namedJdbcTemplate.update(
                QUERY_SAVE_VOTE,
                params
        );

        if (rows != 1) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(QUERY_SAVE_VOTE, 1, rows);
        }
    }
}
