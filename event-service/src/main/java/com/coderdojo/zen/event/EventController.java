package com.coderdojo.zen.event;

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
@Tag(name = "Event", description = "the Event API")
@RestController
@RequestMapping("/api/events")
class EventController {

    /**
     * Javadoc
     */
    private final EventRepository repository;

    /**
     * Javadoc
     *
     * @param repository Example
     */
    EventController(EventRepository repository) {
        this.repository = repository;
    }

    /**
     * Javadoc
     *
     * @return Example
     */
    @Operation(summary = "Create user", description = "This can only be done by the logged in user.", tags = { "user" })
    @GetMapping("")
    List<Event> findAll() {
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
    Optional<Event> findById(@PathVariable Integer id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(EventNotFoundException::new));
    }

    /**
     * Javadoc
     *
     * @param event Example
     * @return Example
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Event save(@RequestBody @Valid Event event) {
        return repository.save(event);
    }

    /**
     * Javadoc
     *
     * @param id Example
     * @param event Example
     * @return Example
     */
    @PutMapping("/{id}")
    Event update(@PathVariable Integer id, @RequestBody Event event) {
        Optional<Event> existing = repository.findById(id);
        if(existing.isPresent()) {
            Event updatedEvent = new Event(existing.get().id(),
                    existing.get().name(),
                    event.description(),
                    event.image(),
                    existing.get().version());

            return repository.save(updatedEvent);
        } else {
            throw new EventNotFoundException();
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
