package com.project.textadventure.game.Graph;

import com.project.textadventure.constants.GameConstants;
import com.project.textadventure.constants.LocationDescriptions;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.controllers.Action;
import com.project.textadventure.game.GameState;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.List;

import static com.project.textadventure.constants.GameConstants.TURN;

import static com.project.textadventure.constants.GameConstants.WEST_AND_DOWN_DIRECTIONS;
import static com.project.textadventure.game.Game.findLocationByName;

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

    /**
     * Take any actions specific to the dam location, or, if no special commands are given, call the super class method to take a generic action.
     * @param command The verb part of the command
     * @param noun The noun of the command
     * @return The response to the action to be displayed to the user
     */
    @Override
    public String takeAction(@NonNull String command, @Nullable final String noun) {
        if (StringUtils.equals(command, TURN)) {
            return parseTurnCommand(noun);
        }
        else {
            return super.takeAction(command, noun);
        }
    }

    /**
     * Parse the turn command for the dam location. Determine if the wheel is allowed to be turned and update the state if it is.
     * @param noun The noun form of the command.
     * @return The response to the action to be displayed to the user.
     */
    private String parseTurnCommand(final String noun) {
        // only "turn wheel" or "turn" does something at dam
        if (noun == null || noun.equals(GameConstants.WHEEL)) {
            if (magnetDropped && !wheelTurned) {
                GameState.getInstance().incrementScore(25);
                return turnWheel();
            }
            else if (!magnetDropped) {
                return "The wheel is locked firmly in place.";
            } else if (wheelTurned) {
                return "You turn the wheel. Nothing happens.";
            }
        }
        return "You can't turn that.";

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
                locationConnection.setDirections(WEST_AND_DOWN_DIRECTIONS);
            }
        }

        // The lake, tailings, and intersection locations all reference the lake in their description
        // Find them and change the descriptions to not include the lake
        final Location lake = findLocationByName(this, LocationNames.LAKE);
        lake.setDescription(LocationDescriptions.LAKE_LONG_DESCRIPTION_EMPTY_LAKE);
        lake.setShortDescription(LocationDescriptions.LAKE_SHORT_DESCRIPTION_EMPTY_LAKE);

        final Location tailings = findLocationByName(this, LocationNames.TAILINGS);
        tailings.setDescription(LocationDescriptions.TAILINGS_LONG_DESCRIPTION_EMPTY_LAKE);

        final Location intersection = findLocationByName(this, LocationNames.INTERSECTION);
        intersection.setDescription(LocationDescriptions.INTERSECTION_LONG_DESCRIPTION_EMPTY_LAKE);

        this.setDescription(LocationDescriptions.DAM_LONG_DESCRIPTION_WHEEL_TURNED);

        wheelTurned = true;

        return GameConstants.LAKE_EMPTYING;
    }
}
