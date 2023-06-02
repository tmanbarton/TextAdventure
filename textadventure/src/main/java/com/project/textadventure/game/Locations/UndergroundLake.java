package com.project.textadventure.game.Locations;

import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Item;

import java.util.List;

public class UndergroundLake extends Location {
    boolean boatAtLocation;

    public UndergroundLake(String description, String shortDescription, List<Item> items, List<ConnectingLocation> connectingLocations, boolean visited, String name, boolean boatAtLocation) {
        super(description, shortDescription, items, connectingLocations, visited, name);
        this.boatAtLocation = boatAtLocation;
    }
}
