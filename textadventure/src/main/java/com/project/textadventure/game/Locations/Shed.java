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

    public boolean isOpen() {
        return open;
    }

    public void unlockShed() {
        this.unlocked = true;
        this.setDescription("A cheerful little shed stands with it's lock hanging open with a picnic table to the north.");
        this.setShortDescription("You're standing before a cheerful little shed with its lock hanging open.");
    }

    public void openShed() {
        this.setDescription("You stand before an open shed with a picnic table to the north.");
        this.setShortDescription("You're standing before a cheerful little, open shed.");
        this.open = true;
        Item hammer = new Item(2, "There is a hammer here", "Hammer", "hammer");
        Item bow = new Item(3, "There is a bow here, strung an ready for shooting", "Bow", "bow");
        Item arrow = new Item(4, "There is an arrow here", "Arrow", "arrow");
        Item jar = new Item(5, "There is a jar here", "Jar", "jar");
        Item shovel = new Item(8, "There is a shovel here.", "Shovel", "shovel");
        Item tent = new Item(9, "There is a tent here, packed neatly in a bag.", "Tent in bag", "tent");
        this.addItemToLocation(hammer);
        this.addItemToLocation(bow);
        this.addItemToLocation(arrow);
        this.addItemToLocation(jar);
        this.addItemToLocation(shovel);
        this.addItemToLocation(tent);
    }
}
