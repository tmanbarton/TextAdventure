package com.project.textadventure.game;


import com.project.textadventure.controllers.Action;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.thymeleaf.util.StringUtils;

import static com.project.textadventure.constants.GameConstants.*;

public class ActionFactory {

    /**
     * Returns the object that the action is being performed on (game, or location)
     * @param verb Verb part of the command
     * @param noun Noun part of the command
     * @return Object that the action is being performed on
     */
    public static Action getActionObject(@NonNull final String verb, @Nullable final String noun) {
        final Game game = GameState.getInstance().getGame();
        if (StringUtils.startsWith(verb, "pick") && noun != null && StringUtils.startsWith(noun, "up")) {
            return game;
        }
        final String action;
        action = verb.toLowerCase();

        return switch(action) {
            case GET, TAKE, FILL, DROP, THROW, QUIT, RESTART, SCORE, INFO, HELP, INVENTORY_SHORT, INVENTORY_MEDIUM, INVENTORY_LONG,
                    EAT, PUSH, PRESS -> game;
            case GO, NORTH_SHORT, SOUTH_SHORT, EAST_SHORT, WEST_SHORT, NE_SHORT, NW_SHORT, SE_SHORT, SW_SHORT, UP_SHORT, DOWN_SHORT,
                    NORTH_LONG, SOUTH_LONG, EAST_LONG, WEST_LONG, NE_LONG, NW_LONG, SE_LONG, SW_LONG,
                    UP_LONG, DOWN_LONG, IN, ENTER, OUT, EXIT, LOOK_LONG, LOOK_SHORT, OPEN, UNLOCK, SHOOT, TURN -> game.getCurrentLocation();
            default -> null;
        };
    }
}