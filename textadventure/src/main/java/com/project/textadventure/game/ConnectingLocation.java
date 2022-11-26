package com.project.textadventure.game;

import com.project.textadventure.game.Locations.Location;

import java.util.List;

public class ConnectingLocation {
    List<String> directions;
    Location location;
    public ConnectingLocation(List<String> directions, Location location) {
        this.directions = directions;
        this.location = location;
    }
}
