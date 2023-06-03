package com.project.textadventure.game;

import com.project.textadventure.constants.Actions;
import com.project.textadventure.controllers.Action;

public class ActionFactory {
    public static Action getActionObject(final String verb, final String noun) {
        final Game game = GameState.getInstance().getGame();
        if(verb.equals("pick") && noun.startsWith("up")) {
            return game;
        }
        final Actions action;
        try {
             action = Actions.valueOf(verb.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }

        return switch(action) {
            case GET, TAKE, FILL, DROP, THROW, QUIT, RESTART, SCORE, INFO, HELP, I, INVEN, INVENTORY -> game;
            case N, S, E, W, NE, NW, SE, SW, U, D, NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST,SOUTHWEST,
                    UP, DOWN, IN, ENTER, OUT, EXIT, LOOK, L, OPEN, UNLOCK, SHOOT, TURN -> game.getCurrentLocation();
        };
    }
}