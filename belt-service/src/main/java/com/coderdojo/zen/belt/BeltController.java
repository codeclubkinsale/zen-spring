package com.coderdojo.zen.belt;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Javadoc
 */
@RestController
@RequestMapping("/api/belts")
class BeltController {

    private static final Logger log = LoggerFactory.getLogger(BeltController.class);
    private final BeltRepository repository;

    public BeltController(BeltRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    List<Belt> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Belt> findById(@PathVariable Integer id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(BeltNotFoundException::new));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Belt save(@RequestBody @Valid Belt belt) {
        return repository.save(belt);
    }

    @PutMapping("/{id}")
    Belt update(@PathVariable Integer id, @RequestBody Belt belt) {
        Optional<Belt> existing = repository.findById(id);
        if(existing.isPresent()) {
            Belt updatedBelt = new Belt(existing.get().id(),
                    existing.get().name(),
                    belt.description(),
                    belt.imageURL());

            return repository.save(updatedBelt);
        } else {
            throw new BeltNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}