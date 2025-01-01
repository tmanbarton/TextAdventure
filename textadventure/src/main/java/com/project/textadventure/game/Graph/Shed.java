package com.project.textadventure.game.Graph;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

public class Shed extends Location implements Action {
    boolean unlocked;
    boolean open;
    public Shed(final String description,
                final String shortDescription,
                final List<Item> items,
                final List<LocationConnection> locationConnections,
                final boolean visited,
                final String name,
                final boolean unlocked,
                final boolean open) {
        super(description,
                shortDescription,
                items,
                locationConnections,
                visited,
                name);
        this.unlocked = unlocked;
        this.open = open;
    }

    /**
     * Take any actions specific to the shed location, or, if no special commands are given, call the super class method to take a generic action.
     * @param verb The verb of the command
     * @param noun The noun of the command
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
     * @param verb verb of the command, "unlock" or "open" in this case
     * @param noun noun of the command, only "" or "shed" will do anything
     * @return String message from successfully unlocking/opening shed or
     * a "don't know command" type message
     */
    private String parseUnlockAndOpenCommand(final String verb, final String noun) {
        final Game game = GameState.getInstance().getGame();
        final Item key = game.getInventoryItemByName("key");
        String response;
        // Check if the verb is "unlock"
        if (verb.equals("unlock")) {
            if (this.unlocked) {
                response = "The shed is already unlocked.";
            }
            else if (key == null) {
                response = "You need a key to unlock the shed.";
            }
            else {
                unlockShed();
                response = "The shed is now unlocked.";
            }
        }
        // Check if the verb is "open"
        else if (verb.equals("open")) {
            if (this.open) {
                response = "The shed is already open.";
            }
            else if (!this.unlocked && key == null) {
                response = "You must unlock the shed before opening it.";
            }
            else {
                openShed();
                response = !this.unlocked ? "First unlocking the shed, the shed now stands open." : "The shed now stands open.";
                response += listLocationItems(game.getCurrentLocation().getItems());
            }
        }
        else {
            response = generateRandomUnknownCommandResponse();
        }
        return response;
    }

    /**
     * Unlock the shed by setting the unlocked flag to true and changing the description of the shed.
     */
    private void unlockShed() {
        this.unlocked = true;
        this.setDescription("A cheerful little shed stands with it's lock hanging open with a picnic table to the north.");
        this.setShortDescription("You're standing before a cheerful little shed with its lock hanging open.");
    }

    /**
     * Open the shed by setting the open flag to true, changing the description of the shed, and adding items that are in the shed to the location.
     * Also, increment the score by 10 points for solving the puzzle of unlocking and opening the shed.
     */
    public void openShed() {
        this.setDescription("You stand before an open shed with a picnic table to the north.");
        this.setShortDescription("You're standing before a cheerful little, open shed.");
        this.open = true;
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
