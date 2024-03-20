package com.jpmorgan.team.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Player implements Comparable<Player> {
    private final String id;
    private final PlayerPosition position;
    private final Integer rank;

    @Override
    public int compareTo(Player o) {
        return this.rank.compareTo(o.rank);
    }

    @Override
    public String toString() {
        return "(" + id + "," + rank + "," + position + ")";
    }
}
