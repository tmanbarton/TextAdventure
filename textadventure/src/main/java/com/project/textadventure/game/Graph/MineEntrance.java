package com.project.textadventure.game.Graph;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.controllers.GameStatus;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import com.project.textadventure.game.ActionExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

import static com.project.textadventure.constants.GameConstants.SHOOT;
import static com.project.textadventure.constants.ItemConstants.ARROW_NAME;
import static com.project.textadventure.constants.ItemConstants.NAILS_NAME;
import static com.project.textadventure.constants.LocationDescriptions.MINE_ENTRANCE_RECENT_CAVE_IN;
import static com.project.textadventure.constants.LocationNames.MINE_SHAFT;
import static com.project.textadventure.game.ActionExecutorUtils.addItemToLocation;
import static com.project.textadventure.game.ActionExecutorUtils.getInventoryItemByName;
import static com.project.textadventure.game.ActionExecutorUtils.isItemInInventory;
import static com.project.textadventure.game.ActionExecutorUtils.removeItemFromInventory;
import static com.project.textadventure.game.Game.isGetCommand;

public class MineEntrance extends Location implements Action {
    /**
     * Whether the nails are off or not. If they are off then the mine entrance has been collapsed.
     */
    private boolean nailsOff;
    /**
     * Whether the mine entrance has collapsed or not. The mine can be collapsed and the nails can be on or off.
     * If the user tries to take the nails by hand, the mine will collapse but the nails won't be off
     * If the user shoots the nails with the arrow, the mine will collapse and the nails will be off
     */
    private boolean nailsTakenByHand;

    public MineEntrance(final String description, final String shortDescription, final List<Item> items, final List<LocationConnection> locationConnections, final boolean visited, final String name, final boolean nailsOff) {
        super(description, shortDescription, items, locationConnections, visited, name);
        this.nailsOff = nailsOff;
    }

    public void setNailsTakenByHand(boolean nailsTakenByHand) {
        this.nailsTakenByHand = nailsTakenByHand;
    }

    public boolean areNailsOff() {
        return nailsOff;
    }

    /**
     * Take any actions specific to the mine entrance location, or, if no special commands are given, call the super class method to take a generic action.
     * @param command The verb part of the command
     * @param noun The noun part of the command
     * @return The response to the action to be displayed to the user
     */
    @Override
    public String takeAction(@NonNull final String command, @Nullable final String noun) {
        if (StringUtils.equals(command, SHOOT)) {
            return executeShootCommand(noun);
            // todo executeShootCommand is wrong. Needs to be a new method specific to MineEntrance
        } else if (isGetCommand(command, noun)) {
            return handleGetCommand(command, noun);
        } else {
            return super.takeAction(command, noun);
        }
    }

    private String handleGetCommand(final String verb, final String itemName) {
        if (StringUtils.equals(itemName, NAILS_NAME)) {
            if (nailsTakenByHand) {
                // The user has previously taken the nails by hand instead of shot them with the arrow as intended, and they are now inaccessible
                return "The nails are buried under rubble from the collapsed mine; you can't get to them.";
            } else if (!nailsOff) {
                // If it hasn't collapsed, they haven't taken the nails at all, prompt/warn to make sure they actually want to take them
                GameState.getInstance().getGame().setGameStatus(GameStatus.GETTING_NAILS);
                return "Are you sure you want to get the nails? The structure is very fragile and may fall apart and onto you.";
            }
        }
        return super.takeAction(verb, itemName);
    }

    String executeShootCommand(final String thingToShoot) {
        // Special case for shooting at mine entrance happens when the user has the arrow and bow and
        // the mine hasn't collapsed (caused by taking or shooting the nails)
        if (isItemInInventory(ARROW_NAME) && isItemInInventory(ItemConstants.BOW_NAME) && !nailsTakenByHand) {
            return shootArrow();
        }
        return ActionExecutor.executeShootCommand(thingToShoot);
    }

    /**
     * Shoot the arrow at the nails, adding the nails to the location and causing the mine entrance to collapse.
     * Also add arrow to location as usual when it's shot.
     * @return The response to the action to be displayed to the user
     */
    String shootArrow() {
        if (nailsOff) {
            return ActionExecutor.shootArrow();
        }

        final Game game = GameState.getInstance().getGame();
        final Item arrow = getInventoryItemByName(ARROW_NAME);
        final Location currentLocation = game.getCurrentLocation();

        removeItemFromInventory(arrow);
        addItemToLocation(arrow);
        final Item nails = new Item(7, ItemConstants.NAILS_LOCATION_DESCRIPTION, ItemConstants.NAILS_INVENTORY_DESCRIPTION, ItemConstants.NAILS_NAME, 0, 1);
        addItemToLocation(nails);
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