package org.example.birdsservice.service;

import org.example.birdsservice.service.dto.BirdDTO;

import java.util.List;
import java.util.Optional;

public interface BirdService {
    BirdDTO create(BirdDTO birdDTO);
    boolean existsById(Long id);
    BirdDTO update(BirdDTO birdDTO);
    Optional<BirdDTO> partialUpdate(BirdDTO birdDTO);
    void delete(Long id);
    List<BirdDTO> findAll();
    Optional<BirdDTO> findById(Long id);
}
