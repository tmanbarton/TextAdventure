package com.project.textadventure.game;

import com.project.textadventure.game.Graph.Item;
import com.project.textadventure.game.Graph.Location;

import java.util.Comparator;
import java.util.List;

public class ActionExecutorUtils {
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
}
