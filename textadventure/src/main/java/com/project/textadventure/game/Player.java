package com.project.textadventure.game;

import com.project.textadventure.game.Locations.Location;
import lombok.Data;

import java.util.List;

@Data
public class Player {
    private List<Item> inventory;
    private Location currentLocation;
    private boolean hasMoved;

    public Player(List<Item> inventory, Location currentLocation, boolean hasMoved) {
        this.inventory = inventory;
        this.currentLocation = currentLocation;
        this.hasMoved = hasMoved;
    }

    public boolean hasPlayerMoved() {
        return hasMoved;
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
        inventory.sort(new Actions());
    }

    public void removeItemFromInventory(Item item) {
        inventory.remove(item);
    }
}
