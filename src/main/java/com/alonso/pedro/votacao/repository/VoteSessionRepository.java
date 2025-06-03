package com.alonso.pedro.votacao.repository;

import com.alonso.pedro.votacao.model.VoteSession;
import com.alonso.pedro.votacao.model.CreateVoteSessionRequest;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VoteSessionRepository extends CrudRepository<VoteSession, Long> {
    @Query("SELECT * FROM vote_session WHERE id = :id FOR UPDATE")
    Optional<VoteSession> findByIdForUpdate(long id);

    @Query("""
    INSERT INTO vote_session(pauta_id, session_start, session_end)
    VALUES (:pautaId, now(), :sessionEnd)
    RETURNING *
    """)
    VoteSession save(Long pautaId, LocalDateTime sessionEnd);

    @Query("""
    UPDATE vote_session
    SET positive_votes = positive_votes + 1,
        total_votes = total_votes + 1
    WHERE id = :sessionId
      AND session_end > NOW()
    RETURNING *
    """)
    VoteSession countPositiveVote(Long sessionId);

    @Query("""
    UPDATE vote_session
    SET negative_votes = negative_votes + 1,
        total_votes = total_votes + 1
    WHERE id = :sessionId
      AND session_end > NOW()
    RETURNING *
    """)
    VoteSession countNegativeVote(Long sessionId);

    List<VoteSession> findByPautaId(Long id);
}
