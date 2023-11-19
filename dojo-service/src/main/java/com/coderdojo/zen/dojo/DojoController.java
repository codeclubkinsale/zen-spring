package com.coderdojo.zen.dojo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Dojo", description = "the Dojo API")
@RestController
@RequestMapping("/api/dojos")
class DojoController {

    private static final Logger log = LoggerFactory.getLogger(DojoController.class);
    private final DojoRepository repository;

    public DojoController(DojoRepository repository) {
        this.repository = repository;
    }
    @Operation(summary = "Create user", description = "This can only be done by the logged in user.", tags = { "user" })
    @GetMapping("")
    List<Dojo> findAll() {
        return repository.findAll();
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete Tweet"),
            @ApiResponse(responseCode = "404", description = "tweet not found")
    })
    @GetMapping("/{id}")
    Optional<Dojo> findById(@PathVariable Integer id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(DojoNotFoundException::new));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Dojo save(@RequestBody @Valid Dojo dojo) {
        return repository.save(dojo);
    }

    @PutMapping("/{id}")
    Dojo update(@PathVariable Integer id, @RequestBody Dojo dojo) {
        Optional<Dojo> existing = repository.findById(id);
        if(existing.isPresent()) {
            Dojo updatedDojo = new Dojo(existing.get().id(),
                    existing.get().name(),
                    dojo.description(),
                    dojo.image(),
                    existing.get().version());

            return repository.save(updatedDojo);
        } else {
            throw new DojoNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}
