package com.project.textadventure.game;

import lombok.Data;

@Data
public class Item {
    private final String locationDescription;
    private String inventoryDescription;
    private final String name;
    private final int order;

    public Item(int order, String locationDescription, String inventoryDescription, String name) {
        this.order = order;
        this.locationDescription = locationDescription;
        this.inventoryDescription = inventoryDescription;
        this.name = name;
    }

    public void setInventoryDescription(String description) {
        this.inventoryDescription = description;
    }
}
