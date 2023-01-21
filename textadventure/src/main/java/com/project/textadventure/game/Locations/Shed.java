package com.project.textadventure.game.Locations;

import com.project.textadventure.controller.Action;
import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import com.project.textadventure.game.Item;
import com.project.textadventure.game.actions.ShedActions;

import java.util.ArrayList;
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
        Item hammer = new Item(2, "There is a hammer here", "Hammer", "hammer");
        Item bow = new Item(3, "There is a bow here, strung and ready for shooting", "Bow", "bow");
        Item arrow = new Item(4, "There is an arrow here", "Arrow", "arrow");
        Item jar = new Item(5, "There is a jar here", "Jar", "jar");
        Item shovel = new Item(8, "There is a rusty shovel here.", "Rusty shovel", "shovel");
        Item tent = new Item(9, "There is a tent here, packed neatly in a bag.", "Tent in bag", "tent");
        this.addItemToLocation(hammer);
        this.addItemToLocation(bow);
        this.addItemToLocation(arrow);
        this.addItemToLocation(jar);
        this.addItemToLocation(shovel);
        this.addItemToLocation(tent);
    }

}
