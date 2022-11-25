package com.project.textadventure.controller;

import com.project.textadventure.dto.Input;
import com.project.textadventure.dto.Response;
import com.project.textadventure.dto.ServiceResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InputController {

    List<Input> inputList = new ArrayList<>();

    @PostMapping("/userInput")
    public ResponseEntity<Object> userInput(@RequestBody Input input) {
        inputList.add(input);
        String inputString = input.getInput();

        String result = new GameController().getResponse(inputString);
        Response resp = new Response(result, inputString);
        ServiceResponse<Response> response = new ServiceResponse<>("success", resp);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/getBooks")
//    public ResponseEntity<Object> getAllBooks() {
//        ServiceResponse<List<Input>> response = new ServiceResponse<>("success", inputList);
//        return new ResponseEntity<Object>(response, HttpStatus.OK);
//    }
}