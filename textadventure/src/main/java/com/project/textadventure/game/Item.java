package com.project.textadventure.game;

public class Item {
    private final int order;
    private final String locationDescription;
    private final String inventoryDescription;
    private final String name;

    public Item(int order, String locationDescription, String inventoryDescription, String name) {
        this.order = order;
        this.locationDescription = locationDescription;
        this.inventoryDescription = inventoryDescription;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
