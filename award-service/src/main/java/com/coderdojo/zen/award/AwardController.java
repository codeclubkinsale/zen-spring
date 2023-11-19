package com.coderdojo.zen.award;

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

@Tag(name = "Award", description = "the Award API")
@RestController
@RequestMapping("/api/awards")
class AwardController {

    private static final Logger log = LoggerFactory.getLogger(AwardController.class);
    private final AwardRepository repository;

    public AwardController(AwardRepository repository) {
        this.repository = repository;
    }
    @Operation(summary = "Create user", description = "This can only be done by the logged in user.", tags = { "user" })
    @GetMapping("")
    List<Award> findAll() {
        return repository.findAll();
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete Tweet"),
            @ApiResponse(responseCode = "404", description = "tweet not found")
    })
    @GetMapping("/{id}")
    Optional<Award> findById(@PathVariable Integer id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(AwardNotFoundException::new));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Award save(@RequestBody @Valid Award award) {
        return repository.save(award);
    }

    @PutMapping("/{id}")
    Award update(@PathVariable Integer id, @RequestBody Award award) {
        Optional<Award> existing = repository.findById(id);
        if(existing.isPresent()) {
            Award updatedAward = new Award(existing.get().id(),
                    existing.get().name(),
                    award.description(),
                    award.image(),
                    existing.get().version());

            return repository.save(updatedAward);
        } else {
            throw new AwardNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}
