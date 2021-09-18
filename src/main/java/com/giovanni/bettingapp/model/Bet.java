package com.giovanni.bettingapp.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Short goals_home;
    private Short goals_away;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_match", referencedColumnName = "id")
    private Match match;
}
