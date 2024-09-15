package com.project.textadventure.game;


import com.project.textadventure.controllers.Action;

import static com.project.textadventure.constants.GameConstants.GET;
import static com.project.textadventure.constants.GameConstants.TAKE;
import static com.project.textadventure.constants.GameConstants.DROP;
import static com.project.textadventure.constants.GameConstants.THROW;
import static com.project.textadventure.constants.GameConstants.FILL;
import static com.project.textadventure.constants.GameConstants.INVENTORY;
import static com.project.textadventure.constants.GameConstants.INVEN;
import static com.project.textadventure.constants.GameConstants.I;
import static com.project.textadventure.constants.GameConstants.QUIT;
import static com.project.textadventure.constants.GameConstants.RESTART;
import static com.project.textadventure.constants.GameConstants.SCORE;
import static com.project.textadventure.constants.GameConstants.INFO;
import static com.project.textadventure.constants.GameConstants.HELP;
import static com.project.textadventure.constants.GameConstants.NORTH_SHORT;
import static com.project.textadventure.constants.GameConstants.SOUTH_SHORT;
import static com.project.textadventure.constants.GameConstants.EAST_SHORT;
import static com.project.textadventure.constants.GameConstants.WEST_SHORT;
import static com.project.textadventure.constants.GameConstants.NE_SHORT;
import static com.project.textadventure.constants.GameConstants.NW_SHORT;
import static com.project.textadventure.constants.GameConstants.SE_SHORT;
import static com.project.textadventure.constants.GameConstants.SW_SHORT;
import static com.project.textadventure.constants.GameConstants.UP_SHORT;
import static com.project.textadventure.constants.GameConstants.DOWN_SHORT;
import static com.project.textadventure.constants.GameConstants.NORTH_LONG;
import static com.project.textadventure.constants.GameConstants.SOUTH_LONG;
import static com.project.textadventure.constants.GameConstants.EAST_LONG;
import static com.project.textadventure.constants.GameConstants.WEST_LONG;
import static com.project.textadventure.constants.GameConstants.NE_LONG;
import static com.project.textadventure.constants.GameConstants.NW_LONG;
import static com.project.textadventure.constants.GameConstants.SE_LONG;
import static com.project.textadventure.constants.GameConstants.SW_LONG;
import static com.project.textadventure.constants.GameConstants.UP_LONG;
import static com.project.textadventure.constants.GameConstants.DOWN_LONG;
import static com.project.textadventure.constants.GameConstants.IN;
import static com.project.textadventure.constants.GameConstants.ENTER;
import static com.project.textadventure.constants.GameConstants.OUT;
import static com.project.textadventure.constants.GameConstants.EXIT;
import static com.project.textadventure.constants.GameConstants.LOOK;
import static com.project.textadventure.constants.GameConstants.L;
import static com.project.textadventure.constants.GameConstants.OPEN;
import static com.project.textadventure.constants.GameConstants.UNLOCK;
import static com.project.textadventure.constants.GameConstants.SHOOT;
import static com.project.textadventure.constants.GameConstants.TURN;

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
            case GET, TAKE, FILL, DROP, THROW, QUIT, RESTART, SCORE, INFO, HELP, I, INVEN, INVENTORY -> game;
            case NORTH_SHORT, SOUTH_SHORT, EAST_SHORT, WEST_SHORT, NE_SHORT, NW_SHORT, SE_SHORT, SW_SHORT, UP_SHORT, DOWN_SHORT,
                    NORTH_LONG, SOUTH_LONG, EAST_LONG, WEST_LONG, NE_LONG, NW_LONG, SE_LONG, SW_LONG,
                    UP_LONG, DOWN_LONG, IN, ENTER, OUT, EXIT, LOOK, L, OPEN, UNLOCK, SHOOT, TURN -> game.getCurrentLocation();
            default -> null;
        };
    }
}