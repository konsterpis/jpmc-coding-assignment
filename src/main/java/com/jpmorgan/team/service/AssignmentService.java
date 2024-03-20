package com.jpmorgan.team.service;

import com.jpmorgan.team.model.Player;
import com.jpmorgan.team.model.Team;

import java.util.List;

public interface AssignmentService {

    void assignPlayerToTeam(Team team, Player player);

}
