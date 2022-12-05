package com.project.textadventure.game;

import com.project.textadventure.game.Locations.Location;

import java.util.List;

public class ConnectingLocation {
    private List<String> directions;
    private final Location location;
    public ConnectingLocation(List<String> directions, Location location) {
        this.directions = directions;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }
}
