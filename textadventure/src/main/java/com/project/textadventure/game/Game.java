package com.project.textadventure.game;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.constants.ResponseConstants;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.controllers.GameStatus;
import com.project.textadventure.game.Graph.LocationConnection;
import com.project.textadventure.game.Graph.Dam;
import com.project.textadventure.game.Graph.Item;
import com.project.textadventure.game.Graph.Location;
import com.project.textadventure.game.Graph.MineEntrance;
import com.project.textadventure.game.Graph.MineShaft;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.*;

import static com.project.textadventure.constants.GameConstants.*;
import static com.project.textadventure.constants.ItemConstants.PIE_NAME;
import static com.project.textadventure.constants.LocationDescriptions.BOTTOM_MINE_SHAFT_NO_CAGE_LONG_DESCRIPTION;
import static com.project.textadventure.constants.LocationDescriptions.BOTTOM_MINE_SHAFT_WITH_CAGE_LONG_DESCRIPTION;
import static com.project.textadventure.constants.LocationDescriptions.MOUNTAIN_PASS_NO_CAGE_LONG_DESCRIPTION;
import static com.project.textadventure.constants.LocationDescriptions.MOUNTAIN_PASS_WITH_CAGE_LONG_DESCRIPTION;
import static com.project.textadventure.constants.LocationNames.BOTTOM_OF_VERTICAL_MINE_SHAFT;
import static com.project.textadventure.constants.LocationNames.MINE_CAGE;
import static com.project.textadventure.constants.LocationNames.MOUNTAIN_PASS;
import static com.project.textadventure.constants.ResponseConstants.HELP_RESPONSE;
import static com.project.textadventure.constants.ResponseConstants.INFO_RESPONSE;


@Getter
@Setter
public class Game implements Action, Comparator<Item> {
    private List<Item> inventory;
    // Limit based on weight
    private int inventoryLimit = 10;
    private Location currentLocation;
    private GameStatus gameStatus;

    public Game(final List<Item> inventory, final Location currentLocation, final GameStatus status) {
        this.inventory = inventory;
        this.currentLocation = currentLocation;
        this.gameStatus = status;
    }

    public Game() {}

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Item getInventoryItemByName(final String name) {
        for(final Item item : this.inventory) {
            if (StringUtils.equals(item.getName(), name)) {
                return item;
            }
        }
        return null;
    }

    public boolean isItemInInventory(final String itemName) {
        return getInventoryItemByName(itemName) != null;
    }

    public String addItemToInventory(final Item item) {
        // Add points to score if the item has points associated with it
        if (getInventoryWeight() > inventoryLimit) {
            return "You can't carry anything more.";
        }
        if (item.getPoints() > 0) {
            GameState.getInstance().incrementScore(item.getPoints());
        }
        inventory.add(item);
        inventory.sort(GameState.getInstance().getGame());
        return null;
    }

    private Double getInventoryWeight() {
        double totalWeight = 0;
        for (final Item item : inventory) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

    public void removeItemFromInventory(final Item item) {
        inventory.remove(item);
        // Remove points from score if the item has points associated with it
        if (item.getPoints() > 0) {
            GameState.getInstance().decrementScore(item.getPoints());
        }
    }

    public static String generateRandomUnknownCommandResponse() {
        final Random r = new Random();
        final int randomNum = r.nextInt(3);
        return switch (randomNum) {
          case 0 -> ResponseConstants.I_DONT_UNDERSTAND;
          case 1 -> ResponseConstants.WHAT;
          default -> ResponseConstants.DONT_KNOW_WORD;
        };
    }

    /**
     * Based on the user input, call the relevant method to handle the action.
     * @param verb The verb part of the command.
     * @param noun The noun part of the command.
     * @return Response to display to the user.
     */
    @Override
    public String takeAction(@NonNull String verb, @Nullable String noun) {
        if (isGetCommand(verb, noun)) {
            noun = noun == null ? noun : simplifyNoun(noun);
            return handleGetCommand(noun);

        } else if (StringUtils.equals(verb, FILL)) {
            return handleFillCommand(noun);

        } else if (StringUtils.equals(verb, DROP) || StringUtils.equals(verb, THROW)) {
            return handleDropCommand(noun);

        } else if (StringUtils.equals(verb, EAT)) {
            return handleEatCommand(noun);

        } else if (StringUtils.equals(verb, PUSH) || StringUtils.equals(verb, PRESS)) {
            return handlePushCommand(verb, noun);

        } else if (noun == null) {
            // Command was only one word
            if (StringUtils.equals(verb, INVENTORY_LONG) || StringUtils.equals(verb, INVENTORY_SHORT) || StringUtils.equals(verb, INVENTORY_MEDIUM)) {
                return takeInventory();

            } else if (StringUtils.equals(verb, QUIT) || StringUtils.equals(verb, RESTART)) {
                gameStatus = GameStatus.QUITTING;
                return "Are you sure you want to " + (StringUtils.equals(verb, QUIT) ? "quit?" : "restart?");

            } else if (StringUtils.equals(verb, SCORE)) {
                return "Your score: " + GameState.getInstance().getScore();

            } else if (StringUtils.equals(verb, INFO)) {
                return INFO_RESPONSE;

            } else if (StringUtils.equals(verb, HELP)) {
                return HELP_RESPONSE;
            }
        }
        return generateRandomUnknownCommandResponse();

    }

    /**
     * Check if the command is any form of a get command. Could be "get", "take", or "pick up".
     * @param verb The verb part of the command.
     * @param noun The noun part of the command.
     * @return True if the command is a get command, false otherwise.
     */
    public static boolean isGetCommand(@NonNull String verb, @Nullable final String noun) {
        return StringUtils.equals(verb, GET) || StringUtils.equals(verb, TAKE) ||
                (noun != null && StringUtils.equals(verb, "pick") && StringUtils.equals(noun.substring(0, 2), "up"));
    }

    /**
     * When the command is parsed for "pick up", it comes out as verb = "pick", noun = "up [item]".
     * This method simplifies the noun part of the command to just the item.
     * @param noun The noun part of the command.
     */
    String simplifyNoun(@NonNull String noun) {
        // Simplify "pick up" to "get"
        if (StringUtils.equals(noun, "up")) {
            noun = null;
        } else if (StringUtils.equals(noun.substring(0, 2), "up ")) {
            noun = noun.substring(3);
        }
        return noun;
    }

    /**
     * Add item to inventory and/or do any special handling for specific items at specific locations
     * @param itemName item to get
     * @return response to user
     */
    private String handleGetCommand(final String itemName) {
        if (itemName == null) {
            return ResponseConstants.WHAT_DO_YOU_WANT_TO_GET;
        }
        final Item item = currentLocation.getLocationItemByName(itemName);
        final Item jar = getInventoryItemByName(ItemConstants.JAR_NAME);
        final boolean isJarInInventory = isItemInInventory(ItemConstants.JAR_NAME);

        if (item == null) {
            if (itemName.equals(ItemConstants.NAILS_NAME) && currentLocation instanceof MineEntrance && !((MineEntrance) currentLocation).areNailsOff()) {
                if (((MineEntrance) currentLocation).areNailsOff()) {
                    // The user has previously taken the nails by hand instead of shot them with the arrow as intended, and they are now inaccessible
                    return  "The nails are buried under rubble from the collapsed mine; you can't get to them.";
                } else {
                    // If it hasn't collapsed, they haven't taken the nails at all, prompt/warn to make sure they actually want to take them
                    GameState.getInstance().getGame().setGameStatus(GameStatus.GETTING_NAILS);
                    return  "Are you sure you want to get the nails? The structure is very fragile and may fall apart and onto you.";
                }
            } else if (itemName.equals(ItemConstants.MAGNET_NAME) && currentLocation instanceof Dam && ((Dam) currentLocation).isMagnetDropped()) {
                return  "The magnet is firmly attached to the wheel";
            }
        }
        else if (getInventoryItemByName(itemName) != null) {
            return ResponseConstants.ALREADY_CARRYING;

        } else if (itemName.equals(ItemConstants.GOLD_NAME) && !isJarInInventory) {
            return  "You need something to hold the gold flakes.";

        } else if (itemName.equals(ItemConstants.GOLD_NAME) && isJarInInventory) {
            final String result = addItemToInventory(item);
            if (result != null) {
                return result;
            }
            jar.setInventoryDescription("Jar full of gold flakes");
            currentLocation.removeItemFromLocation(item);
            return "The jar is now full of gold flakes.";

        } else {
            final String result = addItemToInventory(item);
            if (result != null) {
                return result;
            }
            if (currentLocation.getName().equals(LocationNames.CRUMPLED_MINE_CART)) {
                // If the user has taken the ruby from the mine cart location, update the description to not include the ruby.
                currentLocation.setDescription("You've reached a dead end. A crumpled mine cart, no longer able to run on the rails, is laying on its side.");
            }
            currentLocation.removeItemFromLocation(item);
            return ResponseConstants.OK;
        }
        return "I don't see that here.";
    }

    /**
     * Drop an item from the player's inventory at the current location. If the item is dropped at the dam and the item is the magnet, the dam's
     * description is updated to reflect the dropped magnet. If the item is dropped at the boat, the item is lost forever.
     * Otherwise, just add the item to the location and remove it from the inventory
     * @param noun item to drop
     * @return response to user
     */
    private String handleDropCommand(@Nullable final String noun) {
        if (noun == null) {
            return "What to want to drop?";
        }
        final Item item = getInventoryItemByName(noun);
        if (item == null) {
            return "You're not carrying that!";
        }
        if (noun.equals("jar")) {
            final Item gold = getInventoryItemByName(ItemConstants.GOLD_NAME);
            if (gold != null) {
                item.setInventoryDescription("Jar");
                currentLocation.addItemToLocation(gold);
                removeItemFromInventory(gold);
            }
        }
        if (noun.equals("gold")) {
            final Item jar = getInventoryItemByName(ItemConstants.JAR_NAME);
            jar.setInventoryDescription(ItemConstants.JAR_INVENTORY_DESCRIPTION);
        }
        if (noun.equals(ItemConstants.MAGNET_NAME) && currentLocation instanceof Dam) {
            removeItemFromInventory(item);
            ((Dam) currentLocation).setMagnetDropped(true);
            return "You drop the magnet and as it's falling it snaps to the shiny center of the wheel. A faint, mechanical clicking comes from deep inside the dam.";
        }
        if (currentLocation.getName().equals(LocationNames.BOAT)) {
            removeItemFromInventory(item);
            return "You're " + item.getName() + " splashes into the water next to the boat and sinks to the bottom, never to be found again.";
        }
        removeItemFromInventory(item);
        currentLocation.addItemToLocation(item);
        return ResponseConstants.OK;
    }

    /**
     * Fill the jar with gold flakes. As of right now, the only item that can be filled is the jar with gold flakes.
     * When the jar is filled, the gold flakes are removed from the current location and added to the player's
     * inventory, and the jar's description is updated.
     * @param itemToFill item to fill
     * @return response to user
     */
    private String handleFillCommand(final String itemToFill) {
        if (!(itemToFill == null || itemToFill.equals(ItemConstants.JAR_NAME))) {
            return "That's not something you can fill.";
        }
        final Item jar = getInventoryItemByName(ItemConstants.JAR_NAME);
        final Item gold = currentLocation.getLocationItemByName(ItemConstants.GOLD_NAME);
        if (jar == null) {
            return "You don't have anything to fill.";
        }
        if (gold == null) {
            return "There's nothing here to fill your jar with.";
        }
        if (isItemInInventory(ItemConstants.GOLD_NAME)) {
            return "The jar is already full of gold flakes.";
        }
        jar.setInventoryDescription("Jar full of gold flakes");
        final String result = addItemToInventory(gold);
        if (result != null) {
            return result;
        }
        currentLocation.removeItemFromLocation(gold);
        return  "The jar is now full of gold flakes.";
    }

    /**
     * Format the user's inventory to display to the user or return a message saying inventory is empty
     * @return the user's inventory
     */
    private String takeInventory() {
        if (this.inventory.isEmpty()) {
            return ResponseConstants.NOT_CARRYING;
        }
        final StringBuilder result = new StringBuilder();
        for (final Item item : this.inventory) {
            result.append("\n").append(item.getInventoryDescription()).append(" ");
        }
        return "You're carrying:" + result;
    }

    private String handleEatCommand(final String itemName) {
        // Check that the pie is in the inventory and if the prompt for item to eat is a pie
        // or empty (command is just "eat").
        // If it is, remove the pie from the inventory and add 3.14 to the score.
        final Item item = getInventoryItemByName(itemName);

        if (isItemInInventory(PIE_NAME) && (StringUtils.isEmpty(itemName) || StringUtils.equals(itemName, PIE_NAME))) {
            removeItemFromInventory(getInventoryItemByName(PIE_NAME));
            GameState.getInstance().incrementScore(3.14);
            return "You eat the pie. It's delicious. You earned 3.14 points.";
        }

        // Item is not found in the inventory
        if (item == null) {
            return StringUtils.isEmpty(itemName) ? "You don't have anything to eat." : "You're not carrying it!";
        }

        // The item is found but not edible
        return "That's not something you can eat.";
    }

    /**
     * Handle the push command. The only thing that can be pushed is the button in the mine cage.
     * @param noun thing to push. null if the user just says "push"
     * @return response to display to the user
     */
    private String handlePushCommand(final String verb, final String noun) {
        String result = "";
        if (currentLocation instanceof MineShaft) {
            if (StringUtils.equals(BUTTON, noun) || noun == null) {
                result = ((MineShaft) currentLocation).moveMineCage();
            } else {
                result = "You can't " + (StringUtils.equals(verb, PRESS) ? "press" : "push") + " that.";
            }
        } else {
            result = "There's nothing to push here.";
        }
        return result;
    }



    /**
     * Check if any of the locations connected to the current location is the mine cage. If it is, return the mine cage location
     * @return mine cage location if it's connected to the current location, null otherwise
     */
    public boolean isLocationDirectlyConnected(final String locationName) {
        for (final LocationConnection locationConnection : currentLocation.getLocationConnections()) {
            final Location location = locationConnection.getLocation();
            if (location.getName().equals(locationName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Drop everything from inventory at current location and move player to start location of the game
     * by setting current location to dirt road
     */
    public String die() {
        // Lose a life
        GameState.getInstance().decrementLifeCount();

        // Drop everything from inventory at current location
        final List<Item> inventoryCopy = new ArrayList<>(inventory);
        for (final Item item : inventoryCopy) {
            removeItemFromInventory(item);
            currentLocation.addItemToLocation(item);
        }

        // Based on location, the message the user sees when they die is different.
        String dieMessage = "";
        if (currentLocation instanceof MineEntrance) {
            dieMessage = DEATH_BY_MINE_SHAFT;
        }
        // Set current location to driveway by doing a breadth first search starting from current location
        currentLocation = findLocationByName(currentLocation, LocationNames.DRIVEWAY);

        if (GameState.getInstance().getLifeCount() == 0) {
            gameStatus = GameStatus.LOSE;
            dieMessage += "\nYou lose.";
        }

        return dieMessage;
    }

    /**
     * Breadth first search to find the start location of the game, which is the driveway. Start the search from
     * the current location
     * @return driveway location if it's found or null if it's not. Should never return null.
     */
    public static Location findLocationByName(final Location startLocation, final String targetLocationName) {
        // if you're at the dirt road, just return
        if (startLocation.getName().equals(targetLocationName)) {
            return startLocation;
        }
        final LinkedList<Location> queue = new LinkedList<>();
        queue.add(startLocation);

        final List<Location> locationsVisited = new ArrayList<>();
        // BFS algorithm
        while (!queue.isEmpty()) {
            final Location current = queue.removeFirst();
            if (current.bfsIsVisited) {
                continue;
            }

            if (current.getName().equals(targetLocationName)) {
                // Set bfsIsVisited to false for every node that was visited in case we need to do a search again
                locationsVisited.forEach(location -> location.setBfsIsVisited(false));
                return current;
            }
            current.bfsIsVisited = true;
            locationsVisited.add(current);

            for (final LocationConnection neighbor : current.getLocationConnections()) {
                if (!neighbor.getLocation().bfsIsVisited) {
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
