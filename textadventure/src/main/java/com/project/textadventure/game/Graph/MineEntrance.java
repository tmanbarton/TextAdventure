package com.project.textadventure.game.Graph;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

import static com.project.textadventure.constants.ItemConstants.ARROW_NAME;
import static com.project.textadventure.constants.LocationDescriptions.MINE_ENTRANCE_RECENT_CAVE_IN;
import static com.project.textadventure.constants.LocationNames.MINE_SHAFT;

public class MineEntrance extends Location implements Action {
    private boolean nailsOff;
    private boolean isCollapsed;

    public MineEntrance(final String description, final String shortDescription, final List<Item> items, final List<LocationConnection> locationConnections, final boolean visited, final String name, final boolean nailsOff) {
        super(description, shortDescription, items, locationConnections, visited, name);
        this.nailsOff = nailsOff;
    }

    public boolean isCollapsed() {
        return isCollapsed;
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
        if (verb.equals("shoot") && !this.nailsOff && !this.isCollapsed) {
            return parseShootCommand(noun);
        } else {
            // return early instead of use response so there's not an extra <br> at the end
            return super.takeAction(verb, noun);
        }
    }

    /**
     * Decide if the input results in shooting the arrow at the nails. If so, call
     * respective method to shoot the arrow.
     * @param thingToShoot noun part of the command, only "" or "arrow" will do anything
     * @return String message from successfully shooting the arrow or
     * a "don't know command" type message
     */
//    @Override
//    String parseShootCommand(final String thingToShoot) {
//        // Only "shoot" or "shoot arrow" does something at mine entrance, and only if nails are still on meaning the user hasn't shot the arrow yet
//        if (!StringUtils.isEmpty(thingToShoot) && !StringUtils.equals(thingToShoot, "arrow")) {
//            // Don't have the bow or arrow, so this can be treated as a regular action
//            return super.parseShootCommand(thingToShoot);
//        } else {
//            // Have the bow and arrow, so shoot the arrow and do special stuff for MineEntrance Location
//            return nailsOff ? super.shootArrow() : shootArrow();
//        }
//    }

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
        final Item nails = new Item(7, ItemConstants.NAILS_LOCATION_DESCRIPTION, ItemConstants.NAILS_INVENTORY_DESCRIPTION, ItemConstants.NAILS_NAME, 0);
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