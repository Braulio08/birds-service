package org.example.birdsservice.service.dto;

import lombok.Data;
import org.example.birdsservice.domain.enums.BirdColor;
import org.example.birdsservice.domain.enums.BirdType;

import java.io.Serializable;
import java.time.Instant;
@Data
public class BirdDTO {
    private Long id;
    private String name;
    private BirdType birdType;
    private BirdColor birdColor;
    private Instant createdDate;
    private String description;
}
