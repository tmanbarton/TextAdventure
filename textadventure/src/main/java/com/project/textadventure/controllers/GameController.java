package com.project.textadventure.controllers;

import com.project.textadventure.constants.LocationDescriptions;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.constants.ResponseConstants;
import com.project.textadventure.dto.Input;
import com.project.textadventure.dto.GameResponse;
import com.project.textadventure.dto.ServiceResponse;

import com.project.textadventure.game.Graph.LocationConnection;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import com.project.textadventure.game.Graph.Location;
import com.project.textadventure.game.Graph.MineEntrance;
//import org.apache.commons.text.StringEscapeUtils;
import com.project.textadventure.game.InputParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static com.project.textadventure.constants.GameConstants.GAME_INTRO;
import static com.project.textadventure.constants.GameConstants.NO_LONG;
import static com.project.textadventure.constants.GameConstants.NO_SHORT;
import static com.project.textadventure.constants.GameConstants.YES_LONG;
import static com.project.textadventure.constants.GameConstants.YES_NO_STATES;
import static com.project.textadventure.constants.GameConstants.YES_SHORT;
import static com.project.textadventure.constants.ResponseConstants.OK;

@RestController
@RequestMapping("/api/vots/game")
public class GameController {

    /**
     * Endpoint for the game. Takes in user input and returns the response.
     * @param input User's input
     * @return Response to the user's input to display to the user
     */
    @PostMapping("/gameRequest")
    public ResponseEntity<ServiceResponse<GameResponse>> executeCommand(@RequestBody Input input) {
        String inputString = input.getInput().toLowerCase();
        // Escape HTML characters in input to prevent JS injection/XSS
        inputString = StringEscapeUtils.escapeHtml4(inputString);
        final Game game = GameState.getInstance().getGame();
        // Get list of commands from input in the form Pair<verb, noun>
        final List<Pair<String, String>> commands = InputParser.parseInput(inputString);
        StringBuilder result = new StringBuilder();
        // Loop over each command and execute it
        for (final Pair<String, String> command : commands) {
            // If the game is in a state that requires a yes/no answer, handle that before user can do anything else
            if (YES_NO_STATES.contains(game.getGameStatus())) {
                result = new StringBuilder(handleYesNoConfirmation(command.getKey()));
                break;
            }

            // Execute the action on the Location or any class that extends Location
            // and append the result to the response
            result.append(game.getCurrentLocation().takeAction(command.getKey(), command.getValue()));
        }
        // Create GameResponse object. Escape the input again so it actually displays correctly
        // instead of showing up as if nothing was entered
        final GameResponse resp = new GameResponse(result.toString(), StringEscapeUtils.escapeHtml4(input.getInput()));
        final ServiceResponse<GameResponse> response = new ServiceResponse<>("success", resp);

        return new ResponseEntity<>(response, HttpStatus.OK);
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

        if (StringUtils.equals(input, YES_LONG) || StringUtils.equals(input, YES_SHORT)) {
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
        } else if (StringUtils.equals(input, NO_SHORT) || StringUtils.equals(input, NO_LONG)) {
            // Resume/start the game now that the question is answered
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