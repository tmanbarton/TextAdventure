package com.project.textadventure.game;


import com.project.textadventure.controllers.Action;

import static com.project.textadventure.constants.GameConstants.*;

public class ActionFactory {
    public static Action getActionObject(final String verb, final String noun) {
        final Game game = GameState.getInstance().getGame();
        if (verb.equals("pick") && noun.startsWith("up")) {
            return game;
        }
//        final Actions action;
        final String action;
        try {
             action = verb.toLowerCase();
        } catch (IllegalArgumentException ex) {
            return null;
        }

        return switch(action) {
            case GET, TAKE, FILL, DROP, THROW, QUIT, RESTART, SCORE, INFO, HELP, I, INVEN, INVENTORY, EAT -> game;
            case NORTH_SHORT, SOUTH_SHORT, EAST_SHORT, WEST_SHORT, NE_SHORT, NW_SHORT, SE_SHORT, SW_SHORT, UP_SHORT, DOWN_SHORT,
                    NORTH_LONG, SOUTH_LONG, EAST_LONG, WEST_LONG, NE_LONG, NW_LONG, SE_LONG, SW_LONG,
                    UP_LONG, DOWN_LONG, IN, ENTER, OUT, EXIT, LOOK, L, OPEN, UNLOCK, SHOOT, TURN -> game.getCurrentLocation();
            default -> null;
        };
    }
}