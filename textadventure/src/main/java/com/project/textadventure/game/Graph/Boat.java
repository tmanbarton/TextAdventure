package com.project.textadventure.game.Graph;

import java.util.List;

import static com.project.textadventure.constants.GameConstants.NE_DIRECTIONS;
import static com.project.textadventure.constants.GameConstants.SE_DIRECTIONS;
import static com.project.textadventure.constants.GameConstants.WEST_DIRECTIONS;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_WITH_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_NE_SHORT_DESCRIPTION_WITH_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_WITH_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_SE_SHORT_DESCRIPTION_WITH_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_WITH_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_WEST_SHORT_DESCRIPTION_WITH_BOAT;

public class Boat extends Location {
    public Boat(String fullDescription, String shortDescription, List<Item> items, List<LocationConnection> locationConnections, boolean visited, String name) {
        super(fullDescription, shortDescription, items, locationConnections, visited, name);
    }

    /**
     * Special handling for when moving out of the boat. Otherwise, move as normal.
     * Once out of the boat, need to set the boat at the location to true for the location the player is going to and
     * set the allowed directions to get into the boat since it is now at a location. Also set the description of the next
     * location to include the boat.
     * @param direction The direction to move in
     * @return The result of the move to display to the user
     */
    @Override
    String move(final String direction) {
        // From boat you can only move W, NE, and SE. If it's any of those directions, set boat at location to true for
        // the location you're going to
        this.getLocationConnections().forEach(locationConnection -> {
            if (locationConnection.getDirections().contains(direction)) {
                // Boat is only connected to UndergroundLake locations. Set allowed directions to go into the boat at the
                // location the player is going to from the boat
                final UndergroundLake undergroundLakeLocation = (UndergroundLake) locationConnection.getLocation();
                undergroundLakeLocation.setBoatAtLocation(true);
                // Depending on the direction, set the description of the location to include the boat
                if (WEST_DIRECTIONS.contains(direction)) {
                    undergroundLakeLocation.setDescription(UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_WITH_BOAT);
                    undergroundLakeLocation.setShortDescription(UNDERGROUND_LAKE_WEST_SHORT_DESCRIPTION_WITH_BOAT);
                } else if (NE_DIRECTIONS.contains(direction)) {
                    undergroundLakeLocation.setDescription(UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_WITH_BOAT);
                    undergroundLakeLocation.setShortDescription(UNDERGROUND_LAKE_NE_SHORT_DESCRIPTION_WITH_BOAT);
                } else if (SE_DIRECTIONS.contains(direction)) {
                    undergroundLakeLocation.setDescription(UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_WITH_BOAT);
                    undergroundLakeLocation.setShortDescription(UNDERGROUND_LAKE_SE_SHORT_DESCRIPTION_WITH_BOAT);
                }
                undergroundLakeLocation.getLocationConnections()
                        .stream()
                        .filter(connection -> connection.getLocation().getName().equals("boat"))
                        .findFirst().get().setDirections(List.of("in", "enter"));
            }
        });
        // Move as normally after setting boat at location, if needed
        return super.move(direction);
    }
}
