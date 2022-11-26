package com.project.textadventure.game;

import com.project.textadventure.game.Locations.Location;

import java.util.List;

public class Actions {
    public static String makeMove(String direction, Player player) {
        List<ConnectingLocation> connectingLocations = player.getCurrentLocation().getConnectingLocations();

        for(ConnectingLocation connectingLocation : connectingLocations) {
            if(connectingLocation.directions.contains(direction)) {
                Location connection = connectingLocation.location;
                player.setCurrentLocation(connection);
                if(connection.isVisited()) {
                    return connection.getShortDescription()
                            + new Actions().listLocationItems(connection.getItems());
                }
                connection.setVisited(true);
                return connection.getDescription()
                        + new Actions().listLocationItems(connection.getItems());
            }
        }
        return "You can't go that way.";
    }

    private String listLocationItems(List<Item> items) {
        StringBuilder result = new StringBuilder();
        for(Item item : items) {
            result.append(item.getLocationDescription());
        }
        return result.isEmpty() ? "" : "<br>" + result.toString();
    }

    public static String getItem(String inputItem, Player player) {
        Item item = player.getCurrentLocation().getItemByName(inputItem);
        if(item != null) {
            player.addItemToInventory(item);
            player.getCurrentLocation().removeItemFromLocation(item);
            return "OK";
        }
        return "I don't see that here.";
    }

    public static String dropItem(String inputItem, Player player) {
        Item item = player.getInventoryItemByName(inputItem);
        if(item != null) {
            player.removeItemFromInventory(item);
            player.getCurrentLocation().addItemToLocation(item);
            return "OK";
        }
        return "You're not carrying it!";
    }

    public static String look(Player player) {
        Location currentLocation = player.getCurrentLocation();
        return currentLocation.getDescription()
                + new Actions().listLocationItems(currentLocation.getItems());
    }

    public static String inventory(Player player) {
        if(player.getInventory().isEmpty()) {
            return "You're not carrying anything!";
        }
        StringBuilder result = new StringBuilder();
        for(Item item : player.getInventory()) {
            result.append(item.getInventoryDescription()).append(" ");
        }
        return result.toString();
    }
}
