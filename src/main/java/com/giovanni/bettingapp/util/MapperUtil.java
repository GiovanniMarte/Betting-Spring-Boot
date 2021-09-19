package com.giovanni.bettingapp.util;

import com.giovanni.bettingapp.dto.BetDto;
import com.giovanni.bettingapp.dto.MatchDto;
import com.giovanni.bettingapp.model.Bet;
import com.giovanni.bettingapp.model.Match;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtil {
    public static BetDto mapBet(Bet bet) {
        return new BetDto(
                bet.getId(),
                bet.getGoalsHome(),
                bet.getGoalsAway(),
                bet.getUser().getUsername(),
                mapMatch(bet.getMatch()));
    }

    public static List<BetDto> mapBets(List<Bet> bets) {
        return bets.stream()
                .map(bet -> new BetDto(
                        bet.getId(),
                        bet.getGoalsHome(),
                        bet.getGoalsAway(),
                        bet.getUser().getUsername(),
                        mapMatch(bet.getMatch())))
                .collect(Collectors.toList());
    }

    public static MatchDto mapMatch(Match match) {
        return new MatchDto(
                match.getId(),
                match.getGoalsHome(),
                match.getGoalsAway(),
                match.getDate(),
                match.getTeamHome().getName(),
                match.getTeamAway().getName());
    }

    public static List<MatchDto> mapMatches(List<Match> matches) {
        return matches.stream()
                .map(match ->
                    new MatchDto(
                            match.getId(),
                            match.getGoalsHome(),
                            match.getGoalsAway(),
                            match.getDate(),
                            match.getTeamHome().getName(),
                            match.getTeamAway().getName()
                    ))
                .collect(Collectors.toList());
    }
}
