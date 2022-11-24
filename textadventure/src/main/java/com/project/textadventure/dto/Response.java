package com.project.textadventure.dto;

public class Response {
    private String response;
    private String input;

    public Response(String response, String input) {
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
