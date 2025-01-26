package com.project.textadventure.game;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.constants.ResponseConstants;
import com.project.textadventure.controllers.GameStatus;
import com.project.textadventure.game.Graph.Dam;
import com.project.textadventure.game.Graph.Item;
import com.project.textadventure.game.Graph.Location;
import com.project.textadventure.game.Graph.MineEntrance;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;

public class ActionExecutorUtils {
    public static Item getInventoryItemByName(final String name) {
        final List<Item> inventory = GameState.getInstance().getGame().getInventory();
        for (final Item item : inventory) {
            if (StringUtils.equals(item.getName(), name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * List the items at the location.
     * @param items Items to list
     * @return List of {@link Item}s at the {@link Location} as a String
     */
    public static String listLocationItems(final List<Item> items) {
        items.sort(Comparator.comparingInt(Item::getDisplayOrder));
        final StringBuilder result = new StringBuilder();
        for(final Item item : items) {
            result.append("\n").append(item.getLocationDescription());
        }
        return result.isEmpty() ? "" : "\n" + result;
    }

    /**
     * Get the {@link Item} at the {@link Location} by name if it's there, otherwise null.
     * @param name Name of the item
     * @return {@link Item} at the {@link Location} with the given name, or null if not found
     */
    public static Item getLocationItemByName(final String name) {
        final List<Item> items = GameState.getInstance().getGame().getCurrentLocation().getItems();
        for(final Item item : items) {
            if (name.equals(item.getName())) {
                return item;
            }
        }
        return null;
    }

    /**
     * Add an {@link Item} to the {@link Location}.
     * @param item Item to add
     */
    public static void addItemToLocation(final Item item) {
        final List<Item> items = GameState.getInstance().getGame().getCurrentLocation().getItems();
        items.add(item);
    }

    /**
     * Remove an {@link Item} from the {@link Location}.
     * @param item Item to remove
     */
    public static void removeItemFromLocation(final Item item) {
        final List<Item> items = GameState.getInstance().getGame().getCurrentLocation().getItems();
        items.remove(item);
    }

    /**
     * Add an {@link Item} to the inventory.
     * @param item Item to add
     * @return Response to adding the item to the inventory
     */
    public static String addItemToInventory(final Item item) {
        final Game game = GameState.getInstance().getGame();
        final List<Item> inventory = game.getInventory();
        // Add points to score if the item has points associated with it
        if (game.getInventoryWeight() > game.getInventoryLimit()) {
            return "You can't carry anything more.";
        }
        if (item.getPoints() > 0) {
            GameState.getInstance().incrementScore(item.getPoints());
        }
        inventory.add(item);
        inventory.sort(GameState.getInstance().getGame());
        return null;
    }

    /**
     * Check if a given {@link Item} is in the inventory.
     * @param itemName Name of the item
     * @return True if the item is in the inventory, false otherwise
     */
    public static boolean isItemInInventory(final String itemName) {
        return getInventoryItemByName(itemName) != null;
    }
}
