package com.ratracejoe.jgaming.model;

import java.util.List;
import java.util.UUID;

public record Game(
    UUID id, String createdBy, String gameType, String userDescription, List<String> players) {}
