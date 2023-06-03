package com.project.textadventure.dto;

public class GameResponse {
    private final String response;
    private final String input;

    public GameResponse(final String response, final String input) {
        this.response = response;
        this.input = input;
    }

    public String getResponse() {
        return response;
    }

    public String getInput() {
        return input;
    }
}
