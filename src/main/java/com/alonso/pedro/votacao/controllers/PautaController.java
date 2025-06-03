package com.alonso.pedro.votacao.controllers;

import com.alonso.pedro.votacao.model.CreatePautaRequest;
import com.alonso.pedro.votacao.model.Pauta;
import com.alonso.pedro.votacao.model.VoteSession;
import com.alonso.pedro.votacao.service.PautaService;
import com.alonso.pedro.votacao.service.VoteSessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pauta")
public class PautaController {

    private final PautaService pautaService;
    private final VoteSessionService voteSessionService;

    public PautaController(PautaService pautaService, VoteSessionService voteSessionService) {
        this.pautaService = pautaService;
        this.voteSessionService = voteSessionService;
    }

    @PostMapping
    public ResponseEntity<Pauta> create(@RequestBody @Valid CreatePautaRequest request) {
        var pauta = pautaService.create(request);

        return ResponseEntity.status(201)
                .body(pauta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> getPauta(@PathVariable Long id) {
        var result = pautaService.getPautaById(id);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/sessions")
    public ResponseEntity<List<VoteSession>> getPautaSessions(@PathVariable Long id) {
        var result = voteSessionService.getPautaSessions(id);

        return ResponseEntity.ok(result);
    }

    @GetMapping()
    public ResponseEntity<List<Pauta>> getAllPauta() {
        var result = pautaService.getPautas();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePauta(@PathVariable Long id) {
        pautaService.delete(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }
}
