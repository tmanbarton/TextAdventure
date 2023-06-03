package com.project.textadventure.game.Locations;

import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Item;

import java.util.List;

public class GraniteRoom extends Location {

    boolean puzzleTaken;

    public GraniteRoom(final String description, final String shortDescription, final List<Item> items, final List<ConnectingLocation> connectingLocations, final boolean visited, final String name, final boolean puzzleTaken) {
        super(description, shortDescription, items, connectingLocations, visited, name);
        this.puzzleTaken = puzzleTaken;
    }
}
