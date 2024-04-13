package com.ratracejoe.jgaming.controller;

import com.ratracejoe.jgaming.service.gol.IGameOfLifeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GameOfLifeController {

    private final IGameOfLifeService service;

    @PostMapping("/iterate/{id}")
    public void iterate(@PathVariable("id") UUID id) {
        service.iterate(id);
    }
}
