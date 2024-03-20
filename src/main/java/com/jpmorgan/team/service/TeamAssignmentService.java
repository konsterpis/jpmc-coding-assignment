package com.jpmorgan.team.service;

import com.google.common.base.Preconditions;
import com.jpmorgan.team.model.Player;
import com.jpmorgan.team.model.Team;

public class TeamAssignmentService implements AssignmentService {
    @Override
    public void assignPlayerToTeam(Team team, Player player) {
        Preconditions.checkArgument(team != null);
        Preconditions.checkArgument(player != null);

        team.getPlayers().add(player);
    }

}
