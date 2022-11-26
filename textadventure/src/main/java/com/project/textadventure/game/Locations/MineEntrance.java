package com.project.textadventure.game.Locations;

import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Item;

import java.util.List;

public class MineEntrance extends Location {
    boolean nailsOff;

    public MineEntrance(String description, String shortDescription, List<Item> items, List<ConnectingLocation> connectingLocations, boolean visited, String name, boolean nailsOff) {
        super(description, shortDescription, items, connectingLocations, visited, name);
        this.nailsOff = nailsOff;
    }

    public boolean areNailsOff() {
        return nailsOff;
    }

    public void setNailsOff(boolean nailsOff) {
        this.nailsOff = nailsOff;
    }
}
