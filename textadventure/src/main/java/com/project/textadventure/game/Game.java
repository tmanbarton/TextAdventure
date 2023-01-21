package com.project.textadventure.game;

import com.project.textadventure.controller.Action;
import com.project.textadventure.game.Locations.Dam;
import com.project.textadventure.game.Locations.Location;
import com.project.textadventure.game.Locations.MineEntrance;
import com.project.textadventure.game.actions.PlayerActions;
import lombok.Data;

import java.util.*;

@Data
public class Game implements Action, Comparator<Item> {
    private List<Item> inventory;
    private Location currentLocation;
    private boolean playerMoved;
    private boolean takingNails = false;

    PlayerActions possibleActions;

    public Game(List<Item> inventory, Location currentLocation, boolean playerMoved) {
        this.inventory = inventory;
        this.currentLocation = currentLocation;
        this.playerMoved = playerMoved;
    }

    public Game() {}

    public boolean hasPlayerMoved() {
        return playerMoved;
    }

    public Item getInventoryItemByName(String name) {
        for(Item item : this.inventory) {
            if(name.equals(item.getName())) {
                return item;
            }
        }
        return null;
    }

    public boolean isItemInInventory(String itemName) {
        return getInventoryItemByName(itemName) != null;
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
        inventory.sort(new Game());
    }

    public void removeItemFromInventory(Item item) {
        inventory.remove(item);
    }

    public static String generateRandomUnknownCommandResponse() {
        Random r = new Random();
        int randomNum = r.nextInt(3);
        return switch (randomNum) {
          case 0 -> "I don't understand that.";
          case 1 -> "What?";
          default -> "I don't know that word.";
        };
    }

    //"solve", "quit", "score", "info", "restart"
    @Override
    public String takeAction(String verb, String noun) {
        String result = "";
        if(verb.equals("get") || verb.equals("take")) {
            result = getItem(noun);
        }
        else if(verb.equals("fill")) {
            result = fill(noun);
        }
        else if(verb.equals("inventory") || verb.equals("i") || verb.equals("inven")) {
            result = takeInventory();
        }
        else if(verb.equals("drop") || verb.equals("throw")) {
            result = dropItem(noun);
        }
        return result;
    }

    private String getItem(String noun) {
        if(noun == null) {
            return "What to want to get?";
        }
        String result = "";
        Item item = currentLocation.getLocationItemByName(noun);
        Item jar = getInventoryItemByName("jar");
        if(getInventoryItemByName(noun) != null) {
            result = "You're already carrying it!";
        }
        else if(noun.equals("magnet") && currentLocation instanceof Dam && ((Dam) currentLocation).isMagnetDropped()) {
            result = "The magnet is firmly attached to the wheel";
        }
        else if(noun.equals("nails") && currentLocation instanceof MineEntrance && !((MineEntrance) currentLocation).areNailsOff()) {
            result = "Are you sure you want to get the nails? The structure is very fragile and may fall apart and onto you.";
            ((MineEntrance) currentLocation).setTakingNails(true);
        }
        else if(!currentLocation.isItemAtLocation(noun)) {
            result = "I don't see that here.";
        }
        else if(noun.equals("gold") && !isItemInInventory("jar")) {
            result = "You need something to hold the gold flakes.";
        }
        else if(noun.equals("gold") && isItemInInventory("jar")) {
            jar.setInventoryDescription("Jar full of gold flakes");
            addItemToInventory(item);
            currentLocation.removeItemFromLocation(item);
            result = "The jar is now full of gold flakes.";
        }
        else {
            if(currentLocation.getName().equals("crumpled mine cart")) {
                currentLocation.setDescription("You've reached a dead end. A crumpled mine cart, no longer able to run on the rails, is laying on its side.");
            }
            else if(currentLocation.getName().equals("granite room")) {
                currentLocation.setDescription("You're in a room of granite, black as night and in the middle of this room is a polished pedestal of the same black granite. Other than that the room is featureless.");
            }
            addItemToInventory(item);
            currentLocation.removeItemFromLocation(item);
            result = "OK.";
        }
        return result;
    }

    private String fill(String noun) {
        if(!(noun == null || noun.equals("jar"))) {
            return "That's not something you can fill.";
        }
        Item jar = getInventoryItemByName("jar");
        Item gold = currentLocation.getLocationItemByName("gold");
        if(jar == null) {
            return "You don't have anything to fill.";
        }
        if(gold == null) {
            return "There's nothing here to fill your jar with.";
        }
        if(isItemInInventory("gold")) {
            return "The jar is already full of gold flakes.";
        }
        jar.setInventoryDescription("Jar full of gold flakes");
        addItemToInventory(gold);
        currentLocation.removeItemFromLocation(gold);
        return  "The jar is now full of gold flakes.";
    }

    private String dropItem(String noun) {
        if(noun == null) {
            return "What to want to drop?";
        }
        String result = "";
        Item item = getInventoryItemByName(noun);
        if(item == null) {
            return "You're not carrying that!";
        }
        if(noun.equals("jar")) {
            Item gold = getInventoryItemByName("gold");
            if(gold != null) {
                item.setInventoryDescription("Jar");
                currentLocation.addItemToLocation(gold);
                removeItemFromInventory(gold);
            }
        }
        if(noun.equals("gold")) {
            Item jar = getInventoryItemByName("jar");
            jar.setInventoryDescription("Jar");
        }
        if(noun.equals("magnet") && currentLocation instanceof Dam) {
            removeItemFromInventory(item);
            ((Dam) currentLocation).setMagnetDropped(true);
            return "You drop the magnet and as it's falling it snaps to the shiny center of the wheel. You can hear " +
                    "some mechanical clicking somewhere inside the dam.";
        }
        if(currentLocation.getName().equals("boat")) {
            removeItemFromInventory(item);
            return "You're " + item.getName() + " splashes into the water next to the boat and sinks to the bottom, never to be found again.";
        }
        removeItemFromInventory(item);
        currentLocation.addItemToLocation(item);
        return "OK.";
    }

    private String takeInventory() {
        if(this.inventory.isEmpty()) {
            return "You're not carrying anything!";
        }
        StringBuilder result = new StringBuilder();
        for(Item item : this.inventory) {
            result.append("<br>").append(item.getInventoryDescription()).append(" ");
        }
        return "You're carrying:" + result;
    }

    /**
     * Drop everything from inventory to current location and move player to start location of the game
     * by setting current location to dirt road
     */
    public void die() {
        List<Item> inventoryCopy = new ArrayList<>(inventory);
        for(Item item : inventoryCopy) {
            removeItemFromInventory(item);
            currentLocation.addItemToLocation(item);
        }
        currentLocation = findStartLocation();
    }

    /**
     * Breadth first search to find the start location of the game, which is the driveway. Start the search from
     * the current location
     * @return driveway location if it's found or null if it's not. Should never return null.
     */
    private Location findStartLocation() {
        String targetLocationName = "driveway";
        // if you're at the dirt road, just return
        if(currentLocation.getName().equals(targetLocationName)) {
            return currentLocation;
        }
        LinkedList<Location> queue = new LinkedList<>();
        queue.add(currentLocation);

        // BFS algorithm
        while(!queue.isEmpty()) {
            Location current = queue.removeFirst();
            if(current.bfsIsVisited) {
                continue;
            }

            if(current.getName().equals(targetLocationName)) {
                return current;
            }
            current.bfsIsVisited = true;
            List<ConnectingLocation> neighbors = current.getConnectingLocations();

            if(neighbors == null) {
                continue;
            }
            for(ConnectingLocation neighbor : neighbors) {
                if(!neighbor.getLocation().bfsIsVisited) {
                    queue.add(neighbor.getLocation());
                }
            }
        }
        return null;
    }

    @Override
    public int compare(Item item1, Item item2) {
        return item1.getOrder() - item2.getOrder();
    }
}
