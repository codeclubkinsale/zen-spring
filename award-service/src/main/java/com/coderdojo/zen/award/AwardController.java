package com.coderdojo.zen.award;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Javadoc
 */
@Tag(name = "Award", description = "the Award API")
@RestController
@RequestMapping("/api/awards")
class AwardController {

    /**
     * Javadoc
     */
    private final AwardRepository repository;

    /**
     * Javadoc
     *
     * @param repository Example
     */
    AwardController(AwardRepository repository) {
        this.repository = repository;
    }

    /**
     * Javadoc
     *
     * @return Example
     */
    @Operation(summary = "Create user", description = "This can only be done by the logged in user.", tags = { "user" })
    @GetMapping("")
    List<Award> findAll() {
        return repository.findAll();
    }

    /**
     * Javadoc
     *
     * @param id Example
     * @return Example
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete Tweet"),
            @ApiResponse(responseCode = "404", description = "tweet not found")
    })
    @GetMapping("/{id}")
    Optional<Award> findById(@PathVariable Integer id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(AwardNotFoundException::new));
    }

    /**
     * Javadoc
     *
     * @param award Example
     * @return Example
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Award save(@RequestBody @Valid Award award) {
        return repository.save(award);
    }

    /**
     * Javadoc
     *
     * @param id Example
     * @param award Example
     * @return Example
     */
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

    /**
     * Javadoc
     *
     * @param id Example
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}
