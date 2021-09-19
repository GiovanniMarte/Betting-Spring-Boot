package com.giovanni.bettingapp.service;

import com.giovanni.bettingapp.exception.ResourceNotFoundException;
import com.giovanni.bettingapp.model.Team;
import com.giovanni.bettingapp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.giovanni.bettingapp.util.ConstantUtil.*;


@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    public Team getTeam(int id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_WITH_ID + id + NOT_FOUND));
    }

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(int id, Team newTeam) {
        return teamRepository.findById(id)
                .map(team -> {
                    team.setName(newTeam.getName());
                    return teamRepository.save(team);
                })
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_WITH_ID + id + NOT_FOUND));
    }

    public void deleteTeam(int id) {
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException(TEAM_WITH_ID + id + NOT_FOUND);
        }
        teamRepository.deleteById(id);
    }
}
