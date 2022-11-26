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

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public Item getInventoryItemByName(String name) {
        for(Item item : this.inventory) {
            if(name.equals(item.getName())) {
                return item;
            }
        }
        return null;
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void removeItemFromInventory(Item item) {
        inventory.remove(item);
    }
}
