package com.project.textadventure.game.Locations;

import com.project.textadventure.constants.Constants;
import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.game.*;
import lombok.Data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

@Data
public class Location implements Action {
    private String description;
    private List<Item> items;
    private List<ConnectingLocation> connectingLocations;
    private boolean visited;
    public boolean bfsIsVisited;
    private String name;
    private String shortDescription;
    String[] commands;

    public Location(
            final String fullDescription,
            final String shortDescription,
            final List<Item> items,
            final List<ConnectingLocation> connectingLocations,
            final boolean visited,
            final String name) {
        this.description = fullDescription;
        this.items = items;
        this.connectingLocations = connectingLocations;
        this.visited = visited;
        this.name = name;
        this.shortDescription = shortDescription;
        this.bfsIsVisited = false;
    }

    public Location() {}

    public void connectLocation(final ConnectingLocation locationToConnect) {
        this.connectingLocations.add(locationToConnect);
    }

    public Item getLocationItemByName(final String name) {
        for(final Item item : this.items) {
            if (name.equals(item.getName())) {
                return item;
            }
        }
        return null;
    }

    public boolean isItemAtLocation(final String itemName) {
        return getLocationItemByName(itemName) != null;
    }

    public void addItemToLocation(final Item item) {
        items.add(item);
    }

    public void removeItemFromLocation(final Item item) {
        items.remove(item);
    }

    @Override
    public String takeAction(final String verb, final String noun) {
        String response = "";
        if (isDirection(verb)) {
            response = move(verb);
        }
        else if (verb.equals("look") || verb.equals("l")) {
            response = look();
        }
        else if (verb.equals("unlock")) {
            response = unlock();
        }
        else if (verb.equals("open")) {
            response = open();
        }
        else if (verb.equals("shoot")) {
            response = parseShootCommand(noun);
        }
        else if (verb.equals("turn")) {
            response = turn();
        }

        return response;
    }

    boolean isDirection(final String input) {
        return Constants.ALL_DIRECTIONS.contains(input);
    }

    private String move(final String direction) {
        final Game game = GameState.getInstance().getGame();
        final Location currentLocation = game.getCurrentLocation();
        final Optional<ConnectingLocation> optionalConnection = currentLocation.getConnectingLocations()
                .stream()
                .filter(connectingLocation -> connectingLocation.getDirections().contains(direction))
                .findFirst();

        if (optionalConnection.isEmpty()) {
            return "You can't go that way.";
        }
        final ConnectingLocation connectingLocation = optionalConnection.get();
        if (currentLocation instanceof UndergroundLake && ((UndergroundLake) currentLocation).boatAtLocation) {
            return moveToLocation(connectingLocation);
        }
        return moveToLocation(connectingLocation);
    }

    private String moveToLocation(final ConnectingLocation connectingLocation) {
        final Location connection = connectingLocation.getLocation();
        final Game game = GameState.getInstance().getGame();
        game.setCurrentLocation(connection);

        if (connection.isVisited()) {
            return connection.getShortDescription() + listLocationItems(connection.getItems());
        }
        connection.setVisited(true);
        return connection.getDescription() + listLocationItems(connection.getItems());
    }

    public String look() {
        final Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();
        return currentLocation.getDescription() + listLocationItems(currentLocation.getItems());
    }

    String listLocationItems(final List<Item> items) {
        items.sort(Comparator.comparingInt(Item::getDisplayOrder));
        final StringBuilder result = new StringBuilder();
        for(final Item item : items) {
            result.append("<br>").append(item.getLocationDescription());
        }
        return result.isEmpty() ? "" : "<br>" + result;
    }

    private String unlock() {
        return "There's nothing to unlock here.";
    }

    private String open() {
        return "There's nothing to open here.";
    }

    private String parseShootCommand(final String noun) {
        final Game game = GameState.getInstance().getGame();
        String response = "";

        // only "shoot" or "shoot arrow" does something at mine entrance
        if (noun == null || noun.equals(ItemConstants.ARROW_NAME)) {
            // Must have the bow and arrow in your inventory
            if (!game.isItemInInventory(ItemConstants.BOW_NAME)) {
                response = "You don't have anything to shoot with.";
            }
            else if (!game.isItemInInventory(ItemConstants.ARROW_NAME)) {
                response = "You don't have anything to shoot.";
            }
            // Shoot the arrow
            else {
                final Item arrow = game.getInventoryItemByName(ItemConstants.ARROW_NAME);
                response = shootArrow(arrow);
            }
        }
        else {
            response = generateRandomUnknownCommandResponse();
        }
        return response;
    }

    private String shootArrow(final Item arrow) {
        final Game game = GameState.getInstance().getGame();
        if (game.getCurrentLocation().getName().equals(LocationNames.BOAT)) {
            game.removeItemFromInventory(arrow);
            return "Your arrow goes flying off into the the distance and splashes into the water, never to be found again.";
        }
        game.removeItemFromInventory(arrow);
        addItemToLocation(arrow);
        return "Your arrow goes flying off into the the distance and lands with a thud on the ground.";
    }

    private String turn() {
        return "There's nothing here to turn.";
    }

    @Override
    public String toString() {
        return this.name;
    }
}
