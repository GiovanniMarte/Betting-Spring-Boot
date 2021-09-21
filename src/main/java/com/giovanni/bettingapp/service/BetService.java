package com.giovanni.bettingapp.service;

import com.giovanni.bettingapp.dto.BetDto;
import com.giovanni.bettingapp.exception.ResourceNotFoundException;
import com.giovanni.bettingapp.mapper.BetMapper;
import com.giovanni.bettingapp.model.Bet;
import com.giovanni.bettingapp.model.Match;
import com.giovanni.bettingapp.model.User;
import com.giovanni.bettingapp.repository.BetRepository;
import com.giovanni.bettingapp.repository.MatchRepository;
import com.giovanni.bettingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.giovanni.bettingapp.util.ConstantUtil.*;

@Service
@RequiredArgsConstructor
public class BetService {
    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final BetMapper betMapper;

    public List<BetDto> getBets() {
        List<Bet> bets = betRepository.findAll();
        return betMapper.toBetDtoList(bets);
    }

    public BetDto getBet(int id) {
        Bet bet = betRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BET_WITH_ID + id + NOT_FOUND));
        return betMapper.toBetDto(bet);
    }

    public BetDto saveBet(Bet bet) {
        bet.setUser(findUser(bet.getUser()));
        bet.setMatch(findMatch(bet.getMatch()));

        Bet savedBet = betRepository.save(bet);
        return betMapper.toBetDto(savedBet);
    }

    public BetDto updateBet(int id, Bet newBet) {
        return betRepository.findById(id)
                .map(bet -> {
                    bet.setGoalsHome(newBet.getGoalsHome());
                    bet.setGoalsAway(newBet.getGoalsAway());
                    bet.setUser(findUser(newBet.getUser()));
                    bet.setMatch(findMatch(newBet.getMatch()));

                    Bet updatedBet = betRepository.save(bet);
                    return betMapper.toBetDto(updatedBet);
                })
                .orElseThrow(() -> new ResourceNotFoundException(BET_WITH_ID + id + NOT_FOUND));
    }

    public void deleteBet(int id) {
        if (!betRepository.existsById(id)) {
            throw new ResourceNotFoundException(BET_WITH_ID + id + NOT_FOUND);
        }
        betRepository.deleteById(id);
    }

    public User findUser(User user) {
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_ID + user.getId() + NOT_FOUND));
    }

    public Match findMatch(Match match) {
        return matchRepository.findById(match.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MATCH_WITH_ID + match.getId() + NOT_FOUND));
    }
}
