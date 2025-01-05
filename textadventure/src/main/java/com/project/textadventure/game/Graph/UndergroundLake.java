package com.project.textadventure.game.Graph;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.project.textadventure.constants.GameConstants.BAD_DIRECTION;
import static com.project.textadventure.constants.GameConstants.ENTER;
import static com.project.textadventure.constants.GameConstants.IN;
import static com.project.textadventure.constants.GameConstants.NE_LONG;
import static com.project.textadventure.constants.GameConstants.NE_SHORT;
import static com.project.textadventure.constants.GameConstants.SE_LONG;
import static com.project.textadventure.constants.GameConstants.SE_SHORT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_WITH_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_NE_SHORT_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_WITH_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_SE_SHORT_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_WITH_BOAT;
import static com.project.textadventure.constants.LocationDescriptions.UNDERGROUND_LAKE_WEST_SHORT_DESCRIPTION_NO_BOAT;
import static com.project.textadventure.constants.LocationNames.UNDERGROUND_LAKE_NE;
import static com.project.textadventure.constants.LocationNames.UNDERGROUND_LAKE_SE;
import static com.project.textadventure.constants.LocationNames.UNDERGROUND_LAKE_WEST;

public class UndergroundLake extends Location {
    private boolean boatAtLocation;

    public UndergroundLake(final String description, final String shortDescription, final List<Item> items, final List<LocationConnection> locationConnections, final boolean visited, final String name, final boolean boatAtLocation) {
        super(description, shortDescription, items, locationConnections, visited, name);
        this.boatAtLocation = boatAtLocation;
    }

    /**
     * Special handling for when moving into the boat. Otherwise, move as normal.
     * Once in the boat, need to remove the boat as a possible location connection from the current location by setting
     * its directions to empty. Also set the current location's description to not have the boat in it since the
     * player is going to be on the boat and may move to a different location.
     * @param direction The direction to move in
     * @return The result of the move to display to the user
     */
    @Override
    String move(final String direction) {
        Optional <LocationConnection> boatConnection = Optional.empty();
        if (boatAtLocation && (StringUtils.equals(direction, IN) || StringUtils.equals(direction, ENTER))) {
            // Set the current location's description to not have the boat in it since the player is going to be on the boat
            if (StringUtils.equals(this.getDescription(), UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_WITH_BOAT)) {
                this.setDescription(UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_NO_BOAT);
                this.setShortDescription(UNDERGROUND_LAKE_WEST_SHORT_DESCRIPTION_NO_BOAT);
            } else if (StringUtils.equals(this.getDescription(), UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_WITH_BOAT)) {
                this.setDescription(UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_NO_BOAT);
                this.setShortDescription(UNDERGROUND_LAKE_NE_SHORT_DESCRIPTION_NO_BOAT);
            } else if (StringUtils.equals(this.getDescription(), UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_WITH_BOAT)) {
                this.setDescription(UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_NO_BOAT);
                this.setShortDescription(UNDERGROUND_LAKE_SE_SHORT_DESCRIPTION_NO_BOAT);
            }
            // Remove boat from this location to be set at another location connected to the boat
            this.boatAtLocation = false;
            // Find the boat connection to set its directions to empty
            boatConnection = this.getLocationConnections().stream()
                    .filter(locationConnection -> locationConnection.getLocation().getName().equals("boat"))
                    .findFirst();
        }
        final String result = super.move(direction);
        // Set boat connection's directions to empty after moving. Must be after, or super.move won't know the boat is a viable option.
        // Only do so if the boat is the destination and the player is moving into the boat
        boatConnection.ifPresent(locationConnection -> locationConnection.setDirections(List.of()));
        return result;
    }

    public void setBoatAtLocation(boolean boatAtLocation) {
        this.boatAtLocation = boatAtLocation;
    }
}
