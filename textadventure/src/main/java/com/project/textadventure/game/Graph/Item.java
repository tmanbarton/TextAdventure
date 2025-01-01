package com.project.textadventure.game.Graph;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Represents an item in the game with a description for the inventory, a description for the location, name, points
 * related to it if any, and an int to determine where in the list it's displayed when taking inventory and listing items at location.
 */
@Data
public class Item {
    private final String locationDescription;
    private String inventoryDescription;
    private final String name;
    private final int displayOrder;
    private final double points;

    public Item(final int displayOrder, final String locationDescription, final String inventoryDescription, final String name, final double points) {
        this.displayOrder = displayOrder;
        this.locationDescription = locationDescription;
        this.inventoryDescription = inventoryDescription;
        this.name = name;
        this.points = points;
    }

    public void setInventoryDescription(final String description) {
        this.inventoryDescription = description;
    }

    /**
     * @return Equal if the names are equal
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Item item = (Item) o;
        return StringUtils.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
