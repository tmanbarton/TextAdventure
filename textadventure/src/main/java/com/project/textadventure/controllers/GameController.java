package com.project.textadventure.controllers;

import com.project.textadventure.constants.LocationDescriptions;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.constants.ResponseConstants;
import com.project.textadventure.dto.Input;
import com.project.textadventure.dto.GameResponse;
import com.project.textadventure.dto.ServiceResponse;

import com.project.textadventure.game.ActionFactory;
import com.project.textadventure.game.Graph.LocationConnection;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import com.project.textadventure.game.Graph.Location;
import com.project.textadventure.game.Graph.MineEntrance;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.project.textadventure.constants.GameConstants.GAME_INTRO;
import static com.project.textadventure.constants.ResponseConstants.OK;
import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

@RestController
@RequestMapping("/api/vots/game")
public class GameController {

    @PostMapping("/gameRequest")
    public ResponseEntity<ServiceResponse<GameResponse>> executeCommand(@RequestBody Input input) {
        final String inputString = input.getInput().toLowerCase();
        final Game game = GameState.getInstance().getGame();
        // Get list of commands from input in the form Pair<verb, noun>
        final List<Pair<String, String>> commands = parseInput(inputString);
        StringBuilder result = new StringBuilder();
        for (final Pair<String, String> command : commands) {
            // If the game is not in progress, it's some other state that requires the user to answer yes/no
            if (game.getGameStatus() != GameStatus.IN_PROGRESS) {
                result = new StringBuilder(handleYesNoConfirmation(command.getKey()));
                break;
            }
            final Action action = ActionFactory.getActionObject(command.getKey(), command.getValue());
            if (action == null) {
                result = new StringBuilder(generateRandomUnknownCommandResponse());
                break;
            }
            result.append(action.takeAction(command.getKey(), command.getValue()));
        }
        final GameResponse resp = new GameResponse(result.toString(), input.getInput());
        final ServiceResponse<GameResponse> response = new ServiceResponse<>("success", resp);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Split input on spaces and separates it into verb and noun. Remove any " the "s from the command.
     * If there are multiple commands, split them on "and" and "then" and separate them into verb and noun.
     * @param input User's input
     * @return List of commands
     */
    private List<Pair<String, String>> parseInput(final String input) {
        // Split input on "and" and "then" to separate into multiple commands
        final String[] commands = input.split("and|then");
        final ArrayList<Pair<String, String>> parsedCommands = new ArrayList<>();

        for (String command : commands) {
            // Remove any " the "s from the command
            command = command.replaceAll("\\Wthe\\W", " ");
            final String[] parsedCommand = command.trim().split(" ", 2);
            final String verb = parsedCommand[0];
            final String noun = parsedCommand.length > 1 ? parsedCommand[1] : null;
            final Pair<String, String> commandPair = new ImmutablePair<>(verb, noun);
            parsedCommands.add(commandPair);
        }
        return parsedCommands;
    }

    /**
     * Handles the player's response to the initial confirmation prompt at the start of the game (Would you like help?)
     * @param input User's input
     * @return Game intro, if requested then current location's description, only current location's description if not requested,
     * "Please anwser the question." if something else is entered
     */
    private String handleYesNoConfirmation(final String input) {
        final Game game = GameState.getInstance().getGame();
        final GameStatus status = game.getGameStatus();

        if (input.equals("yes") || input.equals("y")) {
            // Resume the game since user answered the question
            game.setGameStatus(GameStatus.IN_PROGRESS);
            switch (status) {
                case NEW:
                    // If this is a new game, we start by asking if the user wants help. If they say yes, we start the game with
                    // returning the help info and the location description and set the game status to in progress
                    return GAME_INTRO + "\n\n" + game.getCurrentLocation().getDescription();
                case QUITTING:
                    // If the user is quitting and confirm they want to quit, restart the game state
                    GameState.getInstance().restartGame();
                    return OK + " " + GameState.getInstance().getGame().getCurrentLocation().getDescription();
                case GETTING_NAILS:
                    final Location currentLocation = game.getCurrentLocation();
                    // Set isCollapsed to true for future logic to know what can and can't be done at this location
                    ((MineEntrance) currentLocation).setCollapsed(true);

                    final List<LocationConnection> locationConnections = currentLocation.getLocationConnections();
                    locationConnections.removeIf(location -> location.getLocation().getName().equals(LocationNames.MINE_SHAFT));
                    // 2 ways to get to mine shaft/2 locations connected to it in the graph. Find and remove both
                    for (final LocationConnection locationConnection : currentLocation.getLocationConnections()) {
                        if (locationConnection.getLocation().getName().equals(LocationNames.MINE_SHAFT)) {
                            locationConnection.getLocation().getLocationConnections().removeIf(
                                    mineShaftLocation -> mineShaftLocation.getLocation().getName().equals(LocationNames.MINE_ENTRANCE)
                            );
                        }
                    }

                    currentLocation.setDescription(LocationDescriptions.MINE_ENTRANCE_RECENT_CAVE_IN);
                    GameState.getInstance().decrementScore(10);
                    return game.die();
                default:
                    // Should never get here.
                    return "Invalid game status. Something went wrong.";
            }
        } else if (input.equals("n") || input.equals("no")) {
            // Resume the game since user answered the question
            game.setGameStatus(GameStatus.IN_PROGRESS);
            return switch (status) {
                // If this is a new game, we start by asking if the user wants help. If they say no, we start the game with
                // returning just the location description
                case NEW -> game.getCurrentLocation().getDescription();
                // If the user is quitting and decide they don't actually want to quit, return "OK."
                case QUITTING -> OK;
                case GETTING_NAILS -> "Good choice.";
                // Should never get here.
                default -> "Invalid game status. Something went wrong.";
            };
        } else {
            // User didn't answer the question, so re-prompt until they answer
            return ResponseConstants.PLEASE_ANSWER_QUESTION;
        }
    }
}