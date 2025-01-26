package com.project.textadventure.game;

import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.game.Graph.Item;
import com.project.textadventure.game.Graph.Location;
import com.project.textadventure.game.Graph.LocationConnection;

import static com.project.textadventure.constants.ItemConstants.ARROW_NAME;

public class ActionExecutor {

    /**
     * Move to the {@link Location} connected by the given connection.
     * @param locationConnection Connection to the {@link Location} to move to
     * @return Description of the new {@link Location} + {@link Item}s at the location
     */
    public static String moveToLocation(final LocationConnection locationConnection) {
        final Location toLocation = locationConnection.getLocation();

        GameState.getInstance().getGame().setCurrentLocation(toLocation);

        if (toLocation.isVisited()) {
            return toLocation.getShortDescription() + ActionExecutorUtils.listLocationItems(toLocation.getItems());
        }
        toLocation.setVisited(true);
        return toLocation.getDescription() + ActionExecutorUtils.listLocationItems(toLocation.getItems());
    }

    /**
     * Display the current {@link Location}'s description and list the {@link Item}s at the {@link Location}.
     * @return Description of the current location + items at the {@link Location}
     */
    public static String executeLookCommand() {
        final Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();
        return currentLocation.getDescription() + ActionExecutorUtils.listLocationItems(currentLocation.getItems());
    }

    /**
     * The box and arrow are in the inventory. Execute shooting the arrow and remove it from the inventory and add it
     * to the location or only remove it from inventory if shot in the boat.
     * @return Response to shooting the arrow
     */
    public static String shootArrow() {
        final Game game = GameState.getInstance().getGame();
        final Item arrow = game.getInventoryItemByName(ARROW_NAME);
        if (game.getCurrentLocation().getName().equals(LocationNames.BOAT)) {
            game.removeItemFromInventory(arrow);
            return "Your arrow goes flying off into the the distance and splashes into the water, never to be found again.";
        }
        game.removeItemFromInventory(arrow);
        game.getCurrentLocation().addItemToLocation(arrow);
        return "Your arrow goes flying off into the the distance and lands with a thud on the ground.";
    }
}
