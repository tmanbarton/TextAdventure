package com.project.textadventure.game;

import com.project.textadventure.game.Locations.Location;
import com.project.textadventure.game.Locations.Shed;

import java.util.List;

public class Actions {
    public static String makeMove(String direction) {
        Player player = GameState.getInstance().getGame();
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

    public static String getItem(String input) {
        Player player = GameState.getInstance().getGame();
        String[] splitInput = input.split(" ");
        Item item = player.getCurrentLocation().getLocationItemByName(splitInput[splitInput.length - 1]);

        // Either input is "get item" or "get the item"
        if(item != null && (splitInput.length == 1 || (splitInput.length == 2 && splitInput[0].equals("the")))) {
            player.addItemToInventory(item);
            player.getCurrentLocation().removeItemFromLocation(item);
            return "OK";
        }
        if(input.equals("tree")) {
            // Make sure location has trees
            return "You walk to the nearest tree and start pulling. After a couple minutes of this you give up. You can't get a tree.";
        }
        return "I don't see that here.";
    }

    private boolean locationHasTrees(Location location) {
        String name = location.getName();
        return name.equals("ant hill") || name.equals("archery range") || name.equals("dirt road") || name.equals("ditch")
                || name.equals("driveway") || name.equals("foot path") || name.equals("intersection")
                || name.equals("lightning tree") || name.equals("outside log cabin") || name.equals("picnic table")
                || name.equals("private property") || name.equals("shed") || name.equals("top of hill") || name.equals("mountain pass");
    }

    public static String dropItem(String input) {
        Player player = GameState.getInstance().getGame();
        String[] splitInput = input.split(" ");

        Item item = player.getInventoryItemByName(splitInput[splitInput.length - 1]);
        // Either input is "drop item" or "drop the item"
        if(item != null && (splitInput.length == 1 || (splitInput.length == 2 && splitInput[0].equals("the")))) {
            player.removeItemFromInventory(item);
            player.getCurrentLocation().addItemToLocation(item);
            return "OK";
        }
        return "You're not carrying it!";
    }

    public static String look() {

        Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();
        return currentLocation.getDescription()
                + new Actions().listLocationItems(currentLocation.getItems());
    }

    public static String inventory() {
        Player player = GameState.getInstance().getGame();
        if(player.getInventory().isEmpty()) {
            return "You're not carrying anything!";
        }
        StringBuilder result = new StringBuilder();
        for(Item item : player.getInventory()) {
            result.append(item.getInventoryDescription()).append(" ");
        }
        return "You're carrying:<br>" + result.toString();
    }

    public static String unlock(String input) {
        String result = "What?";
        Item key = GameState.getInstance().getGame().getInventoryItemByName("key");
        Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();

        if(input.equals("shed") || input.equals("") || input.equals("the shed")) {
            if(!(currentLocation instanceof Shed)) {
                result = "You can't unlock something that doesn't have a lock.";
            }
            else if(key == null) {
                result = "You need a key to unlock the shed.";
            }
            else if(!((Shed) currentLocation).isUnlocked()) {
                ((Shed) currentLocation).unlockShed();
                currentLocation.setDescription("A cheerful little shed stands with " +
                        "it's lock hanging open with a picnic table to the north.");
                result = "The shed is now unlocked";
            }
        }
        else if(new Actions().unlockingAndOpeningShed(input)) {
            if(!(currentLocation instanceof Shed)) {
                result = "You can't unlock something that doesn't have a lock.";
            }
            else if(key == null) {
                result = "You need a key to unlock the shed.";
            }
            else {
                ((Shed) currentLocation).unlockShed();
                currentLocation.setDescription("You stand before an open shed " +
                        "with a picnic table to the north.");
                result = "open";//open("");
            }
        }
        return result;
    }

    private boolean unlockingAndOpeningShed(String input) {
        return input.equals("shed and open shed") || input.equals("shed then open shed") ||input.equals("and open")
                || input.equals("then open") || input.equals("shed and open") || input.equals("shed then open")
                || input.equals("and open shed") || input.equals("then open shed");
    }

//    public static String open(String input) {
//
//    }

    public static String curse(String input) {
        String result = "Hey, watch your language.";
        if(input.equals("fuck this")) {
            result = "Maybe you need to take a break.";
        }
        else if(input.equals("fuck you")) {
            result = "Well fuck you too, that's not very nice.";
        }
        else if(input.equals("shit")) {
            result = "Poop is gross.";
        }
        else if(input.equals("damn") || input.equals("damn it")) {
            result = "There's a dam around here, but no damn.";
        }
        return result;
    }
}
