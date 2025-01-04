package com.project.textadventure.game.Graph;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.project.textadventure.constants.GameConstants.NE_LONG;
import static com.project.textadventure.constants.GameConstants.NE_SHORT;
import static com.project.textadventure.constants.GameConstants.NW_SHORT;
import static com.project.textadventure.constants.GameConstants.SE_LONG;
import static com.project.textadventure.constants.GameConstants.SE_SHORT;
import static com.project.textadventure.constants.GameConstants.WEST_LONG;
import static com.project.textadventure.constants.GameConstants.WEST_SHORT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_WITH_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_WITH_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_WITH_BOAT;

public class Boat extends Location {
    public Boat(String fullDescription, String shortDescription, List<Item> items, List<LocationConnection> locationConnections, boolean visited, String name) {
        super(fullDescription, shortDescription, items, locationConnections, visited, name);
    }

    @Override
    String move(String direction) {
        // From boat you can only move W, NE, and SE. If it's any of those directions, set boat at location to true for
        // the location you're going to
        this.getLocationConnections().forEach(locationConnection -> {
            // Set each location connected to the boat to not have the boat in the description since we don't know where we came from, only where we're going
            // Override the location we're going to with the description including the boat later
            locationConnection.getLocation().setDescription(UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_NO_BOAT);
            locationConnection.getLocation().setDescription(UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_NO_BOAT);
            locationConnection.getLocation().setDescription(UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_NO_BOAT);

            if (locationConnection.getDirections().contains(direction)) {
                // Boat is only connected to UndergroundLake locations. Set boat at location to true for whichever location the boat is going to
                UndergroundLake undergroundLakeLocation = (UndergroundLake) locationConnection.getLocation();
                undergroundLakeLocation.setBoatAtLocation(true);
                if (StringUtils.equals(direction, WEST_LONG) || StringUtils.equals(direction, WEST_SHORT)) {
                    undergroundLakeLocation.setDescription(UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_WITH_BOAT);
                } else if (StringUtils.equals(direction, NE_LONG) || StringUtils.equals(direction, NE_SHORT)) {
                    undergroundLakeLocation.setDescription(UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_WITH_BOAT);
                } else if (StringUtils.equals(direction, SE_LONG) || StringUtils.equals(direction, SE_SHORT)) {
                    undergroundLakeLocation.setDescription(UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_WITH_BOAT);
                }
            }
        });
        // Move as normally after setting boat at location, if needed
        return super.move(direction);
    }
}
