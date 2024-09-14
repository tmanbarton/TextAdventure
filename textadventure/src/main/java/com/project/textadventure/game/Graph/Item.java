package com.project.textadventure.game.Graph;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Data
public class Item {
    private final String locationDescription;
    private String inventoryDescription;
    private final String name;
    private final int displayOrder;

    public Item(final int displayOrder, final String locationDescription, final String inventoryDescription, final String name) {
        this.displayOrder = displayOrder;
        this.locationDescription = locationDescription;
        this.inventoryDescription = inventoryDescription;
        this.name = name;
    }

    public void setInventoryDescription(final String description) {
        this.inventoryDescription = description;
    }

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
