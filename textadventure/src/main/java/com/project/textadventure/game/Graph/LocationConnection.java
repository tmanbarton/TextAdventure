package com.project.textadventure.game.Graph;

import lombok.Data;

import java.util.List;

@Data
public class LocationConnection {
    private List<String> directions;
    private final Location location;
    public LocationConnection(final List<String> directions, final Location location) {
        this.directions = directions;
        this.location = location;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }
}
