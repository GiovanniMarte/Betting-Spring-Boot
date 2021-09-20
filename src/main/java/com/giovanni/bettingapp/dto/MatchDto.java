package com.giovanni.bettingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDto {
    private int id;
    private Short goalsHome;
    private Short goalsAway;
    private Date date;
    private String teamHome;
    private String teamAway;
}
