package com.jpmorgan.team.service;

import com.jpmorgan.team.model.Player;
import com.jpmorgan.team.model.PlayerPosition;
import com.jpmorgan.team.model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.jpmorgan.team.TeamCreationApp.*;
import static com.jpmorgan.team.model.PlayerPosition.*;

public class TeamRosterServiceTest {
    private TeamRosterService rosterService = new TeamRosterService(new TeamAssignmentService());

    @Test
    void addPlayerToRoster_playerNotExists_exceptionRaised(){
        Player aPlayer = null;

        Assertions.assertThrows(IllegalArgumentException.class, ()-> rosterService.addPlayerToRoster(aPlayer));
    }

    @Test
    void addPlayerToRoster(){
        Player aPlayer = Player.builder().id("P1").rank(85).position(GOALKEEPER).build();
        rosterService.addPlayerToRoster(aPlayer);
        Assertions.assertTrue(rosterService.getAllPlayers().contains(aPlayer));
    }

    @Test
    void createTeams_noPositions(){
        List<PlayerPosition> positions = List.of();

        Assertions.assertThrows(IllegalArgumentException.class, () ->rosterService.createTeams(positions));
    }

    @Test
    void createTeams(){
        //list available positions for the two teams
        List<PlayerPosition> positions = List.of(GOALKEEPER, CENTER_BACK, DEFENSIVE_MIDFIELDER, FORWARD);

        //add players to roster
        rosterService.addPlayerToRoster(Player.builder().id("P1").position(GOALKEEPER).rank(85).build());
        rosterService.addPlayerToRoster(Player.builder().id("P2").position(GOALKEEPER).rank(92).build());

        rosterService.addPlayerToRoster(Player.builder().id("P3").position(CENTER_BACK).rank(87).build());
        rosterService.addPlayerToRoster(Player.builder().id("P4").position(CENTER_BACK).rank(94).build());

        rosterService.addPlayerToRoster(Player.builder().id("P5").position(DEFENSIVE_MIDFIELDER).rank(86).build());
        rosterService.addPlayerToRoster(Player.builder().id("P6").position(DEFENSIVE_MIDFIELDER).rank(93).build());

        rosterService.addPlayerToRoster(Player.builder().id("P7").position(FORWARD).rank(88).build());
        rosterService.addPlayerToRoster(Player.builder().id("P8").position(FORWARD).rank(95).build());

        //create balanced teams out of roster
        var teams = rosterService.createTeams(positions);

        //assert that we have two teams
        Assertions.assertEquals(2, teams.size());
        Team teamA = teams.get(0);
        Team teamB = teams.get(1);

        //assert that both teams have players for all positions
        Assertions.assertEquals(positions, getPositionsForTeam(teamA));
        Assertions.assertEquals(positions, getPositionsForTeam(teamB));

        //assert that the teams have equal skill
        Assertions.assertEquals(getTeamSkill(teamA), getTeamSkill(teamB));

    }

    private List<PlayerPosition> getPositionsForTeam(Team team){
        return team.getPlayers().stream().map(Player::getPosition).collect(Collectors.toList());
    }

    private int getTeamSkill(Team team){
        return team.getPlayers().stream().mapToInt(Player::getRank).sum();
    }
}
