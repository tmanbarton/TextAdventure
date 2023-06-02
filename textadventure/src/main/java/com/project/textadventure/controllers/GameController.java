package com.project.textadventure.controllers;

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
        String inputString = input.getInput().toLowerCase();
        Game game = GameState.getInstance().getGame();
        List<Pair<String , String>> commands = parseInput(inputString);
        String result = "";
        if(game.getCurrentLocation() instanceof MineEntrance && ((MineEntrance) game.getCurrentLocation()).isTakingNails()) {
            result = attemptGetNailsByHand(inputString);
        }
        else {
            for (Pair<String, String> command : commands) {
                if (!game.hasPlayerMoved()) {
                    result = introduceGame(command.getKey()) + "<br>";
                    break;
                }
                Action action = ActionFactory.getActionObject(command.getKey(), command.getValue());
                if (action == null) {
                    result = "<br>" + generateRandomUnknownCommandResponse() + "<br>";
                    break;
                }
                result += "<br>" + action.takeAction(command.getKey(), command.getValue()) + "<br>";
            }
        }
        GameResponse resp = new GameResponse(result, input.getInput());
        ServiceResponse<GameResponse> response = new ServiceResponse<>("success", resp);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<Pair<String, String>> parseInput(String input) {
        String[] commands = input.split("and|then");
        ArrayList<Pair<String, String>> parsedCommands = new ArrayList<>();

        for(String command : commands) {
            // Remove any " the "s from the command
            command = command.replaceAll("\\Wthe\\W", " ");
            String[] parsedCommand = command.trim().split(" ", 2);
            String verb = parsedCommand[0];
            String noun = parsedCommand.length > 1 ? parsedCommand[1] : null;
            Pair<String, String> commandPair = new ImmutablePair<>(verb, noun);
            parsedCommands.add(commandPair);
        }
        return parsedCommands;
    }

    private String attemptGetNailsByHand(String input) {
        Game game = GameState.getInstance().getGame();
        Location currentLocation = game.getCurrentLocation();
        if(input.equals("yes") || input.equals("y")) {
            ((MineEntrance) game.getCurrentLocation()).setTakingNails(false);
            // Set nails off to true so if player comes back to mine entrance and tries to shoot arrow, the nails don't get added to the location
            ((MineEntrance) game.getCurrentLocation()).setNailsOff(true);
            // 2 ways to get to mine shaft. Find and remove both
            List<ConnectingLocation> connectingLocations = currentLocation.getConnectingLocations();
            connectingLocations.removeIf(location -> location.getLocation().getName().equals("mine shaft"));

            for (ConnectingLocation connectingLocation : currentLocation.getConnectingLocations()) {
                if (connectingLocation.getLocation().getName().equals("mine shaft")) {
                    connectingLocation.getLocation().getConnectingLocations().removeIf(
                            mineShaftLocation -> mineShaftLocation.getLocation().getName().equals("mine entrance")
                    );
                }
            }

            currentLocation.setDescription("You're at the entrance of an abandoned gold mine, a recent cave-in preventing " +
                    "entry. Piles of tailings scatter the area, leaving only one path leading away from the entrance, heading north.");
            game.die();
            return "<br>OK. I warned you. You walk up to the wooden supports and start to remove the loose nails and, " +
                    "before you can even get them out, there is a loud crack and the support you were working on " +
                    "snaps and the ceiling comes crashing down on top of you. Unfortunately being crushed by a " +
                    "mountain and old wood is very dangerous, thus this decision has cost you your life.<br>";
        }
        else if(input.equals("n") || input.equals("no")) {
            ((MineEntrance) game.getCurrentLocation()).setTakingNails(false);
            return "<br>Good choice.<br>";
        }
        else {
            return "<br>Please answer the question.<br>";
        }
    }

    private String introduceGame(String input) {
        Game game = GameState.getInstance().getGame();
        if(input.equals("yes") || input.equals("y")) {
            game.setPlayerMoved(true);
            return "<br>You're on a mountain with several scattered mining towns. It's said that some of the mines are " +
                    "still accessible, but you've also heard stories that the local miners report seeing " +
                    "tommyknockers in some of them. I will be your eyes and hands. Use commands in the form " +
                    "\"verb noun\" to guide me.<br>If you would like further information on how the game works, type " +
                    "\"info\".<br><br>" + game.getCurrentLocation().getDescription();
        }
        else if(input.equals("n") || input.equals("no")) {
            game.setPlayerMoved(true);
            return "<br>" + game.getCurrentLocation().getDescription();
        }
        else {
            return "<br>Please answer the question";
        }
    }
}