package com.alonso.pedro.votacao.repository;

import com.alonso.pedro.votacao.model.Pauta;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PautaRepository extends CrudRepository<Pauta, Long> {
    Optional<Pauta> findPautaById(Long id);
}
