package com.project.textadventure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Input {
    private String input;
    private boolean gameStarted;

    public String getInput() {
        return input;
    }

    public boolean hasGameStarted() {
        return gameStarted;
    }
}
