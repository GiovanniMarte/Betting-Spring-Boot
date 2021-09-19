package com.giovanni.bettingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MatchDto {
    private int id;
    private Short goalsHome;
    private Short goalsAway;
    private Date date;
    private String teamHome;
    private String teamAway;
}
