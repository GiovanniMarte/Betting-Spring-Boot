package com.giovanni.bettingapp.service;

import com.giovanni.bettingapp.exception.ConflictException;
import com.giovanni.bettingapp.exception.ResourceNotFoundException;
import com.giovanni.bettingapp.model.Match;
import com.giovanni.bettingapp.model.Team;
import com.giovanni.bettingapp.repository.MatchRepository;
import com.giovanni.bettingapp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.giovanni.bettingapp.util.AppConstant.*;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public List<Match> getMatches() {
        return matchRepository.findAll();
    }

    public Match getMatch(int id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MATCH_WITH_ID + id + NOT_FOUND));
    }

    public Match addMatch(Match match) {
        match.setTeamHome(findTeam(match.getTeamHome()));
        match.setTeamAway(findTeam(match.getTeamAway()));
        return matchRepository.save(match);
    }

    public Match updateMatch(int id, Match newMatch) {
        return matchRepository.findById(id)
                .map(match -> {
                    match.setGoalsHome(newMatch.getGoalsHome());
                    match.setGoalsAway(newMatch.getGoalsAway());
                    match.setTeamHome(findTeam(newMatch.getTeamHome()));
                    match.setTeamAway(findTeam(newMatch.getTeamAway()));
                    match.setDate(newMatch.getDate());
                    return matchRepository.save(match);
                })
                .orElseThrow(() -> new ResourceNotFoundException(MATCH_WITH_ID + id + NOT_FOUND));
    }

    public void deleteMatch(int id) {
        if (!matchRepository.existsById(id)) {
            throw new ResourceNotFoundException(MATCH_WITH_ID + id + NOT_FOUND);
        }
        matchRepository.deleteById(id);
    }

    private Team findTeam(Team team) {
        return teamRepository.findById(team.getId())
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_WITH_ID + team.getId() + NOT_FOUND));
    }
}
