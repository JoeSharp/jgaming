package com.ratracejoe.jgaming.dto;

import com.ratracejoe.jgaming.model.GameType;

public record NewGameDTO(String createdBy, GameType gameType, String description) {}
