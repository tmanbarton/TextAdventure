package com.project.textadventure.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Input {
    private String input;

    public String getInput() {
        return input;
    }
}
