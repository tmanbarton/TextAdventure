package com.project.textadventure.game.Graph;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.controllers.GameStatus;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

import static com.project.textadventure.constants.ItemConstants.ARROW_NAME;
import static com.project.textadventure.constants.ItemConstants.NAILS_NAME;
import static com.project.textadventure.constants.LocationDescriptions.MINE_ENTRANCE_RECENT_CAVE_IN;
import static com.project.textadventure.constants.LocationNames.MINE_SHAFT;
import static com.project.textadventure.game.Game.isGetCommand;

public class MineEntrance extends Location implements Action {
    private boolean nailsOff;
    private boolean isCollapsed;

    public MineEntrance(final String description, final String shortDescription, final List<Item> items, final List<LocationConnection> locationConnections, final boolean visited, final String name, final boolean nailsOff) {
        super(description, shortDescription, items, locationConnections, visited, name);
        this.nailsOff = nailsOff;
    }

    public void setCollapsed(boolean collapsed) {
        isCollapsed = collapsed;
    }

    public boolean areNailsOff() {
        return nailsOff;
    }

    /**
     * Take any actions specific to the mine entrance location, or, if no special commands are given, call the super class method to take a generic action.
     * @param verb The verb part of the command
     * @param noun The noun part of the command
     * @return The response to the action to be displayed to the user
     */
    @Override
    public String takeAction(@NonNull final String verb, @Nullable final String noun) {
        if (verb.equals("shoot") && !nailsOff && !isCollapsed) {
            return parseShootCommand(noun);
        } else if (isGetCommand(verb, noun)) {
            return handleGetCommand(verb, noun);
        } else {
            // return early instead of use response so there's not an extra <br> at the end
            return super.takeAction(verb, noun);
        }
    }

    private String handleGetCommand(final String verb, final String itemName) {
        if (StringUtils.equals(itemName, NAILS_NAME)) {
            if (nailsOff) {
                // The user has previously taken the nails by hand instead of shot them with the arrow as intended, and they are now inaccessible
                return "The nails are buried under rubble from the collapsed mine; you can't get to them.";
            } else {
                // If it hasn't collapsed, they haven't taken the nails at all, prompt/warn to make sure they actually want to take them
                GameState.getInstance().getGame().setGameStatus(GameStatus.GETTING_NAILS);
                return "Are you sure you want to get the nails? The structure is very fragile and may fall apart and onto you.";
            }
        } else {
            return super.takeAction(verb, itemName);
        }
    }

    /**
     * Shoot the arrow at the nails, adding the nails to the location and causing the mine entrance to collapse.
     * Also add arrow to location as usual when it's shot.
     * @return The response to the action to be displayed to the user
     */
    @Override
    String shootArrow() {
        if (nailsOff) {
            return super.shootArrow();
        }

        final Game game = GameState.getInstance().getGame();
        final Item arrow = game.getInventoryItemByName(ARROW_NAME);
        final Location currentLocation = game.getCurrentLocation();

        game.removeItemFromInventory(arrow);
        addItemToLocation(arrow);
        final Item nails = new Item(7, ItemConstants.NAILS_LOCATION_DESCRIPTION, ItemConstants.NAILS_INVENTORY_DESCRIPTION, ItemConstants.NAILS_NAME, 0, 1);
        currentLocation.addItemToLocation(nails);
        nailsOff = true;

        // 2 ways to get to mine shaft (in and east). Find and set connecting locations to empty. Do this instead of removing the
        // connections, otherwise BFS won't work for locations in the mine since they will be cut off.
        final List<LocationConnection> locationConnections = currentLocation.getLocationConnections();
        locationConnections.forEach(location -> {
            if (location.getLocation().getName().equals(MINE_SHAFT)) {
                location.setDirections(List.of());
            }
        });

        // The user solved the puzzle. Get 20 points
        GameState.getInstance().incrementScore(20);
        currentLocation.setDescription(MINE_ENTRANCE_RECENT_CAVE_IN);
        return "You shoot the arrow and it glances off the nails with a small ringing sound. The nails and your " +
                "arrow land a few feet away then there's a loud crack of the support and the entrance caves in with an " +
                "even louder crash and cloud of dust. Good thing you didn't try to take them by hand.";
    }
}