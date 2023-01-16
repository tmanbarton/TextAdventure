package com.project.textadventure.game.Locations;

import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Item;

import java.util.List;

public class GraniteRoom extends Location {

    boolean puzzleTaken;

    public GraniteRoom(String description, String shortDescription, List<Item> items, List<ConnectingLocation> connectingLocations, boolean visited, String name, boolean puzzleTaken) {
        super(description, shortDescription, items, connectingLocations, visited, name);
        this.puzzleTaken = puzzleTaken;
    }
}
