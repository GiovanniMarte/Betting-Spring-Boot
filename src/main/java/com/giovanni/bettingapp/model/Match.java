package com.giovanni.bettingapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "goals_home")
    private Short goalsHome;
    @Column(name = "goals_away")
    private Short goalsAway;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_home", referencedColumnName = "id")
    private Team teamHome;

    @ManyToOne
    @JoinColumn(name = "id_away", referencedColumnName = "id")
    private Team teamAway;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<Bet> bets;
}
