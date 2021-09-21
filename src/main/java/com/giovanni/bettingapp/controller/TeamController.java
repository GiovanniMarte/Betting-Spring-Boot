package com.giovanni.bettingapp.controller;

import com.giovanni.bettingapp.dto.MatchDto;
import com.giovanni.bettingapp.model.Team;
import com.giovanni.bettingapp.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> getTeams() {
        List<Team> teams = teamService.getTeams();
        return new ResponseEntity<>(teams, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeam(@PathVariable int id) {
        Team team = teamService.getTeam(id);
        return new ResponseEntity<>(team, OK);
    }

    @GetMapping("/{id}/matches/home")
    public ResponseEntity<List<MatchDto>> getMatchesHomeByTeam(@PathVariable int id) {
        List<MatchDto> matches = teamService.getMatchesHomeByTeam(id);
        return new ResponseEntity<>(matches, OK);
    }

    @GetMapping("/{id}/matches/away")
    public ResponseEntity<List<MatchDto>> getMatchesAwayByTeam(@PathVariable int id) {
        List<MatchDto> matches = teamService.getMatchesAwayByTeam(id);
        return new ResponseEntity<>(matches, OK);
    }

    @PostMapping
    public ResponseEntity<Team> saveTeam(@Valid @RequestBody Team team) {
        Team newTeam = teamService.saveTeam(team);
        return new ResponseEntity<>(newTeam, OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable int id, @Valid @RequestBody Team team) {
        Team updatedTeam = teamService.updateTeam(id, team);
        return new ResponseEntity<>(updatedTeam, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable int id) {
        teamService.deleteTeam(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
