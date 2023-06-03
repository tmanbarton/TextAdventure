package com.project.textadventure.game;

import lombok.Data;

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
}
