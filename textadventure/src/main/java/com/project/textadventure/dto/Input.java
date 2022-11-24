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
    private String input = ":)";

    public String getInput() {
        return input;
    }
}
