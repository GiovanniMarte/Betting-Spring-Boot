package com.giovanni.bettingapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "teamHome", cascade = CascadeType.ALL)
    private List<Match> matchesHome;

    @OneToMany(mappedBy = "teamAway", cascade = CascadeType.ALL)
    private List<Match> matchesAway;
}
