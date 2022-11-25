package com.project.textadventure.game.Locations;

import com.project.textadventure.game.Item;
import com.project.textadventure.game.graph.ConnectingLocation;

import java.util.List;

public class Location {
    private String description;
    private List<Item> items;
    public List<ConnectingLocation> connectingLocations;
    private Location previousLocation;
    private boolean visited;                            // Has this location been visited yet? Used to know what to print for description
    private String name;
    private String shortDescription;

    public Location() {}

    public Location(
            String description,
            String shortDescription,
            List<Item> items,
            List<ConnectingLocation> connectingLocations,
            Location previousLocation,
            boolean visited, String name) {
        this.description = description;
        this.items = items;
        this.connectingLocations = connectingLocations;
        this.previousLocation = previousLocation;
        this.visited = visited;
        this.name = name;
        this.shortDescription = shortDescription;
    }
}
