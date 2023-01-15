package com.project.textadventure.game.Locations;

import com.project.textadventure.controller.Action;
import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import com.project.textadventure.game.Item;
import com.project.textadventure.game.actions.DamActions;

import java.util.List;

import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

public class Dam extends Location implements Action {
    private boolean wheelTurned;
    private boolean magnetDropped;
    DamActions possibleActions;

    public Dam(String fullDescription, String shortDescription, List<Item> items, List<ConnectingLocation> connectingLocations, boolean visited, String name, boolean wheelTurned, boolean magnetDropped) {
        super(fullDescription, shortDescription, items, connectingLocations, visited, name);
        this.wheelTurned = wheelTurned;
        this.magnetDropped = magnetDropped;
    }

    public Dam() {}

    public boolean isWheelTurned() {
        return wheelTurned;
    }

    public boolean isMagnetDropped() {
        return magnetDropped;
    }

    public void setMagnetDropped(boolean magnetDropped) {
        this.magnetDropped = magnetDropped;
    }

    @Override
    public String takeAction(String verb, String noun) {
        String response = "";

        if(verb.equals("turn")) {
            response = parseTurnCommand(verb, noun);
        }
        else {
            response = super.takeAction(verb, noun);
        }
        return response;
    }

    private String parseTurnCommand(String verb, String noun) {
        Game game = GameState.getInstance().getGame();
        Location currentLocation = game.getCurrentLocation();
        String response = "";

        // only "turn" or "" does something at dam
        if(noun.equals("turn") || noun.equals("") && currentLocation instanceof Dam) {
            if(magnetDropped && !wheelTurned) {
                response = turnWheel();
            }
            else if(!magnetDropped) {
                response = "The wheel is locked firmly in place.";
            }
        }
        else {
            response = generateRandomUnknownCommandResponse();
        }
        return response;
    }

    /**
     * Connect the lake town location to the dam location.
     * Change the description of all locations that reference the lake to not include the lake
     * since there is no more lake.
     */
    public String turnWheel() {
        this.getConnectingLocations().get(3).setDirections(List.of("east", "e"));
        this.getConnectingLocations().get(4).setDirections(List.of("down", "d"));

        Location lake = this.getConnectingLocations().get(1).getLocation();
        lake.setDescription("You are on the south side of an empty lake. There's a path going west and there's a dam to the north.");
        lake.setShortDescription("You're on the south side of an empty lake.");

        Location tailings = lake.getConnectingLocations().get(1).getLocation();
        tailings.setDescription("All around are piles of tailings that look like they have been puked into this valley. There's not much else to be seen except the entrance to a mine to the south. There's a path leading east and another going north.");

        Location intersection = tailings.getConnectingLocations().get(2).getLocation();
        intersection.setDescription("You have reached an intersection in the road. It leads into the forest to the north and west and a southern road goes into a thinner part of the forest.");

        Location driveway = intersection.getConnectingLocations().get(2).getLocation()
                .getConnectingLocations().get(1).getLocation();
        driveway.setDescription("You are at the west end of a dirt road surrounded by a forest of pine trees. There is a small gap to the north that exposes a steep, dirt driveway sloping down into the forest. Looking down the road to the east you can see over the trees and into the valley. There's also a foot path going northwest.");

        wheelTurned = true;

        return "The ground starts to rumble and you see a massive concrete wall start to rise out of the water on\nthe opposite side of the lake, blocking the flow of water from the river into the lake. There's\nanother shudder and a huge whirl pool form near the middle of the lake and the water level starts\ngoing down. Soon the water is completely gone, revealing a town that had been under water only a\nfew minutes ago. You can probably get to the town if you go down the dam to the west.";
    }
}
