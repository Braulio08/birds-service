package org.example.birdsservice.service.impl;

import org.example.birdsservice.domain.Bird;
import org.example.birdsservice.domain.enums.BirdColor;
import org.example.birdsservice.domain.enums.BirdType;
import org.example.birdsservice.repository.BirdRepository;
import org.example.birdsservice.service.dto.BirdDTO;
import org.example.birdsservice.service.mapper.BirdMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BirdServiceImplTest {
    @InjectMocks
    private BirdServiceImpl birdServiceImpl;
    @Mock
    private BirdMapper birdMapper;
    @Mock
    private BirdRepository birdRepository;

    BirdDTO birdDTO = new BirdDTO();
    Bird bird = new Bird();
    Bird savedBird = new Bird();
    BirdDTO savedBirdDTO = new BirdDTO();

    @Before
    public void setup() {
        birdServiceImpl = new BirdServiceImpl(
                birdMapper,
                birdRepository
        );
        Instant date = Instant.parse("2024-06-06T14:23:45.123456Z");

        birdDTO.setBirdColor(BirdColor.AZUL);
        birdDTO.setBirdType(BirdType.BOSQUE);
        birdDTO.setName("Pájaro azul");
        birdDTO.setCreatedDate(date);
        birdDTO.setDescription("Un pájaro azul del bosque");

        bird.setBirdColor(BirdColor.AZUL);
        bird.setBirdType(BirdType.BOSQUE);
        bird.setName("Pájaro azul");
        bird.setCreatedDate(date);
        bird.setDescription("Un pájaro azul del bosque");

        savedBird.setId(1L);
        savedBird.setBirdColor(BirdColor.AZUL);
        savedBird.setBirdType(BirdType.BOSQUE);
        savedBird.setName("Pájaro azul");
        savedBird.setCreatedDate(date);
        savedBird.setDescription("Un pájaro azul del bosque");

        savedBirdDTO.setId(1L);
        savedBirdDTO.setBirdColor(BirdColor.AZUL);
        savedBirdDTO.setBirdType(BirdType.BOSQUE);
        savedBirdDTO.setName("Pájaro azul");
        savedBirdDTO.setCreatedDate(date);
        savedBirdDTO.setDescription("Un pájaro azul del bosque");
    }

    @Test
    public void create() {

        when(birdMapper.toEntity(birdDTO)).thenReturn(bird);
        when(birdRepository.save(bird)).thenReturn(savedBird);
        when(birdMapper.toDto(savedBird)).thenReturn(savedBirdDTO);

        BirdDTO result = birdServiceImpl.create(birdDTO);

        assertEquals(result.getId(), savedBirdDTO.getId());

    }

    @Test
    public void existsById() {
        when(birdRepository.existsById(savedBirdDTO.getId())).thenReturn(true);

        boolean result = birdServiceImpl.existsById(savedBirdDTO.getId());

        assertTrue(result);
    }

    @Test
    public void update() {

        when(birdMapper.toEntity(savedBirdDTO)).thenReturn(savedBird);
        when(birdRepository.save(savedBird)).thenReturn(savedBird);
        when(birdMapper.toDto(savedBird)).thenReturn(savedBirdDTO);

        BirdDTO updatedResult = birdServiceImpl.update(savedBirdDTO);

        assertEquals(savedBirdDTO.getId(), updatedResult.getId());
    }

    @Test
    public void partialUpdate() {
        BirdDTO resultDTO = new BirdDTO();
        when(birdRepository.findById(savedBirdDTO.getId())).thenReturn(Optional.of(savedBird));

        doAnswer(invocation -> {
            Bird target = invocation.getArgument(0);
            BirdDTO source = invocation.getArgument(1);

            if (source.getDescription() != null) target.setDescription(source.getDescription());

            return null;
        }).when(birdMapper).partialUpdate(savedBird, savedBirdDTO);

        when(birdRepository.save(savedBird)).thenReturn(savedBird);
        when(birdMapper.toDto(savedBird)).thenReturn(savedBirdDTO);

        Optional<BirdDTO> partiallyUpdatedResult;
        partiallyUpdatedResult = birdServiceImpl.partialUpdate(savedBirdDTO);

        if(partiallyUpdatedResult.isPresent()){
            resultDTO = partiallyUpdatedResult.get();
        }

        assertEquals(savedBirdDTO.getId(), resultDTO.getId());
    }

    @Test
    public void delete() {
        doNothing().when(birdRepository).deleteById(savedBirdDTO.getId());

        birdServiceImpl.delete(savedBirdDTO.getId());

        verify(birdRepository, times(1)).deleteById(savedBirdDTO.getId());
    }

    @Test
    public void findAll() {
        when(birdRepository.findAll()).thenReturn(Collections.singletonList(savedBird));
        when(birdMapper.toDto(Collections.singletonList(savedBird))).thenReturn(Collections.singletonList(savedBirdDTO));
        List<BirdDTO> resultList = birdServiceImpl.findAll();

        assertEquals(1, resultList.size());
    }

    @Test
    public void findById() {
        when(birdRepository.findById(savedBirdDTO.getId())).thenReturn(Optional.of(savedBird));
        when(birdMapper.toDto(savedBird)).thenReturn(savedBirdDTO);
        BirdDTO resultDTO = new BirdDTO();

        Optional<BirdDTO> result = birdServiceImpl.findById(savedBirdDTO.getId());

        if(result.isPresent()){
            resultDTO = result.get();
        }

        assertEquals(savedBirdDTO.getId(), resultDTO.getId());
    }
}