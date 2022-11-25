package com.project.textadventure.game;

import com.project.textadventure.game.Locations.Location;

import java.util.List;

public class Player {
    private List<Item> inventory;
    private Location currentLocation;

    public Player(List<Item> inventory, Location currentLocation) {
        this.inventory = inventory;
        this.currentLocation = currentLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }
}
