package com.project.textadventure.game;

import lombok.Data;

@Data
public class Item {
    private final String locationDescription;
    private String inventoryDescription;
    private final String name;
    private final int displayOrder;

    public Item(int displayOrder, String locationDescription, String inventoryDescription, String name) {
        this.displayOrder = displayOrder;
        this.locationDescription = locationDescription;
        this.inventoryDescription = inventoryDescription;
        this.name = name;
    }

    public void setInventoryDescription(String description) {
        this.inventoryDescription = description;
    }
}
