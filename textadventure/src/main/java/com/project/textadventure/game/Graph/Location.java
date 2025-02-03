package com.project.textadventure.game.Graph;

import com.project.textadventure.constants.GameConstants;
import com.project.textadventure.game.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.project.textadventure.constants.GameConstants.BAD_DIRECTION;
import static com.project.textadventure.constants.GameConstants.DROP;
import static com.project.textadventure.constants.GameConstants.EAT;
import static com.project.textadventure.constants.GameConstants.FILL;
import static com.project.textadventure.constants.GameConstants.GET;
import static com.project.textadventure.constants.GameConstants.HELP;
import static com.project.textadventure.constants.GameConstants.INFO;
import static com.project.textadventure.constants.GameConstants.INVENTORY_LONG;
import static com.project.textadventure.constants.GameConstants.LOOK_LONG;
import static com.project.textadventure.constants.GameConstants.MOVE;
import static com.project.textadventure.constants.GameConstants.OPEN;
import static com.project.textadventure.constants.GameConstants.PUSH;
import static com.project.textadventure.constants.GameConstants.RESTART;
import static com.project.textadventure.constants.GameConstants.SCORE;
import static com.project.textadventure.constants.GameConstants.SHOOT;
import static com.project.textadventure.constants.GameConstants.TURN;
import static com.project.textadventure.constants.GameConstants.UNLOCK;
import static com.project.textadventure.constants.ItemConstants.ARROW_NAME;
import static com.project.textadventure.constants.ItemConstants.BOW_NAME;
import static com.project.textadventure.constants.ItemConstants.PIE_NAME;
import static com.project.textadventure.game.ActionExecutor.executeDropCommand;
import static com.project.textadventure.game.ActionExecutor.executeEatCommand;
import static com.project.textadventure.game.ActionExecutor.executeFillCommand;
import static com.project.textadventure.game.ActionExecutor.executeGetCommand;
import static com.project.textadventure.game.ActionExecutor.executeHelpCommand;
import static com.project.textadventure.game.ActionExecutor.executeInfoCommand;
import static com.project.textadventure.game.ActionExecutor.executeInventoryCommand;
import static com.project.textadventure.game.ActionExecutor.executeLookCommand;
import static com.project.textadventure.game.ActionExecutor.executePushCommand;
import static com.project.textadventure.game.ActionExecutor.executeQuitCommand;
import static com.project.textadventure.game.ActionExecutor.executeScoreCommand;
import static com.project.textadventure.game.ActionExecutor.executeShootCommand;
import static com.project.textadventure.game.ActionExecutorUtils.getInventoryItemByName;
import static com.project.textadventure.game.ActionExecutorUtils.isItemInInventory;
import static com.project.textadventure.game.ActionExecutorUtils.removeItemFromInventory;
import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

/**
 * Represents a location in the game. Essentially a node in the graph.
 */
@Data
public class Location {
    private String description;
    private List<Item> items;
    private List<LocationConnection> locationConnections;
    private boolean visited;
    public boolean bfsIsVisited;
    private String name;
    private String shortDescription;

    public Location(final String fullDescription, final String shortDescription, final List<Item> items, final List<LocationConnection> locationConnections,
            final boolean visited, final String name) {
        this.description = fullDescription;
        this.shortDescription = shortDescription;
        this.items = items;
        this.locationConnections = locationConnections;
        this.visited = visited;
        this.name = name;

        // Only used for BFS algorithm when finding a location
        this.bfsIsVisited = false;
    }

    /**
     * Only used for testing.
     */
    public Location() {
        this("Full description", "Short Description", new ArrayList<>(), new ArrayList<>(), false, "name");
    }

    /**
     * Connect this {@link Location} to another {@link Location}. Essentially an edge in the graph.
     * @param locationToConnect Location to connect to
     */
    public void connectLocation(final LocationConnection locationToConnect) {
        this.locationConnections.add(locationToConnect);
    }

    /**
     * Determine what action to take based on the verb and noun and execute the action for locations that don't do
     * anything special/actions that are the same for all locations
     * @param command Verb part of the command
     * @param noun Noun part of the command, could be empty
     * @return Response to the command to display to the user
     */
    public String takeAction(@NonNull String command, @NonNull final String noun) {
        if (StringUtils.equals(command, MOVE)) {
            return parseMoveCommand(noun);
        } else if (StringUtils.equals(command, GET)) {
            return executeGetCommand(noun);
        } else if (StringUtils.equals(command, DROP)) {
            return executeDropCommand(noun);
        } else if (StringUtils.equals(command, INVENTORY_LONG)) {
            return executeInventoryCommand();
        } else if (StringUtils.equals(command, RESTART)) {
            return executeQuitCommand();
        } else if (StringUtils.equals(command, LOOK_LONG)) {
            return executeLookCommand();
        } else if (StringUtils.equals(command, HELP)) {
            return executeHelpCommand();
        } else if (StringUtils.equals(command, INFO)) {
            return executeInfoCommand();
        } else if (StringUtils.equals(command, SCORE)) {
            return executeScoreCommand();
        } else if (StringUtils.equals(command, SHOOT)) {
            return executeShootCommand(noun);
        } else if (StringUtils.equals(command, EAT)) {
            return executeEatCommand(noun);
        } else if (StringUtils.equals(command, PUSH)) {
            return executePushCommand(command, noun);
        } else if (StringUtils.equals(command, FILL)) {
            return executeFillCommand(noun);
        } else if (StringUtils.equals(command, UNLOCK)) {
            return "There's nothing to unlock here.";
        } else if (StringUtils.equals(command, OPEN)) {
            return "There's nothing to open here.";
        } else if (StringUtils.equals(command, TURN)) {
            return "There's nothing to turn here.";
        } else {
            return generateRandomUnknownCommandResponse();
        }
    }

    /**
     * Parses the input when it's a move command. If the direction is valid, move to the location in that direction.
     * @param direction Direction to try to move in
     * @return Description of the new location or a message saying user can't go that way
     */
    String parseMoveCommand(final String direction) {
        final Game game = GameState.getInstance().getGame();
        final Location currentLocation = game.getCurrentLocation();
        // Find the location connected by the given direction
        final Optional<LocationConnection> connectingLocation = currentLocation.getLocationConnections()
                .stream()
                .filter(location -> location.getDirections().contains(direction))
                .findFirst();

        if (connectingLocation.isEmpty()) {
            return BAD_DIRECTION;
        }
        final LocationConnection locationConnection = connectingLocation.get();
        return ActionExecutor.moveToLocation(locationConnection);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
