package com.project.textadventure.game.Locations;

import com.project.textadventure.game.Item;
import com.project.textadventure.game.ConnectingLocation;

import java.util.List;

public class Location {
    private String description;
    private List<Item> items;
    private List<ConnectingLocation> connectingLocations;
    private boolean visited;
    private String name;
    private String shortDescription;

    public Location(
            String fullDescription,
            String shortDescription,
            List<Item> items,
            List<ConnectingLocation> connectingLocations,
            boolean visited,
            String name) {
        this.description = fullDescription;
        this.items = items;
        this.connectingLocations = connectingLocations;
        this.visited = visited;
        this.name = name;
        this.shortDescription = shortDescription;
    }

    public void connectLocation(ConnectingLocation locationToConnect) {
        this.connectingLocations.add(locationToConnect);
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public List<ConnectingLocation> getConnectingLocations() {
        return connectingLocations;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setConnectingLocations(List<ConnectingLocation> connectingLocations) {
        this.connectingLocations = connectingLocations;
    }

    public Item getLocationItemByName(String name) {
        for(Item item : this.items) {
            if(name.equals(item.getName())) {
                return item;
            }
        }
        return null;
    }

    public void addItemToLocation(Item item) {
        items.add(item);
    }

    public void removeItemFromLocation(Item item) {
        items.remove(item);
    }
}
