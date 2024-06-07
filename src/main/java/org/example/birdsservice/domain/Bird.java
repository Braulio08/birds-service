package org.example.birdsservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.example.birdsservice.domain.enums.BirdColor;
import org.example.birdsservice.domain.enums.BirdType;

import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "bird")
public class Bird {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "birdSequence")
    @SequenceGenerator(name = "birdSequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "bird_type")
    private BirdType birdType;

    @Enumerated(EnumType.STRING)
    @Column(name = "bird_color")
    private BirdColor birdColor;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "description")
    private String description;

}
