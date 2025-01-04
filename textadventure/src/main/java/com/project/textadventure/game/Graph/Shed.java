package com.project.textadventure.game.Graph;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.List;

import static com.project.textadventure.constants.LocationDescriptions.SHED_OPEN_LONG_DESCRIPTION;
import static com.project.textadventure.constants.LocationDescriptions.SHED_OPEN_SHORT_DESCRIPTION;
import static com.project.textadventure.constants.LocationDescriptions.SHED_UNLOCKED_LONG_DESCRIPTION;
import static com.project.textadventure.constants.LocationDescriptions.SHED_UNLOCKED_SHORT_DESCRIPTION;
import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

public class Shed extends Location implements Action {
    boolean isUnlocked;
    boolean isOpen;
    public Shed(final String description,
                final String shortDescription,
                final List<Item> items,
                final List<LocationConnection> locationConnections,
                final boolean visited,
                final String name,
                final boolean isUnlocked,
                final boolean isOpen) {
        super(description,
                shortDescription,
                items,
                locationConnections,
                visited,
                name);
        this.isUnlocked = isUnlocked;
        this.isOpen = isOpen;
    }

    /**
     * Take any actions specific to the shed location, or, if no special commands are given, call the super class method to take a generic action.
     * @param verb The verb part of the command
     * @param noun The noun part of the command
     * @return The response to the action to be displayed to the user
     */
    @Override
    public String takeAction(@NonNull String verb, @Nullable final String noun) {
        String response = "";
        if (verb.equals("unlock") || verb.equals("open")) {
            response = parseUnlockAndOpenCommand(verb, noun);
        }
        else {
            response = super.takeAction(verb, noun);
        }
        return response;
    }

    /**
     * Decide if the input results in unlocking/opening the shed. If so, call
     * respective method to unlock/open the shed.
     * @param verb verb part of the command, "unlock" or "open" in this case
     * @param noun noun part of the command, only "" or "shed" will do anything
     * @return String message from successfully unlocking/opening shed or
     * a "don't know command" type message
     */
    private String parseUnlockAndOpenCommand(final String verb, final String noun) {
        final Game game = GameState.getInstance().getGame();
        final Item key = game.getInventoryItemByName("key");
        // Check if the verb is "unlock"
        if (StringUtils.equals(noun, "shed") || noun == null ) {
            if (verb.equals("unlock")) {
                if (this.isOpen) {
                    return "The shed is already unlocked and open.";
                } else if (this.isUnlocked) {
                    return "The shed is already unlocked.";
                } else if (key == null) {
                    return "You need a key to unlock the shed.";
                } else {
                    unlockShed();
                    return "The shed is now unlocked.";
                }
            } else if (verb.equals("open")) {
                // Check if the verb is "open"
                if (this.isOpen) {
                    return "The shed is already open.";
                } else if (!this.isUnlocked && key == null) {
                    return "You must unlock the shed before opening it.";
                } else {
                    openShed();
                    String response;
                    response = !this.isUnlocked ? "First unlocking the shed, the shed now stands open." : "The shed now stands open.";
                    // In case the user did the open command before the unlock command, set the shed as unlocked even if it was already unlocked
                    this.isUnlocked = true;
                    response += listLocationItems(game.getCurrentLocation().getItems());
                    return response;
                }
            } else {
                return generateRandomUnknownCommandResponse();
            }
        } else {
            return generateRandomUnknownCommandResponse();
        }
    }

    /**
     * Unlock the shed by setting the unlocked flag to true and changing the description of the shed.
     */
    private void unlockShed() {
        this.isUnlocked = true;
        this.setDescription(SHED_UNLOCKED_LONG_DESCRIPTION);
        this.setShortDescription(SHED_UNLOCKED_SHORT_DESCRIPTION);
    }

    /**
     * Open the shed by setting the open flag to true, changing the description of the shed, and adding items that are in the shed to the location.
     * Also, increment the score by 10 points for solving the puzzle of unlocking and opening the shed.
     */
    public void openShed() {
        this.setDescription(SHED_OPEN_LONG_DESCRIPTION);
        this.setShortDescription(SHED_OPEN_SHORT_DESCRIPTION);
        this.isOpen = true;
        final Item hammer = new Item(2, ItemConstants.HAMMER_LOCATION_DESCRIPTION, ItemConstants.HAMMER_INVENTORY_DESCRIPTION, ItemConstants.HAMMER_NAME, 0);
        final Item bow = new Item(3, ItemConstants.BOW_LOCATION_DESCRIPTION, ItemConstants.BOW_INVENTORY_DESCRIPTION, ItemConstants.BOW_NAME, 0);
        final Item arrow = new Item(4, ItemConstants.ARROW_LOCATION_DESCRIPTION, ItemConstants.ARROW_INVENTORY_DESCRIPTION, ItemConstants.ARROW_NAME, 0);
        final Item jar = new Item(5, ItemConstants.JAR_LOCATION_DESCRIPTION, ItemConstants.JAR_INVENTORY_DESCRIPTION, ItemConstants.JAR_NAME, 0);
        final Item shovel = new Item(8, ItemConstants.SHOVEL_LOCATION_DESCRIPTION, ItemConstants.SHOVEL_INVENTORY_DESCRIPTION, ItemConstants.SHOVEL_NAME, 0);
        final Item tent = new Item(9, ItemConstants.TENT_LOCATION_DESCRIPTION, ItemConstants.TENT_INVENTORY_DESCRIPTION, ItemConstants.TENT_NAME, 0);
        this.addItemToLocation(hammer);
        this.addItemToLocation(bow);
        this.addItemToLocation(arrow);
        this.addItemToLocation(jar);
        this.addItemToLocation(shovel);
        this.addItemToLocation(tent);
        // Solved this puzzle of unlocking and opening the shed. Add 10 points to the score.
        GameState.getInstance().incrementScore(10);
    }

}
