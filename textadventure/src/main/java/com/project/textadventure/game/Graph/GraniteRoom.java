package com.project.textadventure.game.Graph;

import java.util.List;

public class GraniteRoom extends Location {

    boolean puzzleTaken;

    public GraniteRoom(final String description, final String shortDescription, final List<Item> items, final List<LocationConnection> locationConnections, final boolean visited, final String name, final boolean puzzleTaken) {
        super(description, shortDescription, items, locationConnections, visited, name);
        this.puzzleTaken = puzzleTaken;
    }
}
