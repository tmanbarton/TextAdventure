package com.project.textadventure.game;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputParserTests {
    @Test
    void parseInput_GetCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("take key");
        assertEquals(1, commands.size());
        assertEquals("get", commands.get(0).getKey());
        assertEquals("key", commands.get(0).getValue());
    }

    @Test
    void parseInput_DropCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("drop the magnet");
        assertEquals(1, commands.size());
        assertEquals("drop", commands.get(0).getKey());
        assertEquals("magnet", commands.get(0).getValue());
    }

    @Test
    void parseInput_MoveCommandNoVerb() {
        final List<Pair<String, String>> commands = InputParser.parseInput("east");
        assertEquals(1, commands.size());
        assertEquals("move", commands.get(0).getKey());
        assertEquals("east", commands.get(0).getValue());
    }

    @Test
    void parseInput_MoveCommandWithVerb() {
        final List<Pair<String, String>> commands = InputParser.parseInput("go west");
        assertEquals(1, commands.size());
        assertEquals("move", commands.get(0).getKey());
        assertEquals("west", commands.get(0).getValue());
    }

    @Test
    void parseInput_LookCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("look around");
        assertEquals(1, commands.size());
        assertEquals("look", commands.get(0).getKey());
        assertTrue(commands.get(0).getValue().isEmpty());
    }

    @Test
    void parseInput_InventoryCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("take inventory");
        assertEquals(1, commands.size());
        assertEquals("inventory", commands.get(0).getKey());
        assertTrue(commands.get(0).getValue().isEmpty());
    }

    @Test
    void parseInput_QuitCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("restart");
        assertEquals(1, commands.size());
        assertEquals("restart", commands.get(0).getKey());
        assertTrue(commands.get(0).getValue().isEmpty());
    }

    @Test
    void parseInput_HelpCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("help");
        assertEquals(1, commands.size());
        assertEquals("help", commands.get(0).getKey());
        assertTrue(commands.get(0).getValue().isEmpty());
    }

    @Test
    void parseInput_PushCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("press the button");
        assertEquals(1, commands.size());
        assertEquals("push", commands.get(0).getKey());
        assertEquals("button", commands.get(0).getValue());
    }

    @Test
    void parseInput_FillCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("fill up the jar");
        assertEquals(1, commands.size());
        assertEquals("fill", commands.get(0).getKey());
        assertEquals("jar", commands.get(0).getValue());
    }

    @Test
    void parseInput_OpenCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("open the shed");
        assertEquals(1, commands.size());
        assertEquals("open", commands.get(0).getKey());
        assertEquals("shed", commands.get(0).getValue());
    }

    @Test
    void parseInput_UnlockCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("unlock the shed");
        assertEquals(1, commands.size());
        assertEquals("unlock", commands.get(0).getKey());
        assertEquals("shed", commands.get(0).getValue());
    }

    @Test
    void parseInput_ShootCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("shoot arrow");
        assertEquals(1, commands.size());
        assertEquals("shoot", commands.get(0).getKey());
        assertEquals("arrow", commands.get(0).getValue());
    }

    @Test
    void parseInput_TurnCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("turn");
        assertEquals(1, commands.size());
        assertEquals("turn", commands.get(0).getKey());
        assertTrue(commands.get(0).getValue().isEmpty());
    }

    @Test
    void parseInput_EatCommand() {
        final List<Pair<String, String>> commands = InputParser.parseInput("eat the pie");
        assertEquals(1, commands.size());
        assertEquals("eat", commands.get(0).getKey());
        assertEquals("pie", commands.get(0).getValue());
    }

    @Test
    void parseInput_MultipleCommands() {
        List<Pair<String, String>> commands = InputParser.parseInput("pick up the key and then unlock the shed then shoot the arrow and go west then u");
        assertEquals(5, commands.size());
        assertEquals("get", commands.get(0).getKey());
        assertEquals("key", commands.get(0).getValue());
        assertEquals("unlock", commands.get(1).getKey());
        assertEquals("shed", commands.get(1).getValue());
        assertEquals("shoot", commands.get(2).getKey());
        assertEquals("arrow", commands.get(2).getValue());
        assertEquals("move", commands.get(3).getKey());
        assertEquals("west", commands.get(3).getValue());
        assertEquals("move", commands.get(4).getKey());
        assertEquals("u", commands.get(4).getValue());
    }
}
