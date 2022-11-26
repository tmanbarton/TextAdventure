package com.project.textadventure.game.Locations;

import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Item;

import java.util.List;

public class Shed extends Location {
    boolean unlocked;
    boolean open;
    public Shed(String description,
                String shortDescription,
                List<Item> items,
                List<ConnectingLocation> connectingLocations,
                boolean visited,
                String name,
                boolean unlocked,
                boolean open) {
        super(description,
                shortDescription,
                items,
                connectingLocations,
                visited,
                name);
        this.unlocked = unlocked;
        this.open = open;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void unlockShed() {
        this.unlocked = true;
    }

    public void openShed() {

    }
}
