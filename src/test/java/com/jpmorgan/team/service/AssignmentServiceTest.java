package com.jpmorgan.team.service;

import com.jpmorgan.team.model.Player;
import com.jpmorgan.team.model.PlayerPosition;
import com.jpmorgan.team.model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssignmentServiceTest {
    private AssignmentService service = new TeamAssignmentService();

    @Test
    void assignPlayerToTeam(){
        Team aTeam = Team.builder().id(1).build();
        Player player = Player.builder().position(PlayerPosition.CENTER_BACK).rank(80).id("P1").build();

        service.assignPlayerToTeam(aTeam, player);
        Assertions.assertTrue(aTeam.getPlayers().contains(player));
    }

    @Test
    void assignPlayerToTeam_noTeam_exceptionRaised(){
        Team aTeam = null;
        Player player = Player.builder().position(PlayerPosition.CENTER_BACK).rank(80).id("P1").build();

        Assertions.assertThrows(IllegalArgumentException.class, ()->service.assignPlayerToTeam(aTeam, player));
    }

    @Test
    void assignPlayerToTeam_noPlayer_exceptionRaised(){
        Team aTeam = Team.builder().id(1).build();
        Player player = null;

        Assertions.assertThrows(IllegalArgumentException.class, ()->service.assignPlayerToTeam(aTeam, player));
    }
}
