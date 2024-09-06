package com.project.textadventure.controllers;

import com.project.textadventure.constants.GameConstants;
import com.project.textadventure.constants.LocationDescriptions;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.constants.ResponseConstants;
import com.project.textadventure.dto.Input;
import com.project.textadventure.dto.GameResponse;
import com.project.textadventure.dto.ServiceResponse;

import com.project.textadventure.game.ActionFactory;
import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import com.project.textadventure.game.Locations.Location;
import com.project.textadventure.game.Locations.MineEntrance;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

@RestController
@RequestMapping("/api/vots/game")
public class GameController {

    @PostMapping("/gameRequest")
    public ResponseEntity<ServiceResponse<GameResponse>> executeCommand(@RequestBody Input input) {
        final String inputString = input.getInput().toLowerCase();
        final Game game = GameState.getInstance().getGame();
        final List<Pair<String , String>> commands = parseInput(inputString);
        String result = "";
        if (game.getCurrentLocation() instanceof MineEntrance && ((MineEntrance) game.getCurrentLocation()).isTakingNails()) {
            result = attemptGetNailsByHand(inputString);
        }
        else {
            for (final Pair<String, String> command : commands) {
                if (!game.hasPlayerMoved()) {
                    result = introduceGame(command.getKey());
                    break;
                }
                final Action action = ActionFactory.getActionObject(command.getKey(), command.getValue());
                if (action == null) {
                    result = generateRandomUnknownCommandResponse();
                    break;
                }
                result += action.takeAction(command.getKey(), command.getValue());
            }
        }
        final GameResponse resp = new GameResponse(result, input.getInput());
        final ServiceResponse<GameResponse> response = new ServiceResponse<>("success", resp);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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

    private String attemptGetNailsByHand(final String input) {
        final Game game = GameState.getInstance().getGame();
        final Location currentLocation = game.getCurrentLocation();
        if (input.equals("yes") || input.equals("y")) {
            ((MineEntrance) game.getCurrentLocation()).setTakingNails(false);
            // Set nails off to true so if player comes back to mine entrance and tries to shoot arrow, the nails don't get added to the location
            ((MineEntrance) game.getCurrentLocation()).setNailsOff(true);
            // 2 ways to get to mine shaft. Find and remove both
            final List<ConnectingLocation> connectingLocations = currentLocation.getConnectingLocations();
            connectingLocations.removeIf(location -> location.getLocation().getName().equals(LocationNames.MINE_SHAFT));

            for (final ConnectingLocation connectingLocation : currentLocation.getConnectingLocations()) {
                if (connectingLocation.getLocation().getName().equals(LocationNames.MINE_SHAFT)) {
                    connectingLocation.getLocation().getConnectingLocations().removeIf(
                            mineShaftLocation -> mineShaftLocation.getLocation().getName().equals(LocationNames.MINE_ENTRANCE)
                    );
                }
            }

            currentLocation.setDescription(LocationDescriptions.MINE_ENTRANCE_RECENT_CAVE_IN);
            game.die();
            return GameConstants.DEATH_BY_MINE_SHAFT;
        }
        else if (input.equals("n") || input.equals("no")) {
            ((MineEntrance) game.getCurrentLocation()).setTakingNails(false);
            return "Good choice.";
        }
        else {
            return ResponseConstants.PLEASE_ANSWER_QUESTION;
        }
    }

    private String introduceGame(final String input) {
        final Game game = GameState.getInstance().getGame();
        if (input.equals("yes") || input.equals("y")) {
            game.setPlayerMoved(true);
            return GameConstants.GAME_INTRO + game.getCurrentLocation().getDescription();
        }
        else if (input.equals("n") || input.equals("no")) {
            game.setPlayerMoved(true);
            return game.getCurrentLocation().getDescription();
        }
        else {
            return ResponseConstants.PLEASE_ANSWER_QUESTION;
        }
    }
}