package com.coderdojo.zen.dojo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Javadoc.
 */
@Tag(name = "Dojo", description = "the Dojo API")
@RestController
@RequestMapping("/api/dojos")
class DojoController {

  /**
   * Javadoc.
   */
  private final DojoRepository repository;

  /**
   * Javadoc.
   *
   * @param repository Example
   */
  DojoController(DojoRepository repository) {
    this.repository = repository;
  }

  /**
   * Javadoc.
   *
   * @return Example
   */
  @Operation(summary = "Create user",
      description = "This can only be done by the logged in user.", tags = {"user"})
  @GetMapping("")
  List<Dojo> findAll() {
    return repository.findAll();
  }

  /**
   * Javadoc.
   *
   * @param id Example
   * @return Example
   */
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "delete Tweet"),
      @ApiResponse(responseCode = "404", description = "tweet not found")
  })
  @GetMapping("/{id}")
  Optional<Dojo> findById(@PathVariable Integer id) {
    return Optional.ofNullable(repository.findById(id).orElseThrow(DojoNotFoundException::new));
  }

  /**
   * Javadoc.
   *
   * @param dojo Example
   * @return Example
   */
  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  Dojo save(@RequestBody @Valid Dojo dojo) {
    return repository.save(dojo);
  }

  /**
   * Javadoc.
   *
   * @param id   Example
   * @param dojo Example
   * @return Example
   */
  @PutMapping("/{id}")
  Dojo update(@PathVariable Integer id, @RequestBody Dojo dojo) {
    Optional<Dojo> existing = repository.findById(id);
    if (existing.isPresent()) {
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

  /**
   * Javadoc.
   *
   * @param id Example
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  void delete(@PathVariable Integer id) {
    repository.deleteById(id);
  }

}
