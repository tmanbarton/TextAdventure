package com.project.textadventure.controller;

import com.project.textadventure.game.GameState;
import com.project.textadventure.game.Player;

import java.util.List;

public class GameController {
    public String getResponse(String input) {
        Player player = GameState.getInstance().getGame();
        return player.getCurrentLocation().getDescription();
//        return findAction(input, player);
    }

    public String findAction(String input, Player player) {
        if(isDirection(input)) {
            return "You went " + input;
        }
        return "description";
    }

    private boolean isDirection(String input) {
        List<String> directions = List.of("n", "north", "s", "south", "e", "east", "w", "west", "u", "up",
                "d", "down", "nw", "northwest", "ne", "northeast", "sw", "southwest", "se", "southeast");
        return directions.contains(input);
    }
}




/*
* do you want indtructions?
* no or yes
* create a graph
* load a game
* create a game from saved game*/