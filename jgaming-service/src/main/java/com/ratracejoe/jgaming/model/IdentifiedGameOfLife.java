package com.ratracejoe.jgaming.model;

import java.util.UUID;
import uk.co.joesharpcs.gaming.gol.GameOfLife;

public record IdentifiedGameOfLife(UUID id, GameOfLife game) {}
