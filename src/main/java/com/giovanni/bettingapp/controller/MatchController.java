package com.giovanni.bettingapp.controller;

import com.giovanni.bettingapp.model.Match;
import com.giovanni.bettingapp.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matches")
public class MatchController {
    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<Match>> getMatches() {
        List<Match> matches = matchService.getMatches();
        return new ResponseEntity<>(matches, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatch(@PathVariable int id) {
        Match match = matchService.getMatch(id);
        return new ResponseEntity<>(match, OK);
    }

    @PostMapping()
    public ResponseEntity<Match> getMatch(@Valid @RequestBody Match match) {
        Match newMatch = matchService.addMatch(match);
        return new ResponseEntity<>(newMatch, OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Match> updateMatch(@PathVariable int id, @Valid @RequestBody Match match) {
        Match updateMatch = matchService.updateMatch(id, match);
        return new ResponseEntity<>(updateMatch, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable int id) {
        matchService.deleteMatch(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
