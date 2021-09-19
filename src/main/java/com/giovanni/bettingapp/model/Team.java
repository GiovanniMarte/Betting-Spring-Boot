package com.giovanni.bettingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "teamHome", cascade = CascadeType.ALL)
    private List<Match> matchesHome;

    @JsonIgnore
    @OneToMany(mappedBy = "teamAway", cascade = CascadeType.ALL)
    private List<Match> matchesAway;
}
