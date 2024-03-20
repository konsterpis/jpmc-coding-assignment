package com.jpmorgan.team.service;

import com.jpmorgan.team.model.Player;
import com.jpmorgan.team.model.PlayerPosition;
import com.jpmorgan.team.model.Team;

import java.util.List;

public interface RosterService {
    void addPlayerToRoster(Player player);

    List<Team> createTeams(List<PlayerPosition> positions);

}
