package com.alonso.pedro.votacao.service;

import com.alonso.pedro.votacao.exceptions.NotFoundException;
import com.alonso.pedro.votacao.model.CreatePautaRequest;
import com.alonso.pedro.votacao.model.Pauta;
import com.alonso.pedro.votacao.repository.PautaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaService {
    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Pauta create(CreatePautaRequest request) {
        var pauta = new Pauta(null, request.title(), request.description(), null, null);

        return pautaRepository.save(pauta);
    }

    public Pauta getPautaById(Long id) {
        return pautaRepository.findPautaById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Pauta %d não encontrada", id)));
    }

    public List<Pauta> getPautas() {
        return (List<Pauta>) pautaRepository.findAll();
    }

    public void delete(Long id) {
        pautaRepository.deleteById(id);
    }
}
