package com.project.textadventure.game;

import com.project.textadventure.controllers.GameStatus;
import com.project.textadventure.game.Graph.Item;
import com.project.textadventure.game.Graph.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameTest {
    @Test
    @DisplayName("The Item whose name corresponds to the String passed in should be returned Item should be returned.")
    void getInventoryItemByNameSuccess() {
        final Game game = new Game(new ArrayList<>(), new Location("", "", new ArrayList<>(), new ArrayList<>(), false, ""), GameStatus.NEW);

        final Item item1 = new Item(1, "test description", "test description", "test name");
        final Item item2 = new Item(2, "test", "test", "name1");
        game.addItemToInventory(item1);
        game.addItemToInventory(item2);
        final Item actual = game.getInventoryItemByName("test name");
        assertNotNull(actual);
    }

    @Test
    @DisplayName("The Item whose name corresponds to the String passed in should be returned Item should be returned.")
    void getInventoryItemByNameFailure() {
        Game game = new Game(new ArrayList<>(), new Location("", "", new ArrayList<>(), new ArrayList<>(), false, ""), GameStatus.NEW);

        final Item item1 = new Item(1, "test description", "test description", "test");
        game.addItemToInventory(item1);
        final Item actual = game.getInventoryItemByName("test name");
        assertNull(actual);
    }
}
