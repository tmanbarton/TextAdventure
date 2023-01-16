package com.project.textadventure.game;

public class Cube extends Item {

    boolean solved;
    Cube(int order, String locationPrint, String inventoryPrint, String name, boolean solved) {
        super(order, locationPrint, inventoryPrint, name);
        this.solved = solved;
    }
}
