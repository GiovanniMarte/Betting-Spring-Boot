package com.giovanni.bettingapp.controller;

import com.giovanni.bettingapp.dto.MatchDto;
import com.giovanni.bettingapp.model.Team;
import com.giovanni.bettingapp.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> getTeams() {
        List<Team> teams = teamService.getTeams();
        return ResponseEntity.ok().body(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeam(@PathVariable int id) {
        Team team = teamService.getTeam(id);
        return ResponseEntity.ok().body(team);
    }

    @GetMapping("/{id}/matches/home")
    public ResponseEntity<List<MatchDto>> getMatchesHomeByTeam(@PathVariable int id) {
        List<MatchDto> matches = teamService.getMatchesHomeByTeam(id);
        return ResponseEntity.ok().body(matches);
    }

    @GetMapping("/{id}/matches/away")
    public ResponseEntity<List<MatchDto>> getMatchesAwayByTeam(@PathVariable int id) {
        List<MatchDto> matches = teamService.getMatchesAwayByTeam(id);
        return ResponseEntity.ok().body(matches);
    }

    @PostMapping
    public ResponseEntity<Team> saveTeam(@Valid @RequestBody Team team) {
        Team newTeam = teamService.saveTeam(team);
        return ResponseEntity.ok().body(newTeam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable int id, @Valid @RequestBody Team team) {
        Team updatedTeam = teamService.updateTeam(id, team);
        return ResponseEntity.ok().body(updatedTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable int id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
