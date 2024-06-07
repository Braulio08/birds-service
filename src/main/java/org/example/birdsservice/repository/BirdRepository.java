package org.example.birdsservice.repository;

import org.example.birdsservice.domain.Bird;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface BirdRepository extends JpaRepository<Bird, Long> {
}
