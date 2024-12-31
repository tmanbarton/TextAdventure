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
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

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
import static com.project.textadventure.constants.ResponseConstants.INFO_RESPONSE;


@Getter
@Setter
public class Game implements Action, Comparator<Item> {
    private List<Item> inventory;
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
            if (name.equals(item.getName())) {
                return item;
            }
        }
        return null;
    }

    public boolean isItemInInventory(final String itemName) {
        return getInventoryItemByName(itemName) != null;
    }

    public void addItemToInventory(final Item item) {
        // Add points to score if the item has points associated with it
        if (item.getPoints() > 0) {
            GameState.getInstance().incrementScore(item.getPoints());
        }
        inventory.add(item);
        inventory.sort(new Game());
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

    @Override
    public String takeAction(String verb, final String noun) {
        String result = "";
        // To lowercase to be able to compare to enum constants to string
        verb = verb.toLowerCase();
        if (StringUtils.equals(verb, GET) || StringUtils.equals(verb, TAKE)) {
            result = handleGetCommand(noun);
        } else if (StringUtils.equals(verb, FILL)) {
            result = handleFillCommand(noun);
        } else if (StringUtils.equals(verb, INVENTORY) || StringUtils.equals(verb, I) || StringUtils.equals(verb, INVEN)) {
            result = takeInventory();
        } else if (StringUtils.equals(verb, DROP) || StringUtils.equals(verb, THROW)) {
            result = handleDropCommand(noun);
        } else if (StringUtils.equals(verb, EAT)) {
            result = handleEatCommand(noun);
        } else if (StringUtils.equals(verb, PUSH)) {
            result = handlePushCommand(noun);
        } else if (StringUtils.equals(verb, QUIT) || StringUtils.equals(verb, RESTART)) {
            result = "Are you sure you want to " + (StringUtils.equals(verb, QUIT) ? "quit?" : "restart?");
            gameStatus = GameStatus.QUITTING;
        } else if (StringUtils.equals(verb, SCORE)) {
            return "Your score: " + GameState.getInstance().getScore();
        } else if (StringUtils.equals(verb, INFO)) {
            return INFO_RESPONSE;
        } else if (StringUtils.equals(verb, HELP)) {
            return ResponseConstants.HELP_RESPONSE;
        }
        return result;
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
        String result = "";
        final Item item = currentLocation.getLocationItemByName(itemName);
        final Item jar = getInventoryItemByName(ItemConstants.JAR_NAME);

        if (item == null) {
            result = "I don't see that here.";
            if (itemName.equals(ItemConstants.NAILS_NAME) && currentLocation instanceof MineEntrance && !((MineEntrance) currentLocation).areNailsOff()) {
                if (((MineEntrance) currentLocation).isCollapsed()) {
                    // The user has previously taken the nails by hand instead of shot them with the arrow as intended, and they are now inaccessible
                    result = "The nails are buried under rubble from the collapsed mine; you can't get to them.";
                } else {
                    // If it hasn't collapsed, they haven't taken the nails at all, prompt/warn to make sure they actually want to take them
                    result = "Are you sure you want to get the nails? The structure is very fragile and may fall apart and onto you.";
                    GameState.getInstance().getGame().setGameStatus(GameStatus.GETTING_NAILS);
                }
            }
        } else if (getInventoryItemByName(itemName) != null) {
            result = ResponseConstants.ALREADY_CARRYING;

        } else if (itemName.equals(ItemConstants.MAGNET_NAME) && currentLocation instanceof Dam && ((Dam) currentLocation).isMagnetDropped()) {
            result = "The magnet is firmly attached to the wheel";
        } else if (itemName.equals(ItemConstants.GOLD_NAME) && !isItemInInventory(ItemConstants.JAR_NAME)) {
            result = "You need something to hold the gold flakes.";

        } else if (itemName.equals(ItemConstants.GOLD_NAME) && isItemInInventory(ItemConstants.JAR_NAME)) {
            jar.setInventoryDescription("Jar full of gold flakes");
            addItemToInventory(item);
            currentLocation.removeItemFromLocation(item);
            result = "The jar is now full of gold flakes.";

        } else {
            if (currentLocation.getName().equals(LocationNames.CRUMPLED_MINE_CART)) {
                // If the user has taken the ruby from the mine cart location, update the description to not include the ruby.
                currentLocation.setDescription("You've reached a dead end. A crumpled mine cart, no longer able to run on the rails, is laying on its side.");
            }
            addItemToInventory(item);
            currentLocation.removeItemFromLocation(item);
            result = ResponseConstants.OK;
        }
        return result;
    }

    private String handleDropCommand(final String noun) {
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
            currentLocation.setDescription("You're on a short dam that created this lake by stopping up a large river. The dam goes north and south along the east end of the lake. Close by is a wheel with its axel extending deep into the dam. Its orange metal has faded to rust except for some different metal at the center, shining in the sun. There's a large magnet stuck to this part of the wheel. South leads around the lake and to the north there's a set of stairs.");
            return "You drop the magnet and as it's falling it snaps to the shiny center of the wheel. You can hear " +
                    "some mechanical clicking somewhere inside the dam.";
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
        addItemToInventory(gold);
        currentLocation.removeItemFromLocation(gold);
        return  "The jar is now full of gold flakes.";
    }

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
        if (StringUtils.equals(itemName, PIE_NAME)) {
            GameState.getInstance().incrementScore(3.14);
            removeItemFromInventory(getInventoryItemByName(PIE_NAME));
            return "You eat the pie. It's delicious. You earned 3.14 points.";
        } else {
            return "That's not something you can eat.";
        }
    }

    /**
     * Handle the push command. The only thing that can be pushed is the button in the mine cage.
     * @param noun thing to push. null if the user just says "push"
     * @return response to send to the terminal
     */
    private String handlePushCommand(final String noun) {
        String result = "";
        if (currentLocation.getName().equals(LocationNames.MINE_CAGE) ||
                currentLocation.getName().equals(LocationNames.MOUNTAIN_PASS) ||
                currentLocation.getName().equals(LocationNames.BOTTOM_OF_VERTICAL_MINE_SHAFT)) {
            if (StringUtils.equals("button", noun) || noun == null) {
                result = moveMineCage();
            } else {
                result = "You can't push that.";
            }
        } else {
            result = "There's nothing to push here.";
        }
        return result;
    }

    /**
     * Determine if the mine cage is at the top or bottom of the mine shaft. If it's at the top, remove Mountain Pass from its connecting location
     * and add Bottom of Vertical Mine Shaft. If it's at the bottom, remove Bottom of Vertical Mine Shaft from its connecting location and add Mountain Pass.
     *
     * @return response to send to the terminal
     */
    private String moveMineCage() {
        String result = "The cage rattles as it starts to move and, as it reaches the end of the shaft, grinds to a halt with the squeaking of chains and pulleys.";
        final Location bottomOfVerticalMineShaft = findLocationByName(currentLocation, BOTTOM_OF_VERTICAL_MINE_SHAFT);
        final Location mountainPass = findLocationByName(currentLocation, MOUNTAIN_PASS);

        if (StringUtils.equals(currentLocation.getName(), MOUNTAIN_PASS)) {
            result = moveMineCageFromAConnectingLocation(bottomOfVerticalMineShaft);
        } else if (StringUtils.equals(currentLocation.getName(), BOTTOM_OF_VERTICAL_MINE_SHAFT)) {
            result = moveMineCageFromAConnectingLocation(mountainPass);
        } else if (StringUtils.equals(currentLocation.getName(), MINE_CAGE)) {
            // Current location is mine cage location.
            // Change connections based on whether it's connected to mountain pass or bottom of vertical mine shaft
            final boolean isConnectedToMountainPass = isLocationDirectlyConnected(MOUNTAIN_PASS);
            final boolean isConnectedToBottomMineShaft = isLocationDirectlyConnected(BOTTOM_OF_VERTICAL_MINE_SHAFT);
            if (isConnectedToMountainPass) {
                moveMineCageFromWithinMineCage(bottomOfVerticalMineShaft, mountainPass);

            } else if (isConnectedToBottomMineShaft) {
                moveMineCageFromWithinMineCage(mountainPass, bottomOfVerticalMineShaft);
            }
        }
        return result;
    }

    /**
     * Moves the mine cage from a connecting location (either the mountain pass or the bottom of the vertical mine shaft)
     * to the opposite location. Updates the connections and descriptions of the involved locations accordingly.
     *
     * @param targetLocation The target location to which the mine cage will be moved.
     * @return A response string indicating the result of moving the mine cage.
     */
    String moveMineCageFromAConnectingLocation(final Location targetLocation) {
        final Location mineCage = findLocationByName(currentLocation, MINE_CAGE);
        final List<LocationConnection> currentLocationConnections = currentLocation.getLocationConnections();

        final boolean isMineCageConnected = isLocationDirectlyConnected(MINE_CAGE);
        if (isMineCageConnected) {
            // The mine cage is currently connected to the current location and is moving to the opposite location
            // Remove mine cage from current location and vice versa, and add target location to mine cage and vice versa
            currentLocationConnections.removeIf(locationConnection -> locationConnection.getLocation().getName().equals(MINE_CAGE));
            mineCage.setLocationConnections(List.of(new LocationConnection(currentLocation.getName().equals(MOUNTAIN_PASS) ? List.of(WEST_LONG, WEST_SHORT, OUT, EXIT) : List.of(EAST_LONG, EAST_SHORT, OUT, EXIT), targetLocation)));
            targetLocation.getLocationConnections().add(new LocationConnection(currentLocation.getName().equals(MOUNTAIN_PASS) ? List.of(EAST_LONG, EAST_SHORT, IN, ENTER) : List.of(WEST_LONG, WEST_SHORT, IN, ENTER), mineCage));

            // Update the descriptions of the locations to reflect whether the mine cage is there or not
            currentLocation.setDescription(currentLocation.getName().equals(MOUNTAIN_PASS) ? MOUNTAIN_PASS_NO_CAGE_LONG_DESCRIPTION : BOTTOM_MINE_SHAFT_NO_CAGE_LONG_DESCRIPTION);
            targetLocation.setDescription(currentLocation.getName().equals(MOUNTAIN_PASS) ? BOTTOM_MINE_SHAFT_WITH_CAGE_LONG_DESCRIPTION : MOUNTAIN_PASS_WITH_CAGE_LONG_DESCRIPTION);

            return currentLocation.getName().equals(MOUNTAIN_PASS) ?
                    "The mine cage descends into the depths of the mountain with much rattling, creaking, and squeaking. After a moment you hear a faint 'boom' as the cage hits the bottom." :
                    "The mine cage ascends into the shaft with much rattling, creaking, and squeaking. After a moment you hear the cage rattle as it reaches the top.";
        } else {
            // Current location is bottom of mine shaft location and the mine cage is at the top of the shaft (mountainPass location)
            // Remove mountain pass from mine cage and vice versa, and add bottom of mine shaft to cage and vice versa
            targetLocation.getLocationConnections().removeIf(locationConnection -> locationConnection.getLocation().getName().equals(MINE_CAGE));
            currentLocationConnections.add(new LocationConnection(currentLocation.getName().equals(MOUNTAIN_PASS) ? List.of(WEST_LONG, WEST_SHORT, IN, ENTER) : List.of(EAST_LONG, EAST_SHORT, IN, ENTER), mineCage));
            mineCage.setLocationConnections(List.of(new LocationConnection(currentLocation.getName().equals(MOUNTAIN_PASS) ? List.of(EAST_LONG, EAST_SHORT, OUT, EXIT) : List.of(WEST_LONG, WEST_SHORT, OUT, EXIT), currentLocation)));

            // Update the descriptions of the locations to reflect whether the mine cage is there or not
            currentLocation.setDescription(currentLocation.getName().equals(MOUNTAIN_PASS) ? MOUNTAIN_PASS_WITH_CAGE_LONG_DESCRIPTION : BOTTOM_MINE_SHAFT_WITH_CAGE_LONG_DESCRIPTION);
            targetLocation.setDescription(currentLocation.getName().equals(MOUNTAIN_PASS) ? BOTTOM_MINE_SHAFT_NO_CAGE_LONG_DESCRIPTION : MOUNTAIN_PASS_NO_CAGE_LONG_DESCRIPTION);

            return currentLocation.getName().equals(MOUNTAIN_PASS) ?
                    "The mine cage ascends from the depths of the mountain with much rattling, creaking, and squeaking. The distant noise soon becomes more dominant as it rumbles into view and lurches to a stop." :
                    "The mine cage descends from the depths of the mountain above with much rattling, creaking, and squeaking. The distant noise soon becomes more dominant as it rumbles into view.";
        }
    }

    /**
     * Moves the mine cage while the player is in the mine cage/current location is mine cage to the target location. Updates the connections and descriptions
     * of the involved locations accordingly.
     *
     * @param targetLocation The target location to which the mine cage will be moved.
     * @param locationConnectedToMineCage The current location that's connected to the mine cage (either the mountain pass or the bottom of the vertical mine shaft).
     */
    void moveMineCageFromWithinMineCage(final Location targetLocation, final Location locationConnectedToMineCage) {
        // Mine cage is currently at the bottom of the mine shaft, connected to the bottom of vertical mine shaft location.
        // Remove bottom of mine shaft from mine cage, add mountain pass to mine cage, and set cage connections to mountain pass
        locationConnectedToMineCage.getLocationConnections().removeIf(locationConnection -> locationConnection.getLocation().getName().equals(MINE_CAGE));
        currentLocation.setLocationConnections(List.of(new LocationConnection(locationConnectedToMineCage.getName().equals(MOUNTAIN_PASS) ? List.of(WEST_LONG, WEST_SHORT, OUT, EXIT) : List.of(EAST_LONG, EAST_SHORT, OUT, EXIT), targetLocation)));
        targetLocation.getLocationConnections().add(new LocationConnection(currentLocation.getName().equals(MOUNTAIN_PASS) ? List.of(WEST_LONG, WEST_SHORT, IN, ENTER) : List.of(EAST_LONG, EAST_SHORT, IN, ENTER), currentLocation));

        // Update descriptions of the locations to reflect whether the mine cage is there or not
        targetLocation.setDescription(locationConnectedToMineCage.getName().equals(MOUNTAIN_PASS) ? BOTTOM_MINE_SHAFT_WITH_CAGE_LONG_DESCRIPTION : MOUNTAIN_PASS_WITH_CAGE_LONG_DESCRIPTION);
        locationConnectedToMineCage.setDescription(locationConnectedToMineCage.getName().equals(MOUNTAIN_PASS) ? MOUNTAIN_PASS_NO_CAGE_LONG_DESCRIPTION : BOTTOM_MINE_SHAFT_NO_CAGE_LONG_DESCRIPTION);
    }

    /**
     * Check if any of the locations connected to the current location is the mine cage. If it is, return the mine cage location
     * @return mine cage location if it's connected to the current location, null otherwise
     */
    private boolean isLocationDirectlyConnected(final String locationName) {
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

        if (GameState.getInstance().getLifeCount() == 0) {
            gameStatus = GameStatus.LOSE;
            return "You lose.";
        }

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
            final List<LocationConnection> neighbors = current.getLocationConnections();

            if (neighbors == null) {
                continue;
            }
            for (final LocationConnection neighbor : neighbors) {
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
