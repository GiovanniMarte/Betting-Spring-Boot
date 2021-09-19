package com.giovanni.bettingapp.controller;

import com.giovanni.bettingapp.dto.BetDto;
import com.giovanni.bettingapp.model.Bet;
import com.giovanni.bettingapp.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bets")
public class BetController {
    private final BetService betService;

    @GetMapping
    public ResponseEntity<List<BetDto>> getBets() {
        List<BetDto> bets = betService.getBets();
        return new ResponseEntity<>(bets, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BetDto> getBet(@PathVariable int id) {
        BetDto bet = betService.getBet(id);
        return new ResponseEntity<>(bet, OK);
    }

    @PostMapping()
    public ResponseEntity<Bet> getBet(@Valid @RequestBody Bet bet) {
        Bet newBet = betService.addBet(bet);
        return new ResponseEntity<>(newBet, OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bet> updateBet(@PathVariable int id, @Valid @RequestBody Bet bet) {
        Bet updateBet = betService.updateBet(id, bet);
        return new ResponseEntity<>(updateBet, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBet(@PathVariable int id) {
        betService.deleteBet(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
