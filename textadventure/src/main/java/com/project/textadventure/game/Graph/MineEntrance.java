package com.project.textadventure.game.Graph;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;

import java.util.List;

import static com.project.textadventure.constants.LocationDescriptions.MINE_ENTRANCE_RECENT_CAVE_IN;
import static com.project.textadventure.constants.LocationNames.MINE_ENTRANCE;
import static com.project.textadventure.constants.LocationNames.MINE_SHAFT;
import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

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
    public void setNailsOff(final boolean nailsOff) {
        this.nailsOff = nailsOff;
    }

    @Override
    public String takeAction(final String verb, final String noun) {
        if (verb.equals("shoot") && !this.nailsOff && !this.isCollapsed) {
            return parseShootCommand(noun);
        }
        else {
            // return early instead of use response so there's not an extra <br> at the end
            return super.takeAction(verb, noun);
        }
    }

    private String parseShootCommand(final String noun) {
        final Game game = GameState.getInstance().getGame();
        String response = "";

        // only "shoot" or "shoot arrow" does something at mine entrance
        if (noun == null || noun.equals("arrow")) {
            final Item bow = game.getInventoryItemByName("bow");
            final Item arrow = game.getInventoryItemByName("arrow");
            // Must have the bow and arrow in your inventory
            if (bow == null) {
                response = "You don't have anything to shoot with.";
            }
            else if (arrow == null) {
                response = "You don't have anything to shoot.";
            }
            else {
                response = shootArrow(arrow);
            }
        }
        else {
            response = generateRandomUnknownCommandResponse();
        }
        return response;
    }

    private String shootArrow(final Item arrow) {
        final Game game = GameState.getInstance().getGame();
        final Location currentLocation = game.getCurrentLocation();
        String response = "";

        game.removeItemFromInventory(arrow);
        addItemToLocation(arrow);
        final Item nails = new Item(7, ItemConstants.NAILS_LOCATION_DESCRIPTION, ItemConstants.NAILS_INVENTORY_DESCRIPTION, ItemConstants.NAILS_NAME);
        currentLocation.addItemToLocation(nails);
        nailsOff = true;

        // 2 ways to get to mine shaft. Find and remove both
        final List<LocationConnection> locationConnections = currentLocation.getLocationConnections();
        locationConnections.removeIf(location -> location.getLocation().getName().equals(MINE_SHAFT));

        for (final LocationConnection locationConnection : currentLocation.getLocationConnections()) {
            if (locationConnection.getLocation().getName().equals(MINE_SHAFT)) {
                locationConnection.getLocation().getLocationConnections().removeIf(
                        mineShaftLocation -> mineShaftLocation.getLocation().getName().equals(MINE_ENTRANCE)
                );
            }
        }

        currentLocation.setDescription(MINE_ENTRANCE_RECENT_CAVE_IN);
        response = "You shoot the arrow and it glances off the nails with a small ringing sound. The nails and your " +
                "arrow land a few feet away then there's a loud crack of the support and the entrance caves in with an " +
                "even louder crash and cloud of dust. Good thing you didn't try to take them by hand.";

        return response;
    }
}