package com.project.textadventure.game;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.project.textadventure.constants.GameConstants.ALL_COMMANDS;
import static com.project.textadventure.constants.GameConstants.ALL_COMMAND_MAPS;
import static com.project.textadventure.constants.GameConstants.MOVE;
import static com.project.textadventure.constants.GameConstants.NO_LONG;
import static com.project.textadventure.constants.GameConstants.NO_SHORT;
import static com.project.textadventure.constants.GameConstants.YES_LONG;
import static com.project.textadventure.constants.GameConstants.YES_NO_STATES;
import static com.project.textadventure.constants.GameConstants.YES_SHORT;

public class InputParser {
    /**
     * Parse input string into list of commands. Commands are in the form of {@code Pair <action, noun/null>}.
     * @param input User's input verbatim
     * @return List of commands in the form of {@code Pair <action, noun/null>}
     */
    public static List<Pair<String, String>> parseInput(String input) {
        // Remove "the" and "a"/"an" from the command then trim whitespace.
        // Convert to lowercase for easier comparison
        input = input.replaceAll("(\\Wthe\\W)|(\\Wa\\W)|(\\Wan\\W)", " ")
                .trim()
                .toLowerCase();

        // Split input on "and", "then", and "and then" to separate into multiple commands.
        // e.g. "take the key then unlock the door" would be split into ["take the key", "unlock the door"]
        final String[] commands = input.split("(\\Wand then\\W)|(\\Wand\\W)|(\\Wthen\\W)");

        final ArrayList<Pair<String, String>> parsedCommands = new ArrayList<>();

        for (String command : commands) {
            // Split the command into a list of individual words
            final String[] words = command.split("\\s+");

            // Find the longest matching command
            // Start by checking the entire command, then remove the last word and check again, and so on until we
            // find a match or determine it's not a valid command
            for (int i = words.length; i > 0; i--) {
                final String potentialCommand = String.join(" ", List.of(words).subList(0, i));
                if (ALL_COMMANDS.contains(potentialCommand)) {
                    // We found a match; convert the command into a generic one for easy comparison later,
                    // then add the action and noun to the list of commands to be executed
                    final String action = getActualCommand(potentialCommand);
                    // If this is a move command in the form "<direction>" with no verb, set the noun to the direction
                    final String noun = StringUtils.equals(MOVE, action) && words.length == 1 ?
                            command : String.join(" ", List.of(words).subList(i, words.length)).trim();
                    parsedCommands.add(Pair.of(action, noun));
                    break;
                }
            }
        }

        return parsedCommands;
    }

    /**
     * Find a key in the All_COMMANDS_MAP whose list contains the input and return the value of that key.
     * e.g. if the action is "take", the actual value will be "get", or "take inventory" -> "inventory"
     *
     * @param inputAction Command to get the value for
     * @return Value for the action
     */
    @Nullable
    public static String getActualCommand(@NonNull final String inputAction) {
        // Check if we're in a state that requires yes/no response and. if so, simplify input to "yes"/"no"
        if (YES_NO_STATES.contains(GameState.getInstance().getGame().getGameStatus())) {
            if (StringUtils.equals(inputAction, YES_LONG) || StringUtils.equals(inputAction, YES_SHORT)) {
                return YES_LONG;
            } else if (StringUtils.equals(inputAction, NO_LONG) || StringUtils.equals(inputAction, NO_SHORT)) {
                return NO_LONG;
            }
        }
        return ALL_COMMAND_MAPS.stream()
                .flatMap(map -> map.entrySet().stream()) // Flatten all map entries into a single stream
                .filter(entry -> entry.getKey().contains(inputAction)) // Check if the key list contains the input action
                .map(Map.Entry::getValue) // Extract the value of the matching entry
                .findFirst() // Get the first matching value, if any
                .orElse(null); // Return null if no match is found
    }
}
