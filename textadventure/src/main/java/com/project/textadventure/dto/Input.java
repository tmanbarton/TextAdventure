package com.project.textadventure.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents input from the user
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Input {
    private String input;

    public String getInput() {
        return input;
    }
}
