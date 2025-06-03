package com.alonso.pedro.votacao.controllers;

import com.alonso.pedro.votacao.model.CreatePautaRequest;
import com.alonso.pedro.votacao.model.CreateVoteRequest;
import com.alonso.pedro.votacao.model.CreateVoteSessionRequest;
import com.alonso.pedro.votacao.model.VoteSession;
import com.alonso.pedro.votacao.service.VoteSessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voteSession")
public class VoteSessionController {
    private final VoteSessionService voteSessionService;

    public VoteSessionController(VoteSessionService voteSessionService) {
        this.voteSessionService = voteSessionService;
    }

    @PostMapping
    public ResponseEntity<VoteSession> create(@RequestBody @Valid CreateVoteSessionRequest request){
        var result = voteSessionService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/{sessionId}/vote")
    public ResponseEntity<VoteSession> vote(@PathVariable Long sessionId, @RequestBody @Valid CreateVoteRequest request){
        var result = voteSessionService.contabilizaVote(sessionId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }
}
