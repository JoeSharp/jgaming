package com.ratracejoe.jgaming.controller;

import static uk.co.joesharpcs.gaming.gol.GolPatterns.NAMED_PATTERNS;

import com.ratracejoe.jgaming.exception.GameNotFoundException;
import com.ratracejoe.jgaming.exception.InvalidGameParameters;
import com.ratracejoe.jgaming.model.IdentifiedGameOfLife;
import com.ratracejoe.jgaming.service.gol.IGameOfLifeService;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gol")
@RequiredArgsConstructor
public class GameOfLifeController {

  private final IGameOfLifeService service;

  @GetMapping("/template/names")
  public Set<String> getListOfTemplates() {
    return NAMED_PATTERNS.keySet();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create/grid")
  public IdentifiedGameOfLife create(@RequestBody List<List<Boolean>> grid) {
    return service.create(grid);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create")
  public IdentifiedGameOfLife create() {
    return service.create();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create/{name}")
  public IdentifiedGameOfLife create(@PathVariable("name") String namedPattern)
      throws InvalidGameParameters {
    return service.create(namedPattern);
  }

  @PostMapping("/iterate/{id}")
  public IdentifiedGameOfLife iterate(@PathVariable("id") UUID id) throws GameNotFoundException {
    return service.iterate(id);
  }

  @GetMapping("/{id}")
  public IdentifiedGameOfLife get(@PathVariable("id") UUID id) throws GameNotFoundException {
    return service.get(id);
  }

  @DeleteMapping("/{id}")
  public void destroy(@PathVariable("id") UUID id) {
    service.destroy(id);
  }
}
