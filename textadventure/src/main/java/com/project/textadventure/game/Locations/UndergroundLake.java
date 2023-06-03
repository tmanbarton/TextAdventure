package com.project.textadventure.game.Locations;

import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Item;

import java.util.List;

public class UndergroundLake extends Location {
    boolean boatAtLocation;

    public UndergroundLake(final String description, final String shortDescription, final List<Item> items, final List<ConnectingLocation> connectingLocations, final boolean visited, final String name, final boolean boatAtLocation) {
        super(description, shortDescription, items, connectingLocations, visited, name);
        this.boatAtLocation = boatAtLocation;
    }
}
