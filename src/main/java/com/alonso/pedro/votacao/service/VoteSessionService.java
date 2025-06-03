package com.alonso.pedro.votacao.service;

import com.alonso.pedro.votacao.exceptions.NotFoundException;
import com.alonso.pedro.votacao.exceptions.SessaoJaFinalizadaException;
import com.alonso.pedro.votacao.model.CreateVoteRequest;
import com.alonso.pedro.votacao.model.VoteSession;
import com.alonso.pedro.votacao.model.CreateVoteSessionRequest;
import com.alonso.pedro.votacao.repository.VoteRepository;
import com.alonso.pedro.votacao.repository.VoteSessionRepository;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoteSessionService {
    private static final Logger logger = LoggerFactory.getLogger(VoteSessionService.class);

    private final PautaService pautaService;
    private final VoteSessionRepository voteSessionRepository;
    private final VoteRepository voteRepository;

    public VoteSessionService(PautaService pautaService, VoteSessionRepository voteSessionRepository, VoteRepository voteRepository) {
        this.pautaService = pautaService;
        this.voteSessionRepository = voteSessionRepository;
        this.voteRepository = voteRepository;
    }

    public VoteSession save(CreateVoteSessionRequest request) {
        pautaService.getPautaById(request.pautaId());

        return voteSessionRepository.save(request.pautaId(), LocalDateTime.now().plusMinutes(request.durationInMinutes()));
    }

    @Transactional
    public VoteSession contabilizaVote(Long sessionId, CreateVoteRequest vote) {
        // TODO check first if vote already exists

        // this findForUpdate and lock on the row will be a problem for the performance of the application
        // we can prevent this from happening by using a cache mechanism to get the session data and verify the sessionEnd
        // in the application code, instead of the database
        // maybe this will introduce some cases where the vote happened after the session end, and it's necessary to
        // design a way to prevent that

        // lock the row for update, preventing other transaction updating it at the same time
        var session = voteSessionRepository.findByIdForUpdate(sessionId)
                .orElseThrow(() -> new NotFoundException("Sessão de votação não encontrada"));

        // validate if the session has expired
        // it is also validated during the update in the database
        if (session.sessionEnd().isBefore(LocalDateTime.now())) {
            throw new SessaoJaFinalizadaException(String.format("Sessão já finalizada as %s", session.sessionEnd()));
        }

        try {
            session = switch (vote.voto()) {
                case SIM -> voteSessionRepository.countPositiveVote(sessionId);
                case NAO -> voteSessionRepository.countNegativeVote(sessionId);
            };
        } catch (DataIntegrityViolationException e) {
            var ex = new SessaoJaFinalizadaException(String.format("Sessão já finalizada as %s", session.sessionEnd()), e);

            logger.error("Validação de expiração da sessão no java falhou", ex);

            throw ex;
        }

        voteRepository.save(sessionId, vote);

        return session;
    }

    public List<VoteSession> getPautaSessions(Long id) {
        return voteSessionRepository.findByPautaId(id);
    }
}
