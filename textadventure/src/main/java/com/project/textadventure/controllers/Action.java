package com.project.textadventure.controllers;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface Action {
    /**
     * Take an action in the game
     * @param verb The verb part of the command
     * @param noun The noun part of the command
     * @return The response to the action to be displayed to the user
     */
    String takeAction(@NonNull final String verb, @Nullable final String noun);
}
