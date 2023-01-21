package com.project.textadventure.game.Locations;

import com.project.textadventure.controller.Action;
import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import com.project.textadventure.game.Item;
import com.project.textadventure.game.actions.MineEntranceActions;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

public class MineEntrance extends Location implements Action {
    private boolean nailsOff;
    private boolean takingNails;
    MineEntranceActions possibleActions; // TODO use in ActionFactory

    public MineEntrance(String description, String shortDescription, List<Item> items, List<ConnectingLocation> connectingLocations, boolean visited, String name, boolean nailsOff) {
        super(description, shortDescription, items, connectingLocations, visited, name);
        this.nailsOff = nailsOff;
        this.takingNails = false;
    }

    public boolean isTakingNails() {
        return takingNails;
    }

    public void setTakingNails(boolean takingNails) {
        this.takingNails = takingNails;
    }

    public boolean areNailsOff() {
        return nailsOff;
    }

    @Override
    public String takeAction(String verb, String noun) {
        if(verb.equals("shoot") && !this.nailsOff) {
            return parseShootCommand(noun);
        }
        else {
            // return early instead of use response so there's not an extra <br> at the end
            return super.takeAction(verb, noun);
        }
    }

    private String parseShootCommand(String noun) {
        Game game = GameState.getInstance().getGame();
        String response = "";

        // only "shoot" or "shoot arrow" does something at mine entrance
        if(noun == null || noun.equals("arrow")) {
            Item bow = game.getInventoryItemByName("bow");
            Item arrow = game.getInventoryItemByName("arrow");
            // Must have the bow and arrow in your inventory
            if(bow == null) {
                response = "You don't have anything to shoot with.";
            }
            else if(arrow == null) {
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

    private String shootArrow(Item arrow) {
        Game game = GameState.getInstance().getGame();
        Location currentLocation = game.getCurrentLocation();
        String response = "";

        game.removeItemFromInventory(arrow);
        addItemToLocation(arrow);
        Item nails = new Item(7, "There are some nails scattered on the ground here", "Some nails", "nails");
        currentLocation.addItemToLocation(nails);
        nailsOff = true;

        // 2 ways to get to mine shaft. Find and remove both
        List<ConnectingLocation> connectingLocations = currentLocation.getConnectingLocations();
        connectingLocations.removeIf(location -> location.getLocation().getName().equals("mine shaft"));

        for (ConnectingLocation connectingLocation : currentLocation.getConnectingLocations()) {
            if (connectingLocation.getLocation().getName().equals("mine shaft")) {
                connectingLocation.getLocation().getConnectingLocations().removeIf(
                        mineShaftLocation -> mineShaftLocation.getLocation().getName().equals("mine entrance")
                );
            }
        }

        currentLocation.setDescription("You're at the entrance of an abandoned gold mine, a recent cave-in preventing " +
                "entry. Piles of tailings scatter the area, leaving only one path leading away from the entrance, heading north.");
        response = "You shoot the arrow and it glances off the nails with a small ringing sound. The nails and your " +
                "arrow land a few feet away then there's a loud crack of the support and the entrance caves in with an " +
                "even louder crash and cloud of dust. Good thing you didn't try to take them by hand.";

        return response;
    }
}