package com.project.textadventure.controllers;

public enum GameStatus {
    NEW,
    IN_PROGRESS,
    LOSE,
    QUITTING,
    // Special state for the mine entrance location when the player is attempting to get the nails
    // Need this to repeatedly ask the player if they actually want to get nails until they enter "yes" or "no"
    GETTING_NAILS;
}
