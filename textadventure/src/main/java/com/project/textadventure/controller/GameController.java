package com.project.textadventure.controller;

import java.util.List;

public class GameController {
    public String getResponse(String input) {
        return findAction(input);
    }

    public String findAction(String input) {
        if(isDirection(input)) {
            return "You went " + input;
        }
        return "description";
    }

    private boolean isDirection(String input) {
        List<String> directions = List.of("n", "north", "s", "south", "e", "east", "w", "west", "u", "up", "d", "down", "nw", "ne", "sw", "se");
        return (input.length() <= 5 && directions.contains(input)) || (input.length() > 5 && directions.contains(input.substring(0, 5)));
    }
}




/*
* do you want indtructions?
* no or yes
* create a graph
* load a game
* create a game from saved game*/