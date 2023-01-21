package com.project.textadventure.game;

import com.project.textadventure.controllers.Action;

public class ActionFactory {
    public static Action getActionObject(String verb, String noun) {
        Game game = GameState.getInstance().getGame();
        if(verb.equals("pick") && noun.startsWith("up")) {
            return game;
        }

        Action retVal = switch(verb) {
            case "get", "take", "fill", "drop", "throw", "solve", "quit", "restart", "score", "info",  "i", "inven", "inventory" -> game;
            case "n", "s", "e", "w", "ne", "nw", "se", "sw", "u", "d",
                    "north", "south", "east", "west", "northeast", "north east","northwest", "north west",
                    "southeast", "south east", "southwest", "south west", "up", "down", "in", "enter", "out", "exit",
                    "look", "l", "open", "unlock", "shoot", "turn" -> game.getCurrentLocation();
            default -> null;
        };
        return retVal;
    }
}