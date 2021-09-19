package com.giovanni.bettingapp.service;

import com.giovanni.bettingapp.exception.ConflictException;
import com.giovanni.bettingapp.exception.ResourceNotFoundException;
import com.giovanni.bettingapp.model.Bet;
import com.giovanni.bettingapp.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.giovanni.bettingapp.util.AppConstant.*;

@Service
@RequiredArgsConstructor
public class BetService {
    private final BetRepository betRepository;

    public List<Bet> getBets() {
        return betRepository.findAll();
    }

    public Bet getBet(int id) {
        return betRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BET_WITH + id + NOT_FOUND));
    }

    public Bet addBet(Bet bet) {
        if (betRepository.existsById(bet.getId())) {
            throw new ConflictException(BET_WITH + bet.getId() + EXISTS);
        }
        return betRepository.save(bet);
    }

    public Bet updateBet(int id, Bet newBet) {
        return betRepository.findById(id)
                .map(bet -> {
                    bet.setGoalsHome(newBet.getGoalsHome());
                    bet.setGoalsAway(newBet.getGoalsAway());
                    bet.setUser(newBet.getUser());
                    bet.setMatch(newBet.getMatch());
                    return betRepository.save(bet);
                })
                .orElseThrow(() -> new ResourceNotFoundException(BET_WITH + id + NOT_FOUND));
    }

    public void deleteBet(int id) {
        if (!betRepository.existsById(id)) {
            throw new ResourceNotFoundException(BET_WITH + id + NOT_FOUND);
        }
        betRepository.deleteById(id);
    }
}
