package com.project.textadventure.game.Locations;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import com.project.textadventure.game.Item;
import com.project.textadventure.game.actions.ShedActions;

import java.util.List;

import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

public class Shed extends Location implements Action {
    boolean unlocked;
    boolean open;
    ShedActions possibleActions; // TODO use in ActionFactory
    public Shed(String description,
                String shortDescription,
                List<Item> items,
                List<ConnectingLocation> connectingLocations,
                boolean visited,
                String name,
                boolean unlocked,
                boolean open) {
        super(description,
                shortDescription,
                items,
                connectingLocations,
                visited,
                name);
        this.unlocked = unlocked;
        this.open = open;
    }

    @Override
    public String takeAction(String verb, String noun) {
        String response = "";
        if(verb.equals("unlock") || verb.equals("open")) {
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
    private String parseUnlockAndOpenCommand(String verb, String noun) {
        Game game = GameState.getInstance().getGame();
        Location currentLocation = game.getCurrentLocation();
        Item key = game.getInventoryItemByName("key");
        String response;
        // Check if the verb is "unlock"
        if (verb.equals("unlock")) {
            if (this.unlocked) {
                response = "The shed is already unlocked.";
            }
            else if(key == null) {
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

    private void unlockShed() {
        this.unlocked = true;
        this.setDescription("A cheerful little shed stands with it's lock hanging open with a picnic table to the north.");
        this.setShortDescription("You're standing before a cheerful little shed with its lock hanging open.");
    }

    public void openShed() {
        this.setDescription("You stand before an open shed with a picnic table to the north.");
        this.setShortDescription("You're standing before a cheerful little, open shed.");
        this.open = true;
//        List<Item> ditchItems = this.game != null && this.game.getInventoryItemByName("key") != null ? new ArrayList<>() : new ArrayList<>(List.of(key)); TODO: this but for these
        Item hammer = new Item(2, ItemConstants.HAMMER_LOCATION_DESCRIPTION, ItemConstants.HAMMER_INVENTORY_DESCRIPTION, ItemConstants.HAMMER_NAME);
        Item bow = new Item(3, ItemConstants.BOW_LOCATION_DESCRIPTION, ItemConstants.BOW_INVENTORY_DESCRIPTION, ItemConstants.BOW_NAME);
        Item arrow = new Item(4, ItemConstants.ARROW_LOCATION_DESCRIPTION, ItemConstants.ARROW_INVENTORY_DESCRIPTION, ItemConstants.ARROW_NAME);
        Item jar = new Item(5, ItemConstants.JAR_LOCATION_DESCRIPTION, ItemConstants.JAR_INVENTORY_DESCRIPTION, ItemConstants.JAR_NAME);
        Item shovel = new Item(8, ItemConstants.SHOVEL_LOCATION_DESCRIPTION, ItemConstants.SHOVEL_INVENTORY_DESCRIPTION, ItemConstants.SHOVEL_NAME);
        Item tent = new Item(9, ItemConstants.TENT_LOCATION_DESCRIPTION, ItemConstants.TENT_INVENTORY_DESCRIPTION, ItemConstants.TENT_NAME);
        this.addItemToLocation(hammer);
        this.addItemToLocation(bow);
        this.addItemToLocation(arrow);
        this.addItemToLocation(jar);
        this.addItemToLocation(shovel);
        this.addItemToLocation(tent);
    }

}
