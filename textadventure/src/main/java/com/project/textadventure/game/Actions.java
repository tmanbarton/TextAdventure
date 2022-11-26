package com.project.textadventure.game;

public class Actions {
    public static String getItem(String inputItem, Player player) {
        Item item = player.getCurrentLocation().getItemByName(inputItem);
        if(item != null) {
            player.addItemToInventory(item);
            player.getCurrentLocation().removeItemFromLocation(item);
            return "OK";
        }
        return "I don't see that here.";
    }

    public static String dropItem(String inputItem, Player player) {
        Item item = player.getInventoryItemByName(inputItem);
        if(item != null) {
            player.removeItemFromInventory(item);
            player.getCurrentLocation().addItemToLocation(item);
            return "OK";
        }
        return "You're not carrying it!";
    }
}
