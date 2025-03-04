package com.project.textadventure.game;

import com.project.textadventure.controllers.GameStatus;
import com.project.textadventure.game.Graph.Item;
import com.project.textadventure.game.Graph.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.project.textadventure.game.GameUtils.addItemToInventory;
import static com.project.textadventure.game.GameUtils.getInventoryItemByName;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameTest {
    @Test
    @DisplayName("The Item whose name corresponds to the String passed in should be returned Item should be returned.")
    void getInventoryItemByNameSuccess() {
        final Game game = new Game(new ArrayList<>(), new Location("", "", new ArrayList<>(), new ArrayList<>(), false, ""), GameStatus.NEW);

        final Item item1 = new Item(1, "test description", "test description", "test name", 0, 1);
        final Item item2 = new Item(2, "test", "test", "name1", 0, 2);
        addItemToInventory(item1);
        addItemToInventory(item2);
        final Item actual = getInventoryItemByName("test name");
        assertNotNull(actual);
    }

    @Test
    @DisplayName("The Item whose name corresponds to the String passed in should be returned Item should be returned.")
    void getInventoryItemByNameFailure() {
        Game game = new Game(new ArrayList<>(), new Location("", "", new ArrayList<>(), new ArrayList<>(), false, ""), GameStatus.NEW);

        final Item item1 = new Item(1, "test description", "test description", "test", 0, 1);
        addItemToInventory(item1);
        final Item actual = getInventoryItemByName("test name");
        assertNull(actual);
    }
}
