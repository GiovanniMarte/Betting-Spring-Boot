package com.giovanni.bettingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BetDto {
    private int id;
    private Short goalsHome;
    private Short goalsAway;
    private String user;
    private MatchDto match;
}
