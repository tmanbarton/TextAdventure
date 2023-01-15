package com.project.textadventure.game;

import com.project.textadventure.controller.Action;
import com.project.textadventure.dto.Input;
import com.project.textadventure.dto.Response;
import com.project.textadventure.dto.ServiceResponse;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.project.textadventure.game.Game.generateRandomUnknownCommandResponse;

@RestController
public class GameController {

    @PostMapping("/gameController")
    public ResponseEntity<ServiceResponse<Response>> executeCommand(@RequestBody Input input) {
        Game game = GameState.getInstance().getGame();
        List<Pair<String , String>> commands = parseInput(input.getInput());
        String result = "";
        for(Pair<String, String> command : commands) {
            if(!game.hasPlayerMoved()) {
                result = introduceGame(command.getKey()) + "<br><br>";
                break;
            }
            Action action = ActionFactory.getActionObject(command.getKey(), command.getValue());
            if(action == null) {
                result = generateRandomUnknownCommandResponse();
                break;
            }
            result += action.takeAction(command.getKey(), command.getValue());
        }

        Response resp = new Response(result, input.getInput());
        ServiceResponse<Response> response = new ServiceResponse<>("success", resp);

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

    private String introduceGame(String input) {
        Game game = GameState.getInstance().getGame();
        if(input.equals("yes") || input.equals("y")) {
            game.setPlayerMoved(true);
            return "You're on a mountain with several scattered mining towns. It's said that some of the mines are " +
                    "still accessible, but you've also heard stories that the local miners report seeing " +
                    "tommyknockers in some of them. I will be your eyes and hands. Use commands in the form " +
                    "\"verb noun\" to guide me.<br>If you would like further information on how the game works, type " +
                    "\"info\".<br><br>" + game.getCurrentLocation().getDescription();
        }
        else if(input.equals("n") || input.equals("no")) {
            game.setPlayerMoved(true);
            return game.getCurrentLocation().getDescription();
        }
        else {
            return "Please answer the question";
        }
    }

//    @GetMapping("/getBooks")
//    public ResponseEntity<Object> getAllBooks() {
//        ServiceResponse<List<Input>> response = new ServiceResponse<>("success", inputList);
//        return new ResponseEntity<Object>(response, HttpStatus.OK);
//    }
}