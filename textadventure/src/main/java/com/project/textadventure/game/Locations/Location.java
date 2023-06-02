package com.project.textadventure.game.Locations;

import com.project.textadventure.controllers.Action;
import com.project.textadventure.game.*;
import com.project.textadventure.game.actions.LocationActions;
import lombok.Data;

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
    LocationActions possibleActions;

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
        this.bfsIsVisited = false;
    }

    public Location() {}

    public void connectLocation(ConnectingLocation locationToConnect) {
        this.connectingLocations.add(locationToConnect);
    }

    public Item getLocationItemByName(String name) {
        for(Item item : this.items) {
            if(name.equals(item.getName())) {
                return item;
            }
        }
        return null;
    }

    public boolean isItemAtLocation(String itemName) {
        return getLocationItemByName(itemName) != null;
    }

    public void addItemToLocation(Item item) {
        items.add(item);
    }

    public void removeItemFromLocation(Item item) {
        items.remove(item);
    }

// "open", "unlock", "shoot", "turn"
    @Override
    public String takeAction(String verb, String noun) {
        Game game = GameState.getInstance().getGame();
        String response = "";
        if(isDirection(verb)) {
            response = move(verb);
        }
        else if(verb.equals("look") || verb.equals("l")) {
            response = look();
        }
        else if(verb.equals("unlock")) {
            response = unlock();
        }
        else if(verb.equals("open")) {
            response = open();
        }
        else if(verb.equals("shoot")) {
            response = parseShootCommand(noun);
        }
        else if(verb.equals("turn")) {
            response = turn();
        }

        return response;
    }

    boolean isDirection(String input) {
        return List.of("n", "s", "e", "w", "ne", "nw", "se", "sw", "u", "d",
                "north", "south", "east", "west", "northeast", "north east","northwest", "north west",
                "southeast", "south east", "southwest", "south west",
                "up", "down", "in", "enter", "out", "exit").contains(input);
    }

    private String move(String direction) {
        Game game = GameState.getInstance().getGame();
        Location currentLocation = game.getCurrentLocation();
        Optional<ConnectingLocation> optionalConnection = currentLocation.getConnectingLocations()
                .stream()
                .filter(connectingLocation -> connectingLocation.getDirections().contains(direction))
                .findFirst();

        if (optionalConnection.isEmpty()) {
            return "You can't go that way.";
        }
        ConnectingLocation connectingLocation = optionalConnection.get();
        if(currentLocation instanceof UndergroundLake && ((UndergroundLake) currentLocation).boatAtLocation) {
            return moveToLocation(connectingLocation);
        }
        return moveToLocation(connectingLocation);
    }

    private String moveToLocation(ConnectingLocation connectingLocation) {
        Location connection = connectingLocation.getLocation();
        Game game = GameState.getInstance().getGame();
        game.setCurrentLocation(connection);

        if(connection.isVisited()) {
            return connection.getShortDescription() + listLocationItems(connection.getItems());
        }
        connection.setVisited(true);
        return connection.getDescription() + listLocationItems(connection.getItems());
    }

    public String look() {
        Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();
        return currentLocation.getDescription() + listLocationItems(currentLocation.getItems());
    }

    String listLocationItems(List<Item> items) {
        StringBuilder result = new StringBuilder();
        for(Item item : items) {
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

    private String parseShootCommand(String noun) {
        Game game = GameState.getInstance().getGame();
        String response = "";

        // only "shoot" or "shoot arrow" does something at mine entrance
        if(noun == null || noun.equals("arrow")) {
            // Must have the bow and arrow in your inventory
            if(!game.isItemInInventory("bow")) {
                response = "You don't have anything to shoot with.";
            }
            else if(!game.isItemInInventory("arrow")) {
                response = "You don't have anything to shoot.";
            }
            // Shoot the arrow
            else {
                Item arrow = game.getInventoryItemByName("arrow");
                response = shootArrow(arrow);
            }
        }
        else {
            response = generateRandomUnknownCommandResponse();
        }
        return response;
    }

    private String shootArrow(Item arrow) {
        Game game = GameState.getInstance().getGame();
        if(game.getCurrentLocation().getName().equals("boat")) {
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
