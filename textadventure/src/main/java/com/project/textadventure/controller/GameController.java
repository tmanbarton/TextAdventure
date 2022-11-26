package com.project.textadventure.controller;

import com.project.textadventure.game.GameState;
import com.project.textadventure.game.Player;

import java.util.List;

import static com.project.textadventure.game.Actions.*;

public class GameController {
    public String getResponse(String input) {
        return findAction(input);
    }

    // Direction, get, drop, throw, look, inventory, help, quit, open, unlock, turn, shoot, fill, solve)
    public String findAction(String input) {
        String[] splitInput = input.split(" ");
        if(splitInput.length > 2) {
            return "Try to stick to one or two word commands.";
        }
        String verb = splitInput[0];
        String noun = splitInput.length > 1 ? splitInput[1] : null;

        if(isDirection(verb)) {
            return makeMove(verb);
        }
        String result = switch (verb) {
            case "get" -> noun == null ? "What do you want to get?" : getItem(noun);
            case "drop", "throw" -> noun == null ? "What do you want to drop?" : dropItem(noun);
            case "look" -> look();
            case "inventory", "inven", "invent", "invento", "inventor" -> inventory();
            case "help" -> "help";
            case "unlock", "unloc" -> "unlock";
            case "open" -> "open";
            case "fill" -> "fill";
            case "shoot" -> "shoot";
            case "turn" -> "turn";
            case "solve" -> "solve";
            case "quit" -> "quit";
            default ->
                // TODO more variants of IDK and make random
                    "I don't know that word.";
        };

        return result;
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