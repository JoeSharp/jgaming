package com.ratracejoe.jgaming.model;

import java.util.List;
import java.util.UUID;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("game")
public record Game(
    UUID id, String createdBy, GameType gameType, String userDescription, List<String> players) {}
