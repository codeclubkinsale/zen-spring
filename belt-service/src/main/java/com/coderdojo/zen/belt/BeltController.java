package com.coderdojo.zen.belt;

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
@Tag(name = "Belt", description = "the Belt API")
@RestController
@RequestMapping("/api/belts")
class BeltController {

  /**
   * Javadoc.
   */
  private final BeltRepository repository;

  /**
   * Javadoc.
   *
   * @param repository Example
   */
  BeltController(BeltRepository repository) {
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
  List<Belt> findAll() {
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
  Optional<Belt> findById(@PathVariable Integer id) {
    return Optional.ofNullable(repository.findById(id).orElseThrow(BeltNotFoundException::new));
  }

  /**
   * Javadoc.
   *
   * @param belt Example
   * @return Example
   */
  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  Belt save(@RequestBody @Valid Belt belt) {
    return repository.save(belt);
  }

  /**
   * Javadoc.
   *
   * @param id   Example
   * @param belt Example
   * @return Example
   */
  @PutMapping("/{id}")
  Belt update(@PathVariable Integer id, @RequestBody Belt belt) {
    Optional<Belt> existing = repository.findById(id);
    if (existing.isPresent()) {
      Belt updatedBelt = new Belt(existing.get().id(),
          existing.get().name(),
          belt.description(),
          belt.image(),
          existing.get().version());

      return repository.save(updatedBelt);
    } else {
      throw new BeltNotFoundException();
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
