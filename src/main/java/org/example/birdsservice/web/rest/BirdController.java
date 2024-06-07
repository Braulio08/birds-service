package org.example.birdsservice.web.rest;
import org.apache.coyote.BadRequestException;
import org.example.birdsservice.service.BirdService;
import org.example.birdsservice.service.dto.BirdDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BirdController {
    private final BirdService birdService;

    public BirdController(BirdService birdService) {
        this.birdService = birdService;
    }

    @PostMapping("/create-bird")
    public ResponseEntity<BirdDTO> createBird(@RequestBody BirdDTO birdDTO) throws Exception {
        if (birdDTO.getId() != null) {
            throw new BadRequestException("Un ave nueva no puede tener código");
        }
        BirdDTO result = birdService.create(birdDTO);
        return ResponseEntity.created(new URI("/api/createBird/" + result.getId())).body(result);
    }

    @PutMapping("/update-bird/{id}")
    public ResponseEntity<BirdDTO> updateBird(@PathVariable(value = "id") final Long id, @RequestBody BirdDTO birdDTO) throws Exception {
        checkBirdId(id, birdDTO);
        if (!birdService.existsById(id)) return ResponseEntity.notFound().build();
        BirdDTO result = birdService.update(birdDTO);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping(value="/update-bird/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BirdDTO> partialUpdateBird(@PathVariable(value = "id") final Long id, @RequestBody BirdDTO birdDTO) throws Exception {
        checkBirdId(id, birdDTO);
        if (!birdService.existsById(id)) return ResponseEntity.notFound().build();
        Optional<BirdDTO> result = birdService.partialUpdate(birdDTO);
        return result.map(dto -> ResponseEntity.ok().body(dto)).orElseGet(() -> ResponseEntity.internalServerError().body(null));
    }

    @DeleteMapping("/delete-bird/{id}")
    public ResponseEntity<Void> deleteBird(@PathVariable Long id) {
        if (!birdService.existsById(id)) return ResponseEntity.notFound().build();
        birdService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all-birds")
    public ResponseEntity<List<BirdDTO>> getAllBirds() {
        List<BirdDTO> birdDTOList = birdService.findAll();
        return ResponseEntity.ok().body(birdDTOList);
    }

    @GetMapping("/get-bird/{id}")
    public ResponseEntity<BirdDTO> getBird(@PathVariable Long id) {
        Optional<BirdDTO> result = birdService.findById(id);
        return result.map(dto -> ResponseEntity.ok().body(dto)).orElseGet(() -> ResponseEntity.internalServerError().body(null));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok().body("I'm alive!");
    }

    private void checkBirdId(@PathVariable("id") Long id, @RequestBody BirdDTO birdDTO) throws Exception {
        if (birdDTO.getId() == null) {
            throw new BadRequestException("Se requiere un ave con código para actualizar");
        }
        if (!Objects.equals(id, birdDTO.getId())) {
            throw new BadRequestException("El código proporcionado no coincide con el del ave");
        }
    }
}
