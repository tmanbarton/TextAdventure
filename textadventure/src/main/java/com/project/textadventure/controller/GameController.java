package com.project.textadventure.controller;

import com.project.textadventure.game.CreateGame;
import com.project.textadventure.game.graph.LocationGraph;

import java.util.List;

public class GameController {
    public String getResponse(String input, boolean gameStarted) {
        if(!gameStarted) {
            LocationGraph locationGraph = new CreateGame().createNewGame();
            return "not started "+ findAction(input, locationGraph);
        }
        return "started " + findAction(input);
    }

    public String findAction(String input, LocationGraph locationGraph) {
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