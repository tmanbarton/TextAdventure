package com.project.textadventure.game.Graph;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.project.textadventure.constants.GameConstants.BAD_DIRECTION;
import static com.project.textadventure.constants.GameConstants.ENTER;
import static com.project.textadventure.constants.GameConstants.IN;
import static com.project.textadventure.constants.GameConstants.NE_LONG;
import static com.project.textadventure.constants.GameConstants.NE_SHORT;
import static com.project.textadventure.constants.GameConstants.SE_LONG;
import static com.project.textadventure.constants.GameConstants.SE_SHORT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationNames.UNDERGROUND_LAKE_NE;
import static com.project.textadventure.constants.LocationNames.UNDERGROUND_LAKE_SE;
import static com.project.textadventure.constants.LocationNames.UNDERGROUND_LAKE_WEST;

public class UndergroundLake extends Location {
    private boolean boatAtLocation;

    public UndergroundLake(final String description, final String shortDescription, final List<Item> items, final List<LocationConnection> locationConnections, final boolean visited, final String name, final boolean boatAtLocation) {
        super(description, shortDescription, items, locationConnections, visited, name);
        this.boatAtLocation = boatAtLocation;
    }

    @Override
    String move(final String direction) {
        if (boatAtLocation && StringUtils.equals(direction, IN) || boatAtLocation && StringUtils.equals(direction, ENTER)) {
            // Set each location connected to the boat to not have the boat at that location then set it to true later
            // once the player chooses a direction
            this.boatAtLocation = false;
        }
        return super.move(direction);
    }

    public void setBoatAtLocation(boolean boatAtLocation) {
        this.boatAtLocation = boatAtLocation;
    }
}
