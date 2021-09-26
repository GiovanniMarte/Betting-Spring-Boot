package com.giovanni.bettingapp.controller;

import com.giovanni.bettingapp.dto.BetDto;
import com.giovanni.bettingapp.dto.MatchDto;
import com.giovanni.bettingapp.model.Match;
import com.giovanni.bettingapp.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matches")
public class MatchController {
    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchDto>> getMatches() {
        List<MatchDto> matches = matchService.getMatches();
        return ResponseEntity.ok().body(matches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDto> getMatch(@PathVariable int id) {
        MatchDto match = matchService.getMatch(id);
        return ResponseEntity.ok().body(match);
    }

    @GetMapping("/{id}/bets")
    public ResponseEntity<List<BetDto>> getBetsByMatch(@PathVariable int id) {
        List<BetDto> bets = matchService.getBetsByMatch(id);
        return ResponseEntity.ok().body(bets);
    }

    @PostMapping()
    public ResponseEntity<MatchDto> saveMatch(@Valid @RequestBody Match match) {
        MatchDto newMatch = matchService.saveMatch(match);
        return ResponseEntity.ok().body(newMatch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDto> updateMatch(@PathVariable int id, @Valid @RequestBody Match match) {
        MatchDto updatedMatch = matchService.updateMatch(id, match);
        return ResponseEntity.ok().body(updatedMatch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable int id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }
}
