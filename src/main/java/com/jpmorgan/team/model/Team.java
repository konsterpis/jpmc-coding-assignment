package com.jpmorgan.team.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class Team {
    private final Integer id;
    @Builder.Default
    private final List<Player> players = new ArrayList<>();
}
