package com.jpmorgan.team.service;

import com.google.common.base.Preconditions;
import com.jpmorgan.team.TeamCreationApp;
import com.jpmorgan.team.model.Player;
import com.jpmorgan.team.model.PlayerPosition;
import com.jpmorgan.team.model.Team;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.jpmorgan.team.TeamCreationApp.*;
import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Getter
public class TeamRosterService implements RosterService {
    private final AssignmentService assignmentService;
    private final List<Team> allTeams = List.of(Team.builder().id(1).build(), Team.builder().id(2).build());
    private final List<Player> allPlayers = new ArrayList<>();


    @Override
    public void addPlayerToRoster(Player player) {
        Preconditions.checkArgument(player != null, "Player has to be specified");
        allPlayers.add(player);
    }

    public List<Team> createTeams(List<PlayerPosition> positions) {
        Preconditions.checkArgument(positions!=null && !positions.isEmpty(), "Team positions have to be specified");
        //get players sorted by rank, categorised by position
        Map<PlayerPosition, SortedSet<Player>> playersByPosition = allPlayers.stream()
                .collect(groupingBy(Player::getPosition, Collectors.toCollection(TreeSet::new)));

        for (int i = 0; i < 2 * positions.size(); i++) {
            //pick the position, twice the same for each team
            var position = positions.get(i/2 % positions.size());

            //pick the list of players in the position
            var playersInPosition = playersByPosition.get(position);
            //if there are still players in that position
            if (!playersInPosition.isEmpty()){
                //select the team
                var team = allTeams.get(i % 2);

                //select the weakest or the strongest for the current position, alternatively
                Player player;
                if (i%4==0 || i%4==3) {
                    player = getPlayerWithLowestRank(playersInPosition);
                }else {
                    player = getPlayerWithHighestRank(playersInPosition);
                }
                assignmentService.assignPlayerToTeam(team, player);
            }


        }
        return allTeams;
    }

    private Player getPlayerWithLowestRank(SortedSet<Player> players){
        return players.removeFirst();
    }

    private Player getPlayerWithHighestRank(SortedSet<Player> players){
        return players.removeLast();
    }

}
