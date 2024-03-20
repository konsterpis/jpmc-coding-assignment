package com.jpmorgan.team;

import com.jpmorgan.team.model.Player;
import com.jpmorgan.team.model.PlayerPosition;
import com.jpmorgan.team.service.AssignmentService;
import com.jpmorgan.team.service.RosterService;
import com.jpmorgan.team.service.TeamAssignmentService;
import com.jpmorgan.team.service.TeamRosterService;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static com.jpmorgan.team.model.PlayerPosition.*;

@Slf4j
public class TeamCreationApp {
    public static final List<PlayerPosition> POSITIONS = List.of(GOALKEEPER, CENTER_BACK, CENTER_BACK, FULL_BACK, FULL_BACK,
            DEFENSIVE_MIDFIELDER, DEFENSIVE_MIDFIELDER , CENTRAL_MIDFIELDER, ATTACKING_MIDFIELDER, WINGER, FORWARD);
    public static final Integer MIN_RANK = 80;
    public static final Integer MAX_RANK = 99;

    public static void main(String[] args) {
        //instantiate business services for team creation
        AssignmentService assignmentService = new TeamAssignmentService();
        RosterService rosterService = new TeamRosterService(assignmentService);

        //generate players randomly
        Random random = new Random();

        for (int i = 0; i < 10 * POSITIONS.size(); i++) {
            var id = "P" + i;
            var rank = random.nextInt(MAX_RANK - MIN_RANK + 1) + MIN_RANK;
            var position = POSITIONS.get(i % POSITIONS.size());
            var player = Player.builder().id(id).rank(rank).position(position).build();
            //add player to initial roster
            rosterService.addPlayerToRoster(player);
        }

        //created balanced teams based on the roster that was populated with players, teams that have the following positions
        var teams = rosterService.createTeams(POSITIONS);

        //print results
        for (var team : teams) {
            int numOfPlayersInTeam = team.getPlayers().size();
            int teamSkillLevel = team.getPlayers().stream().mapToInt(Player::getRank).sum();
            log.info("#players:{}, team skill level: {}, team:{}", numOfPlayersInTeam, teamSkillLevel, team);
        }

    }
}
