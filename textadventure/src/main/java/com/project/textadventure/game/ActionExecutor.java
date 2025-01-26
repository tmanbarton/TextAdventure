package com.project.textadventure.game;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.constants.ResponseConstants;
import com.project.textadventure.controllers.GameStatus;
import com.project.textadventure.game.Graph.Dam;
import com.project.textadventure.game.Graph.Item;
import com.project.textadventure.game.Graph.Location;
import com.project.textadventure.game.Graph.LocationConnection;
import com.project.textadventure.game.Graph.MineEntrance;

import static com.project.textadventure.constants.ItemConstants.ARROW_NAME;
import static com.project.textadventure.constants.ItemConstants.JAR_NAME;
import static com.project.textadventure.game.ActionExecutorUtils.addItemToInventory;
import static com.project.textadventure.game.ActionExecutorUtils.getInventoryItemByName;
import static com.project.textadventure.game.ActionExecutorUtils.isItemInInventory;
import static com.project.textadventure.game.ActionExecutorUtils.removeItemFromLocation;

public class ActionExecutor {

    /**
     * Move to the {@link Location} connected by the given connection.
     * @param locationConnection Connection to the {@link Location} to move to
     * @return Description of the new {@link Location} + {@link Item}s at the location
     */
    public static String moveToLocation(final LocationConnection locationConnection) {
        final Location toLocation = locationConnection.getLocation();

        GameState.getInstance().getGame().setCurrentLocation(toLocation);

        if (toLocation.isVisited()) {
            return toLocation.getShortDescription() + ActionExecutorUtils.listLocationItems(toLocation.getItems());
        }
        toLocation.setVisited(true);
        return toLocation.getDescription() + ActionExecutorUtils.listLocationItems(toLocation.getItems());
    }

    /**
     * Add item to inventory and/or do any special handling for specific items at specific locations
     * @param itemName item to get
     * @return response to user
     */
    public static String executeGetCommand(final String itemName) {
        if (itemName == null) {
            return ResponseConstants.WHAT_DO_YOU_WANT_TO_GET;
        }
        final Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();
        final Item item = ActionExecutorUtils.getLocationItemByName(itemName);
        final boolean isJarInInventory = isItemInInventory(ItemConstants.JAR_NAME);

        // Make sure the item is at the location
        if (item == null) {
            if (itemName.equals(ItemConstants.NAILS_NAME) && currentLocation instanceof MineEntrance && !((MineEntrance) currentLocation).areNailsOff()) {
                // If the user tries to get nails when at the mine entrance and the nails have not been taken by hand instead of knocked off with the
                // arrow (causing the user to die), have a special message saying the nails are buried instead of the generic "I don't see that here."
                if (((MineEntrance) currentLocation).areNailsOff()) {
                    return "The nails are buried under rubble from the collapsed mine; you can't get to them.";
                } else {
                    // If the mine hasn't collapsed then they haven't taken the nails yet, prompt/warn to make sure they actually want to take them
                    // Set the state to GETTING_NAILS to indicate that a yes/no answer is required to continue
                    GameState.getInstance().getGame().setGameStatus(GameStatus.GETTING_NAILS);
                    return "Are you sure you want to get the nails? The structure is very fragile and may fall apart and onto you.";
                }
            } else if (itemName.equals(ItemConstants.MAGNET_NAME) && currentLocation instanceof Dam && ((Dam) currentLocation).isMagnetDropped()) {
                // Special case from when the user tries to get the magnet from the dam after it's been dropped
                return "The magnet is firmly attached to the wheel";
            }
        } else if (ActionExecutorUtils.getInventoryItemByName(itemName) != null) {
            return ResponseConstants.ALREADY_CARRYING;

        } else if (itemName.equals(ItemConstants.GOLD_NAME) && !isJarInInventory) {
            // Special case for when user tries to get gold flakes without the jar
            return "You need something to hold the gold flakes.";

        } else if (itemName.equals(ItemConstants.GOLD_NAME) && isJarInInventory) {
            // Special case for when user tries to get gold flakes and does have the jar
            // Remove the gold flakes from the location and add them to inventory and update the jar's description
            final String result = addItemToInventory(item);
            if (result != null) {
                return result;
            }
            getInventoryItemByName(JAR_NAME).setInventoryDescription("Jar full of gold flakes");
            removeItemFromLocation(item);
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
            removeItemFromLocation(item);
            return ResponseConstants.OK;
        }
        return "I don't see that here.";
    }

    /**
     * Display the current {@link Location}'s description and list the {@link Item}s at the {@link Location}.
     * @return Description of the current location + items at the {@link Location}
     */
    public static String executeLookCommand() {
        final Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();
        return currentLocation.getDescription() + ActionExecutorUtils.listLocationItems(currentLocation.getItems());
    }

    /**
     * The box and arrow are in the inventory. Execute shooting the arrow and remove it from the inventory and add it
     * to the location or only remove it from inventory if shot in the boat.
     * @return Response to shooting the arrow
     */
    public static String shootArrow() {
        final Game game = GameState.getInstance().getGame();
        final Item arrow = getInventoryItemByName(ARROW_NAME);
        if (game.getCurrentLocation().getName().equals(LocationNames.BOAT)) {
            game.removeItemFromInventory(arrow);
            return "Your arrow goes flying off into the the distance and splashes into the water, never to be found again.";
        }
        game.removeItemFromInventory(arrow);
        ActionExecutorUtils.addItemToLocation(arrow);
        return "Your arrow goes flying off into the the distance and lands with a thud on the ground.";
    }
}
