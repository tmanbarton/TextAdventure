package com.project.textadventure.game.Graph;

import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.project.textadventure.constants.GameConstants.EAST_LONG;
import static com.project.textadventure.constants.GameConstants.EAST_SHORT;
import static com.project.textadventure.constants.GameConstants.ENTER;
import static com.project.textadventure.constants.GameConstants.EXIT;
import static com.project.textadventure.constants.GameConstants.IN;
import static com.project.textadventure.constants.GameConstants.OUT;
import static com.project.textadventure.constants.GameConstants.WEST_LONG;
import static com.project.textadventure.constants.GameConstants.WEST_SHORT;
import static com.project.textadventure.constants.LocationDescriptions.BOTTOM_MINE_SHAFT_NO_CAGE_LONG_DESCRIPTION;
import static com.project.textadventure.constants.LocationDescriptions.BOTTOM_MINE_SHAFT_WITH_CAGE_LONG_DESCRIPTION;
import static com.project.textadventure.constants.LocationDescriptions.MOUNTAIN_PASS_NO_CAGE_LONG_DESCRIPTION;
import static com.project.textadventure.constants.LocationDescriptions.MOUNTAIN_PASS_WITH_CAGE_LONG_DESCRIPTION;
import static com.project.textadventure.constants.LocationNames.BOTTOM_OF_VERTICAL_MINE_SHAFT;
import static com.project.textadventure.constants.LocationNames.MINE_CAGE;
import static com.project.textadventure.constants.LocationNames.MOUNTAIN_PASS;

/**
 * Represents the Locations connected to the vertical mine shaft: bottomOfVerticalMineShaft, mountainPass, and mineCage
 */
public class MineShaft extends Location {
    private boolean pointsAcquiredFromButtonPushOutsideMineCage;
    private boolean pointsAcquiredFromButtonPushInsideMineCage;

    public MineShaft(String fullDescription, String shortDescription, List<Item> items, List<LocationConnection> locationConnections, boolean visited, String name) {
        super(fullDescription, shortDescription, items, locationConnections, visited, name);
        this.pointsAcquiredFromButtonPushOutsideMineCage = false;
        this.pointsAcquiredFromButtonPushInsideMineCage = false;
    }

    public void setPointsAcquiredFromButtonPushInsideMineCage(final boolean pointsAcquiredFromButtonPushInsideMineCage) {
        this.pointsAcquiredFromButtonPushInsideMineCage = pointsAcquiredFromButtonPushInsideMineCage;
    }

    public void setPointsAcquiredFromButtonPushOutsideMineCage(final boolean pointsAcquiredFromButtonPushOutsideMineCage) {
        this.pointsAcquiredFromButtonPushOutsideMineCage = pointsAcquiredFromButtonPushOutsideMineCage;
    }

    /**
     * Check if button has already been pushed from either inside or outside the cage, thus adding points to score and
     * setting the respective flag to true. Also set the same flag(s) on the other side of the cage if it hasn't been
     * set yet since the same points can be gotten from all of those locations.
     * (e.g. if the current location is mountainPass, set the flag for bottomOfVerticalMineShaft and mineCage also)
     * @param points The points to add if none have been added for the scenario yet.
     */
    public void checkAndAddPoints(final double points) {
        // Get all locations that need to have the flag set
        final MineShaft bottomOfVerticalMineShaft = (MineShaft) Game.findLocationByName(this, BOTTOM_OF_VERTICAL_MINE_SHAFT);
        final MineShaft mountainPass = (MineShaft) Game.findLocationByName(this, MOUNTAIN_PASS);
        final MineShaft mineCage = (MineShaft) Game.findLocationByName(this, MINE_CAGE);
        if (!pointsAcquiredFromButtonPushOutsideMineCage) {
            // Add points and set the flag for the current location if pushing button from outside the cage (mountain pass or bottom of vertical mine shaft)
            GameState.getInstance().incrementScore(points);
            bottomOfVerticalMineShaft.setPointsAcquiredFromButtonPushOutsideMineCage(true);
            mountainPass.setPointsAcquiredFromButtonPushOutsideMineCage(true);
            mineCage.setPointsAcquiredFromButtonPushOutsideMineCage(true);
        } else if (!pointsAcquiredFromButtonPushInsideMineCage) {
            // Add points and set the flag for the current location if pushing button from inside the cage (mineCage only)
            GameState.getInstance().incrementScore(points);
            bottomOfVerticalMineShaft.setPointsAcquiredFromButtonPushInsideMineCage(true);
            mountainPass.setPointsAcquiredFromButtonPushInsideMineCage(true);
            mineCage.setPointsAcquiredFromButtonPushInsideMineCage(true);
        }
    }

    /**
     * Determine if the mine cage is at the top or bottom of the mine shaft. If it's at the top, remove Mountain Pass from its connecting location
     * and add Bottom of Vertical Mine Shaft. If it's at the bottom, remove Bottom of Vertical Mine Shaft from its connecting location and add Mountain Pass.
     *
     * @return response to display to the user
     */
    public String moveMineCage() {
        final Location bottomOfVerticalMineShaft = Game.findLocationByName(this, BOTTOM_OF_VERTICAL_MINE_SHAFT);
        final Location mountainPass = Game.findLocationByName(this, MOUNTAIN_PASS);

        if (StringUtils.equals(this.getName(), MOUNTAIN_PASS)) {
            return moveMineCageFromAConnectingLocation(bottomOfVerticalMineShaft);
        } else if (StringUtils.equals(this.getName(), BOTTOM_OF_VERTICAL_MINE_SHAFT)) {
            return moveMineCageFromAConnectingLocation(mountainPass);
        } else if (StringUtils.equals(this.getName(), MINE_CAGE)) {
            // Current location is mine cage location.
            // Change connections based on whether it's connected to mountain pass or bottom of vertical mine shaft
            final boolean isConnectedToMountainPass = GameState.getInstance().getGame().isLocationDirectlyConnected(MOUNTAIN_PASS);
            final boolean isConnectedToBottomMineShaft = GameState.getInstance().getGame().isLocationDirectlyConnected(BOTTOM_OF_VERTICAL_MINE_SHAFT);
            if (isConnectedToMountainPass) {
                moveMineCageFromWithinMineCage(bottomOfVerticalMineShaft, mountainPass);

            } else if (isConnectedToBottomMineShaft) {
                moveMineCageFromWithinMineCage(mountainPass, bottomOfVerticalMineShaft);
            }
        }
        return "The cage rattles as it starts to move and, as it reaches the end of the shaft, grinds to a halt with the squeaking of chains and pulleys.";
    }

    /**
     * Moves the mine cage from a connecting location (either the mountain pass or the bottom of the vertical mine shaft)
     * to the opposite location. Updates the connections and descriptions of the involved locations accordingly.
     *
     * @param targetLocation The target location to which the mine cage will be moved.
     * @return A response string indicating the result of moving the mine cage.
     */
    String moveMineCageFromAConnectingLocation(final Location targetLocation) {
        final Location mineCage = Game.findLocationByName(this, MINE_CAGE);
        final List<LocationConnection> currentLocationConnections = this.getLocationConnections();

        final boolean isMineCageConnected = GameState.getInstance().getGame().isLocationDirectlyConnected(MINE_CAGE);
        if (!pointsAcquiredFromButtonPushOutsideMineCage) {
            checkAndAddPoints(5);
        }
        if (isMineCageConnected) {
            // The mine cage is currently connected to the current location and is moving to the opposite location
            // Remove mine cage from current location and vice versa, and add target location to mine cage and vice versa
            currentLocationConnections.removeIf(locationConnection -> locationConnection.getLocation().getName().equals(MINE_CAGE));
            mineCage.setLocationConnections(List.of(new LocationConnection(this.getName().equals(MOUNTAIN_PASS) ? List.of(WEST_LONG, WEST_SHORT, OUT, EXIT) : List.of(EAST_LONG, EAST_SHORT, OUT, EXIT), targetLocation)));
            targetLocation.getLocationConnections().add(new LocationConnection(this.getName().equals(MOUNTAIN_PASS) ? List.of(EAST_LONG, EAST_SHORT, IN, ENTER) : List.of(WEST_LONG, WEST_SHORT, IN, ENTER), mineCage));

            // Update the descriptions of the locations to reflect whether the mine cage is there or not
            this.setDescription(this.getName().equals(MOUNTAIN_PASS) ? MOUNTAIN_PASS_NO_CAGE_LONG_DESCRIPTION : BOTTOM_MINE_SHAFT_NO_CAGE_LONG_DESCRIPTION);
            targetLocation.setDescription(this.getName().equals(MOUNTAIN_PASS) ? BOTTOM_MINE_SHAFT_WITH_CAGE_LONG_DESCRIPTION : MOUNTAIN_PASS_WITH_CAGE_LONG_DESCRIPTION);

            return this.getName().equals(MOUNTAIN_PASS) ?
                    "The mine cage descends into the depths of the mountain with much rattling, creaking, and squeaking. After a moment you hear a faint 'boom' as the cage hits the bottom." :
                    "The mine cage ascends into the shaft with much rattling, creaking, and squeaking. After a moment you hear the cage rattle as it reaches the top.";
        } else {
            // Current location is bottom of mine shaft location and the mine cage is at the top of the shaft (mountainPass location)
            // Remove mountain pass from mine cage and vice versa, and add bottom of mine shaft to cage and vice versa
            targetLocation.getLocationConnections().removeIf(locationConnection -> locationConnection.getLocation().getName().equals(MINE_CAGE));
            currentLocationConnections.add(new LocationConnection(this.getName().equals(MOUNTAIN_PASS) ? List.of(WEST_LONG, WEST_SHORT, IN, ENTER) : List.of(EAST_LONG, EAST_SHORT, IN, ENTER), mineCage));
            mineCage.setLocationConnections(List.of(new LocationConnection(this.getName().equals(MOUNTAIN_PASS) ? List.of(EAST_LONG, EAST_SHORT, OUT, EXIT) : List.of(WEST_LONG, WEST_SHORT, OUT, EXIT), this)));

            // Update the descriptions of the locations to reflect whether the mine cage is there or not
            this.setDescription(this.getName().equals(MOUNTAIN_PASS) ? MOUNTAIN_PASS_WITH_CAGE_LONG_DESCRIPTION : BOTTOM_MINE_SHAFT_WITH_CAGE_LONG_DESCRIPTION);
            targetLocation.setDescription(this.getName().equals(MOUNTAIN_PASS) ? BOTTOM_MINE_SHAFT_NO_CAGE_LONG_DESCRIPTION : MOUNTAIN_PASS_NO_CAGE_LONG_DESCRIPTION);

            return this.getName().equals(MOUNTAIN_PASS) ?
                    "The mine cage ascends from the depths of the mountain with much rattling, creaking, and squeaking. The distant noise soon becomes more dominant as it rumbles into view and lurches to a stop." :
                    "The mine cage descends from the depths of the mountain above with much rattling, creaking, and squeaking. The distant noise soon becomes more dominant as it rumbles into view.";
        }
    }

    /**
     * Moves the mine cage while the player is in the mine cage/current location is mine cage to the target location. Updates the connections and descriptions
     * of the involved locations accordingly.
     *
     * @param targetLocation The target location to which the mine cage will be moved.
     * @param locationConnectedToMineCage The current location that's connected to the mine cage (either the mountain pass or the bottom of the vertical mine shaft).
     */
    void moveMineCageFromWithinMineCage(final Location targetLocation, final Location locationConnectedToMineCage) {
        // Mine cage is currently at the bottom of the mine shaft, connected to the bottom of vertical mine shaft location.
        // Remove bottom of mine shaft from mine cage, add mountain pass to mine cage, and set cage connections to mountain pass
        locationConnectedToMineCage.getLocationConnections().removeIf(locationConnection -> locationConnection.getLocation().getName().equals(MINE_CAGE));
        this.setLocationConnections(List.of(new LocationConnection(targetLocation.getName().equals(MOUNTAIN_PASS) ? List.of(EAST_LONG, EAST_SHORT, OUT, EXIT) : List.of(WEST_LONG, WEST_SHORT, OUT, EXIT), targetLocation)));
        targetLocation.getLocationConnections().add(new LocationConnection(targetLocation.getName().equals(MOUNTAIN_PASS) ? List.of(WEST_LONG, WEST_SHORT, IN, ENTER) : List.of(EAST_LONG, EAST_SHORT, IN, ENTER), this));

        // Update descriptions of the locations to reflect whether the mine cage is there or not
        targetLocation.setDescription(locationConnectedToMineCage.getName().equals(MOUNTAIN_PASS) ? BOTTOM_MINE_SHAFT_WITH_CAGE_LONG_DESCRIPTION : MOUNTAIN_PASS_WITH_CAGE_LONG_DESCRIPTION);
        locationConnectedToMineCage.setDescription(locationConnectedToMineCage.getName().equals(MOUNTAIN_PASS) ? MOUNTAIN_PASS_NO_CAGE_LONG_DESCRIPTION : BOTTOM_MINE_SHAFT_NO_CAGE_LONG_DESCRIPTION);
        if (!pointsAcquiredFromButtonPushInsideMineCage) {
            checkAndAddPoints(7);
        }
    }
}
