package com.project.textadventure.game;

import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.game.Locations.Dam;
import com.project.textadventure.game.Locations.Location;
import com.project.textadventure.game.Locations.MineEntrance;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Game implements Action, Comparator<Item> {
    private List<Item> inventory;
    private Location currentLocation;
    private boolean playerMoved;
    final private boolean takingNails = false;


    public Game(final List<Item> inventory, final Location currentLocation, final boolean playerMoved) {
        this.inventory = inventory;
        this.currentLocation = currentLocation;
        this.playerMoved = playerMoved;
    }

    public Game() {}

    public boolean hasPlayerMoved() {
        return playerMoved;
    }

    public Item getInventoryItemByName(final String name) {
        for(final Item item : this.inventory) {
            if(name.equals(item.getName())) {
                return item;
            }
        }
        return null;
    }

    public boolean isItemInInventory(final String itemName) {
        return getInventoryItemByName(itemName) != null;
    }

    public void addItemToInventory(final Item item) {
        inventory.add(item);
        inventory.sort(new Game());
    }

    public void removeItemFromInventory(final Item item) {
        inventory.remove(item);
    }

    public static String generateRandomUnknownCommandResponse() {
        final Random r = new Random();
        final int randomNum = r.nextInt(3);
        return switch (randomNum) {
          case 0 -> "I don't understand that.";
          case 1 -> "What?";
          default -> "I don't know that word.";
        };
    }

    // "quit", "score", "info", "restart"
    @Override
    public String takeAction(final String verb, final String noun) {
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

    private String getItem(final String noun) {
        if(noun == null) {
            return "What to want to get?";
        }
        String result;
        final Item item = currentLocation.getLocationItemByName(noun);
        final Item jar = getInventoryItemByName("jar");
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

    private String fill(final String noun) {
        if(!(noun == null || noun.equals("jar"))) {
            return "That's not something you can fill.";
        }
        final Item jar = getInventoryItemByName("jar");
        final Item gold = currentLocation.getLocationItemByName("gold");
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

    private String dropItem(final String noun) {
        if(noun == null) {
            return "What to want to drop?";
        }
        final Item item = getInventoryItemByName(noun);
        if(item == null) {
            return "You're not carrying that!";
        }
        if(noun.equals("jar")) {
            final Item gold = getInventoryItemByName("gold");
            if(gold != null) {
                item.setInventoryDescription("Jar");
                currentLocation.addItemToLocation(gold);
                removeItemFromInventory(gold);
            }
        }
        if(noun.equals("gold")) {
            final Item jar = getInventoryItemByName("jar");
            jar.setInventoryDescription("Jar");
        }
        if(noun.equals("magnet") && currentLocation instanceof Dam) {
            removeItemFromInventory(item);
            ((Dam) currentLocation).setMagnetDropped(true);
            currentLocation.setDescription("You're on a short dam that created this lake by stopping up a large river. The dam goes north and south along the east end of the lake. Close by is a wheel with its axel extending deep into the dam. Its orange metal has faded to rust except for some different metal at the center, shining in the sun. There's a large magnet stuck to this part of the wheel. South leads around the lake and to the north there's a set of stairs.");
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
        final StringBuilder result = new StringBuilder();
        for(Item item : this.inventory) {
            result.append("<br>").append(item.getInventoryDescription()).append(" ");
        }
        return "You're carrying:" + result;
    }

    /**
     * Drop everything from inventory at current location and move player to start location of the game
     * by setting current location to dirt road
     */
    public void die() {
        final List<Item> inventoryCopy = new ArrayList<>(inventory);
        for(Item item : inventoryCopy) {
            removeItemFromInventory(item);
            currentLocation.addItemToLocation(item);
        }
        // Set current location to driveway by doing a breadth first search starting from current location
        currentLocation = findLocation(currentLocation, LocationNames.DRIVEWAY);
    }

    /**
     * Breadth first search to find the start location of the game, which is the driveway. Start the search from
     * the current location
     * @return driveway location if it's found or null if it's not. Should never return null.
     */
    public static Location findLocation(final Location startLocation, final String targetLocationName) {
        // if you're at the dirt road, just return
        if(startLocation.getName().equals(targetLocationName)) {
            return startLocation;
        }
        final LinkedList<Location> queue = new LinkedList<>();
        queue.add(startLocation);

        final List<Location> locationsVisited = new ArrayList<>();
        // BFS algorithm
        while(!queue.isEmpty()) {
            final Location current = queue.removeFirst();
            if(current.bfsIsVisited) {
                continue;
            }

            if(current.getName().equals(targetLocationName)) {
                // Set bfsIsVisited to false for every node that was visited in case we need to do a search again
                locationsVisited.forEach(location -> location.setBfsIsVisited(false));
                return current;
            }
            current.bfsIsVisited = true;
            locationsVisited.add(current);
            final List<ConnectingLocation> neighbors = current.getConnectingLocations();

            if(neighbors == null) {
                continue;
            }
            for(final ConnectingLocation neighbor : neighbors) {
                if(!neighbor.getLocation().bfsIsVisited) {
                    queue.add(neighbor.getLocation());
                }
            }
        }

        // Set bfsIsVisited to false for every node that was visited in case we need to do a search again
        locationsVisited.forEach(location -> location.setBfsIsVisited(false));
        return null;
    }

    @Override
    public int compare(final Item item1, final Item item2) {
        return item1.getDisplayOrder() - item2.getDisplayOrder();
    }
}
