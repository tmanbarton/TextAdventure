package com.project.textadventure.game.Graph;

import com.project.textadventure.constants.GameConstants;
import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.game.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.project.textadventure.constants.GameConstants.BAD_DIRECTION;
import static com.project.textadventure.constants.GameConstants.GO;
import static com.project.textadventure.constants.GameConstants.LOOK_LONG;
import static com.project.textadventure.constants.GameConstants.LOOK_SHORT;
import static com.project.textadventure.constants.GameConstants.OPEN;
import static com.project.textadventure.constants.GameConstants.SHOOT;
import static com.project.textadventure.constants.GameConstants.TURN;
import static com.project.textadventure.constants.GameConstants.UNLOCK;
import static com.project.textadventure.constants.ItemConstants.ARROW_NAME;
import static com.project.textadventure.constants.ItemConstants.BOW_NAME;
import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

/**
 * Represents a location in the game. Essentially a node in the graph.
 */
@Data
public class Location implements Action {
    private String description;
    private List<Item> items;
    private List<LocationConnection> locationConnections;
    private boolean visited;
    public boolean bfsIsVisited;
    private String name;
    private String shortDescription;
    String[] commands;

    public Location(
            final String fullDescription,
            final String shortDescription,
            final List<Item> items,
            final List<LocationConnection> locationConnections,
            final boolean visited,
            final String name) {
        this.description = fullDescription;
        this.items = items;
        this.locationConnections = locationConnections;
        this.visited = visited;
        this.name = name;
        this.shortDescription = shortDescription;
        this.bfsIsVisited = false;
    }

    /**
     * Connect this {@link Location} to another {@link Location}. Essentially an edge in the graph.
     * @param locationToConnect Location to connect to
     */
    public void connectLocation(final LocationConnection locationToConnect) {
        this.locationConnections.add(locationToConnect);
    }

    /**
     * Get the {@link Item} at the {@link Location} by name if it's there, otherwise null.
     * @param name Name of the item
     * @return {@link Item} at the {@link Location} with the given name, or null if not found
     */
    public Item getLocationItemByName(final String name) {
        for(final Item item : this.items) {
            if (name.equals(item.getName())) {
                return item;
            }
        }
        return null;
    }

    /**
     * Add an {@link Item} to the {@link Location}.
     * @param item Item to add
     */
    public void addItemToLocation(final Item item) {
        items.add(item);
    }

    /**
     * Remove an {@link Item} from the {@link Location}.
     * @param item Item to remove
     */
    public void removeItemFromLocation(final Item item) {
        items.remove(item);
    }

    /**
     * Determine what action to take based on the verb and noun and execute the action.
     * @param verb Verb part of the command
     * @param noun Noun part of the command, could be empty
     * @return Response to the command to display to the user
     */
    @Override
    public String takeAction(@NonNull String verb, @Nullable final String noun) {
        // If the verb is "go", then we expect the noun to be the direction
        final String newNoun = StringUtils.equals(verb, GO) ? null : noun;
        final String newVerb = StringUtils.equals(verb, GO) ? noun : verb;

        if (isDirection(newVerb, newNoun)) {
            return move(newVerb);
        } else if ((StringUtils.equals(verb, LOOK_LONG) || StringUtils.equals(verb, LOOK_SHORT)) && StringUtils.isEmpty(noun)) {
            return look();
        } else if (verb.equals(UNLOCK)) {
            return "There's nothing to unlock here.";
        } else if (verb.equals(OPEN)) {
            return "There's nothing to open here.";
        } else if (verb.equals(SHOOT)) {
            return parseShootCommand(noun);
        } else if (verb.equals(TURN)) {
            return "There's nothing here to turn.";
        } else {
            return generateRandomUnknownCommandResponse();
        }
    }

    /**
     * Check if the input is a valid direction based on the game constants.
     * @param verb Input to check
     * @return True if the input is a valid direction, false otherwise
     */
    boolean isDirection(String verb, String noun) {
        if (StringUtils.isNotEmpty(noun)) {
            return false;
        }
        return GameConstants.ALL_DIRECTIONS.contains(verb);
    }

    String move(final String direction) {
        final Game game = GameState.getInstance().getGame();
        final Location currentLocation = game.getCurrentLocation();
        final Optional<LocationConnection> connection = currentLocation.getLocationConnections()
                .stream()
                .filter(connectingLocation -> connectingLocation.getDirections().contains(direction))
                .findFirst();

        if (connection.isEmpty()) {
            return BAD_DIRECTION;
        }
        final LocationConnection locationConnection = connection.get();
        return moveToLocation(locationConnection);
    }

    /**
     * Move to the {@link Location} connected by the given connection.
     * @param locationConnection Connection to the {@link Location} to move to
     * @return Description of the new {@link Location} + {@link Item}s at the location
     */
    private String moveToLocation(final LocationConnection locationConnection) {
        final Location toLocation = locationConnection.getLocation();

        GameState.getInstance().getGame().setCurrentLocation(toLocation);

        if (toLocation.isVisited()) {
            return toLocation.getShortDescription() + listLocationItems(toLocation.getItems());
        }
        toLocation.setVisited(true);
        return toLocation.getDescription() + listLocationItems(toLocation.getItems());
    }

    /**
     * Display the current {@link Location}'s description and list the {@link Item}s at the {@link Location}.
     * @return Description of the current location + items at the {@link Location}
     */
    public String look() {
        final Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();
        return currentLocation.getDescription() + listLocationItems(currentLocation.getItems());
    }

    /**
     * List the items at the location.
     * @param items Items to list
     * @return List of {@link Item}s at the {@link Location} as a String
     */
    String listLocationItems(final List<Item> items) {
        items.sort(Comparator.comparingInt(Item::getDisplayOrder));
        final StringBuilder result = new StringBuilder();
        for(final Item item : items) {
            result.append("\n").append(item.getLocationDescription());
        }
        return result.isEmpty() ? "" : "\n" + result;
    }


    /**
     * Parse the shoot command. If the noun is null or "arrow", shoot the arrow. Otherwise, return a response indicating why you can't shoot.
     * @param thingToShoot Noun of the command
     * @return Response to the shoot command
     */
    String parseShootCommand(final String thingToShoot) {
        final Game game = GameState.getInstance().getGame();
        String response = "";

        if (!game.isItemInInventory(BOW_NAME)) {
            // Must have the bow and arrow in your inventory
            response = "You don't have anything to shoot with.";
        } else if (!game.isItemInInventory(ARROW_NAME)) {
            response = "You don't have anything to shoot.";
        } else if (StringUtils.equals(thingToShoot, ARROW_NAME) || thingToShoot == null) {
            // Shoot the arrow
            response = shootArrow();
        } else {
            // User has the bow and arrow, but tries to shoot something other than the arrow
            response = "You can't shoot that.";
        }
        return response;
    }

    /**
     * The box and arrow are in the inventory. Execute shooting the arrow and remove it from the inventory and add it
     * to the location or only remove it from inventory if shot in the boat.
     * @return Response to shooting the arrow
     */
    String shootArrow() {
        final Game game = GameState.getInstance().getGame();
        final Item arrow = game.getInventoryItemByName(ARROW_NAME);
        if (game.getCurrentLocation().getName().equals(LocationNames.BOAT)) {
            game.removeItemFromInventory(arrow);
            return "Your arrow goes flying off into the the distance and splashes into the water, never to be found again.";
        }
        game.removeItemFromInventory(arrow);
        addItemToLocation(arrow);
        return "Your arrow goes flying off into the the distance and lands with a thud on the ground.";
    }

    @Override
    public String toString() {
        return this.name;
    }
}
