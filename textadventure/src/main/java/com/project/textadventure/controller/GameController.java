package com.project.textadventure.controller;

import com.project.textadventure.game.GameState;
import com.project.textadventure.game.Player;

import java.util.List;

import static com.project.textadventure.game.Actions.dropItem;
import static com.project.textadventure.game.Actions.getItem;

public class GameController {
    public String getResponse(String input) {
        Player player = GameState.getInstance().getGame();
//        return player.getCurrentLocation().getDescription();
        return findAction(input, player);
    }

    // Direction, get, drop, throw, look, inventory, open, unlock, turn, shoot, fill, solve)
    public String findAction(String input, Player player) {
        String[] splitInput = input.split(" ");
        if(splitInput.length > 2) {
            return "Try to stick to one or two word commands.";
        }
        String verb = splitInput[0];
        String noun = splitInput.length > 1 ? splitInput[1] : null;

        if(isDirection(input)) {
            // move
            return "Moved " + input;
        }
        String result = "";
        switch (verb) {
            case "get":
                result = getItem(noun, player);
                break;
            case "drop":
                result = dropItem(noun, player);
                break;
            default:
                // TODO more variants of IDK and random
                result = "I don't know that word.";
        }

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