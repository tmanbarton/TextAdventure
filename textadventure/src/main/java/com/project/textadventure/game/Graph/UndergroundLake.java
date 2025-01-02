package com.project.textadventure.game.Graph;

import java.util.List;

public class UndergroundLake extends Location {
    private boolean boatAtLocation;

    public UndergroundLake(final String description, final String shortDescription, final List<Item> items, final List<LocationConnection> locationConnections, final boolean visited, final String name, final boolean boatAtLocation) {
        super(description, shortDescription, items, locationConnections, visited, name);
        this.boatAtLocation = boatAtLocation;
    }

    public boolean isBoatAtLocation() {
        return boatAtLocation;
    }
}
