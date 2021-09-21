package com.giovanni.bettingapp.service;

import com.giovanni.bettingapp.dto.BetDto;
import com.giovanni.bettingapp.dto.MatchDto;
import com.giovanni.bettingapp.exception.ResourceNotFoundException;
import com.giovanni.bettingapp.mapper.BetMapper;
import com.giovanni.bettingapp.mapper.MatchMapper;
import com.giovanni.bettingapp.model.Match;
import com.giovanni.bettingapp.model.Team;
import com.giovanni.bettingapp.repository.MatchRepository;
import com.giovanni.bettingapp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.giovanni.bettingapp.util.ConstantUtil.*;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final MatchMapper matchMapper;
    private final BetMapper betMapper;

    public List<MatchDto> getMatches() {
        List<Match> matches = matchRepository.findAll();
        return matchMapper.toMatchDtoList(matches);
    }

    public MatchDto getMatch(int id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MATCH_WITH_ID + id + NOT_FOUND));
        return matchMapper.toMatchDto(match);
    }

    public List<BetDto> getBetsByMatch(int id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MATCH_WITH_ID + id + NOT_FOUND));
        return betMapper.toBetDtoList(match.getBets());
    }

    public MatchDto saveMatch(Match match) {
        match.setTeamHome(findTeam(match.getTeamHome()));
        match.setTeamAway(findTeam(match.getTeamAway()));

        Match savedMatch = matchRepository.save(match);
        return matchMapper.toMatchDto(savedMatch);
    }

    public MatchDto updateMatch(int id, Match newMatch) {
        return matchRepository.findById(id)
                .map(match -> {
                    match.setGoalsHome(newMatch.getGoalsHome());
                    match.setGoalsAway(newMatch.getGoalsAway());
                    match.setTeamHome(findTeam(newMatch.getTeamHome()));
                    match.setTeamAway(findTeam(newMatch.getTeamAway()));
                    match.setDate(newMatch.getDate());

                    Match updatedMatch = matchRepository.save(match);
                    return matchMapper.toMatchDto(updatedMatch);
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
