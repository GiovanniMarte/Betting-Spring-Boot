package com.giovanni.bettingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BetDto {
    private int id;
    private Short goalsHome;
    private Short goalsAway;
    private String user;
    private MatchDto match;
}
