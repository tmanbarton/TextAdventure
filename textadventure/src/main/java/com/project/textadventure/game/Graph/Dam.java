package com.project.textadventure.game.Graph;

import com.project.textadventure.constants.GameConstants;
import com.project.textadventure.constants.LocationDescriptions;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.controllers.Action;

import java.util.List;

import static com.project.textadventure.constants.GameConstants.TURN;

import static com.project.textadventure.game.Game.findLocation;

public class Dam extends Location implements Action {
    private boolean wheelTurned;
    private boolean magnetDropped;

    public Dam(final String fullDescription, final String shortDescription, final List<Item> items, final List<LocationConnection> locationConnections, final boolean visited, final String name, final boolean wheelTurned, final boolean magnetDropped) {
        super(fullDescription, shortDescription, items, locationConnections, visited, name);
        this.wheelTurned = wheelTurned;
        this.magnetDropped = magnetDropped;
    }

    public boolean isMagnetDropped() {
        return magnetDropped;
    }

    public void setMagnetDropped(final boolean magnetDropped) {
        this.magnetDropped = magnetDropped;
    }

    @Override
    public String takeAction(final String verb, final String noun) {
        if (verb.equals(TURN.toString())) {
            return parseTurnCommand(noun);
        }
        else {
            return super.takeAction(verb, noun);
        }
    }

    private String parseTurnCommand(final String noun) {
        String response = "";

        // only "turn wheel" or "turn" does something at dam
        if (noun == null || noun.equals(GameConstants.WHEEL)) {
            if (magnetDropped && !wheelTurned) {
                response = turnWheel();
            }
            else if (!magnetDropped) {
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
        // Find the edge of the graph (ConnectingLocation object) that connects the dam to the lake town and set the directions to down and west
        for(final LocationConnection locationConnection : this.getLocationConnections()) {
            if (locationConnection.getLocation().getName().equals(LocationNames.LAKE_TOWN)) {
                locationConnection.setDirections(List.of(GameConstants.WEST_LONG, GameConstants.WEST_SHORT, GameConstants.DOWN_LONG, GameConstants.DOWN_SHORT));
            }
        }

        // The lake, tailings, and intersection locations all reference the lake in their description
        // Find them and change the descriptions to not include the lake
        final Location lake = findLocation(this, LocationNames.LAKE);
        lake.setDescription(LocationDescriptions.LAKE_LONG_DESCRIPTION_EMPTY_LAKE);
        lake.setShortDescription(LocationDescriptions.LAKE_SHORT_DESCRIPTION_EMPTY_LAKE);

        final Location tailings = findLocation(this, LocationNames.TAILINGS);
        tailings.setDescription(LocationDescriptions.TAILINGS_LONG_DESCRIPTION_EMPTY_LAKE);

        final Location intersection = findLocation(this, LocationNames.INTERSECTION);
        intersection.setDescription(LocationDescriptions.INTERSECTION_LONG_DESCRIPTION_EMPTY_LAKE);

        wheelTurned = true;

        return GameConstants.LAKE_EMPTYING;
    }
}
