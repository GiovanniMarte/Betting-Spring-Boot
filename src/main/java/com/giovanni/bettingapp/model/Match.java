package com.giovanni.bettingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @PositiveOrZero
    @Column(name = "goals_home")
    private Short goalsHome = 0;

    @PositiveOrZero
    @Column(name = "goals_away")
    private Short goalsAway = 0;

    @NotNull
    private Date date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_home", referencedColumnName = "id")
    private Team teamHome;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_away", referencedColumnName = "id")
    private Team teamAway;

    @JsonIgnore
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<Bet> bets;
}
