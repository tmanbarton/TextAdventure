package com.project.textadventure.game;

import com.project.textadventure.game.Locations.Location;
import lombok.Data;

import java.util.List;

@Data
public class ConnectingLocation {
    private final List<String> directions;
    private final Location location;
    public ConnectingLocation(final List<String> directions, final Location location) {
        this.directions = directions;
        this.location = location;
    }
}
