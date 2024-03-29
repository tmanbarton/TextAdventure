package com.project.textadventure.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameStateTest {
    @Test
    @DisplayName("Create the graph data structure")
    void testCreateGame() {
        Game game = GameState.getInstance().getGame();
        assertNotNull(game.getCurrentLocation(), "The Player should have a current location.");
    }
}
