package com.coderdojo.zen.badge;

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
@Tag(name = "Badge", description = "the Badge API")
@RestController
@RequestMapping("/api/badges")
class BadgeController {

    /**
     * Javadoc
     */
    private final BadgeRepository repository;

    /**
     * Javadoc
     *
     * @param repository Example
     */
    BadgeController(BadgeRepository repository) {
        this.repository = repository;
    }

    /**
     * Javadoc
     *
     * @return Example
     */
    @Operation(summary = "Create user", description = "This can only be done by the logged in user.", tags = { "user" })
    @GetMapping("")
    List<Badge> findAll() {
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
    Optional<Badge> findById(@PathVariable Integer id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(BadgeNotFoundException::new));
    }

    /**
     * Javadoc
     *
     * @param badge Example
     * @return Example
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Badge save(@RequestBody @Valid Badge badge) {
        return repository.save(badge);
    }

    /**
     * Javadoc
     *
     * @param id Example
     * @param badge Example
     * @return Example
     */
    @PutMapping("/{id}")
    Badge update(@PathVariable Integer id, @RequestBody Badge badge) {
        Optional<Badge> existing = repository.findById(id);
        if(existing.isPresent()) {
            Badge updatedBadge = new Badge(existing.get().id(),
                    existing.get().name(),
                    badge.description(),
                    badge.image(),
                    existing.get().version());

            return repository.save(updatedBadge);
        } else {
            throw new BadgeNotFoundException();
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
