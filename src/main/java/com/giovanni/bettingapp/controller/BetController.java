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
        return ResponseEntity.ok().body(bets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BetDto> getBet(@PathVariable int id) {
        BetDto bet = betService.getBet(id);
        return ResponseEntity.ok().body(bet);
    }

    @PostMapping()
    public ResponseEntity<BetDto> saveBet(@Valid @RequestBody Bet bet) {
        BetDto newBet = betService.saveBet(bet);
        return ResponseEntity.ok().body(newBet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BetDto> updateBet(@PathVariable int id, @Valid @RequestBody Bet bet) {
        BetDto updatedBet = betService.updateBet(id, bet);
        return ResponseEntity.ok().body(updatedBet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBet(@PathVariable int id) {
        betService.deleteBet(id);
        return ResponseEntity.noContent().build();
    }
}
