package org.example.birdsservice.service.impl;

import org.example.birdsservice.domain.Bird;
import org.example.birdsservice.repository.BirdRepository;
import org.example.birdsservice.service.BirdService;
import org.example.birdsservice.service.dto.BirdDTO;
import org.example.birdsservice.service.mapper.BirdMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BirdServiceImpl implements BirdService {
    private final BirdMapper birdMapper;
    private final BirdRepository birdRepository;

    public BirdServiceImpl(BirdMapper birdMapper, BirdRepository birdRepository) {
        this.birdMapper = birdMapper;
        this.birdRepository = birdRepository;
    }

    @Override
    public BirdDTO create(BirdDTO birdDTO) {
        Bird bird = birdMapper.toEntity(birdDTO);
        bird = birdRepository.save(bird);
        return birdMapper.toDto(bird);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return birdRepository.existsById(id);
    }

    @Override
    public BirdDTO update(BirdDTO birdDTO) {
        Bird bird = birdMapper.toEntity(birdDTO);
        bird = birdRepository.save(bird);
        return birdMapper.toDto(bird);
    }

    @Override
    public Optional<BirdDTO> partialUpdate(BirdDTO birdDTO) {
        return birdRepository.findById(birdDTO.getId()).map(bird -> {
            birdMapper.partialUpdate(bird, birdDTO);
            return bird;
        }).map(birdRepository::save).map(birdMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        birdRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BirdDTO> findAll() { return birdMapper.toDto(birdRepository.findAll()); }

    @Override
    @Transactional(readOnly = true)
    public Optional<BirdDTO> findById(Long id) {
        return birdRepository.findById(id).map(birdMapper::toDto);
    }
}
