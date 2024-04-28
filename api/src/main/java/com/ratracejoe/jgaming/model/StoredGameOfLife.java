package com.ratracejoe.jgaming.model;

import java.util.UUID;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("gameOfLife")
public record StoredGameOfLife(UUID id, String board) {}
