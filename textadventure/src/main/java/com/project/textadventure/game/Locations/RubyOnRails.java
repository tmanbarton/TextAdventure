package com.project.textadventure.game.Locations;

import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Item;

import java.util.List;

public class RubyOnRails extends Location {
    boolean rubyTaken;

    public RubyOnRails(String description, String shortDescription, List<Item> items, List<ConnectingLocation> connectingLocations, boolean visited, String name, boolean rubyTaken) {
        super(description, shortDescription, items, connectingLocations, visited, name);
        this.rubyTaken = rubyTaken;
    }
}
