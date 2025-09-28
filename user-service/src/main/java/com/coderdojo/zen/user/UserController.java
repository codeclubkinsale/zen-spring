package com.coderdojo.zen.user;

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
@Tag(name = "User", description = "the User API")
@RestController
@RequestMapping("/api/users")
class UserController {

  /**
   * Javadoc.
   */
  private final UserRepository repository;

  /**
   * Javadoc.
   *
   * @param repository Example
   */
  UserController(UserRepository repository) {
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
  List<User> findAll() {
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
  Optional<User> findById(@PathVariable Integer id) {
    return Optional.ofNullable(repository.findById(id).orElseThrow(UserNotFoundException::new));
  }

  /**
   * Javadoc.
   *
   * @param user Example
   * @return Example
   */
  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  User save(@RequestBody @Valid User user) {
    return repository.save(user);
  }

  /**
   * Javadoc.
   *
   * @param id   Example
   * @param user Example
   * @return Example
   */
  @PutMapping("/{id}")
  User update(@PathVariable Integer id, @RequestBody User user) {
    Optional<User> existing = repository.findById(id);
    if (existing.isPresent()) {
      User updatedUser = new User(existing.get().id(),
          existing.get().name(),
          user.description(),
          user.image(),
          existing.get().version());

      return repository.save(updatedUser);
    } else {
      throw new UserNotFoundException();
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
