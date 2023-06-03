package com.project.textadventure.game.Locations;

import com.project.textadventure.constants.Constants;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import com.project.textadventure.game.Item;
import com.project.textadventure.game.actions.DamActions;

import java.util.List;

public class Dam extends Location implements Action {
    private boolean wheelTurned;
    private boolean magnetDropped;
    DamActions possibleActions;

    public Dam(String fullDescription, String shortDescription, List<Item> items, List<ConnectingLocation> connectingLocations, boolean visited, String name, boolean wheelTurned, boolean magnetDropped) {
        super(fullDescription, shortDescription, items, connectingLocations, visited, name);
        this.wheelTurned = wheelTurned;
        this.magnetDropped = magnetDropped;
    }

    public boolean isMagnetDropped() {
        return magnetDropped;
    }

    public void setMagnetDropped(boolean magnetDropped) {
        this.magnetDropped = magnetDropped;
    }

    @Override
    public String takeAction(String verb, String noun) {
        if(verb.equals("turn")) {
            return parseTurnCommand(noun);
        }
        else {
            return super.takeAction(verb, noun);
        }
    }

    private String parseTurnCommand(String noun) {
        Game game = GameState.getInstance().getGame();
        Location currentLocation = game.getCurrentLocation();
        String response = "";

        // only "turn wheel" or "turn" does something at dam
        if(noun == null || noun.equals("wheel")) {
            if(magnetDropped && !wheelTurned) {
                response = turnWheel();
            }
            else if(!magnetDropped) {
                response = "The wheel is locked firmly in place.";
            }
        }
        else {
            response = "There's nothing to turn here.";
        }
        return response;
    }

    /**
     * Connect the lake town location to the dam location.
     * Change the description of all locations that reference the lake to not include the lake
     * since there is no more lake.
     */
    public String turnWheel() {
        // Connect dam to Lake Town location, which is always third in the list of locations in its list of connecting locations
        this.getConnectingLocations().get(2).setDirections(List.of(Constants.WEST, Constants.W, Constants.DOWN, Constants.D));

        Location lake = this.getConnectingLocations().get(1).getLocation();
        Location lake1 = findConnectingLocation(this, LocationNames.LAKE);
        lake.setDescription("You are on the south side of an empty lake. There's a path going west and there's a dam to the north.");
        lake.setShortDescription("You're on the south side of an empty lake.");

        Location tailings = lake.getConnectingLocations().get(1).getLocation();
        tailings.setDescription("All around are piles of tailings that look like they have been puked into this valley. There's not much else to be seen except the entrance to a mine to the south. There's a path leading east and another going north.");

        Location intersection = tailings.getConnectingLocations().get(2).getLocation();
        intersection.setDescription("You have reached an intersection in the road. It leads into the forest to the north and west and a southern road goes into a thinner part of the forest.");

        wheelTurned = true;

        return "The ground begins to rumble and you see a massive wall slowly rise from the water on the far side of the lake, blocking the flow of water from the river into the lake. There's another shudder and the water begins to recede as a huge whirl pool form near the middle of the lake. Soon the water is completely gone, revealing a town that had been submerged only a few moments ago. You can get to the town to the west, down the dam.";
    }

    Location findConnectingLocation(Location location, String targetLocationName) {
        return location;
    }
}
