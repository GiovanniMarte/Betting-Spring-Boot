package com.giovanni.bettingapp.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @PositiveOrZero
    @Column(name = "goals_home")
    private Short goalsHome;

    @PositiveOrZero
    @Column(name = "goals_away")
    private Short goalsAway;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_match", referencedColumnName = "id")
    private Match match;
}
