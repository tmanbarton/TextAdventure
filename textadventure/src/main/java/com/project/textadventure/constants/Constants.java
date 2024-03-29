package com.project.textadventure.constants;

import java.util.List;

public class Constants {
//    public final String gameIntro = "You're on a mountain with several scattered mining towns. It's said that some of the mines are " +
    public static final String GAME_INTRO = "<be>You're on a mountain with several scattered mining towns. It's said that some of the mines are " +
            "still accessible, but you've also heard stories that the local miners report seeing " +
            "tommyknockers in some of them. I will be your eyes and hands. Use commands in the form " +
//            "\"verb noun\" to guide me. <br>If you would like further information on how the game works, type " +
            "\"verb noun\" to guide me. If you would like further information on how the game works, type " +
            "\"info\"<br><br>";

//    public static final String DEATH_BY_MINE_SHAFT = "OK. I warned you. You walk up to the wooden supports and start to remove the loose nails and, " +
    public static final String DEATH_BY_MINE_SHAFT = "<br>OK. I warned you. You walk up to the wooden supports and start to remove the loose nails and, " +
            "before you can even get them out, there is a loud crack and the support you were working on " +
            "snaps and the ceiling comes crashing down on top of you. Unfortunately being crushed by a " +
//            "mountain and old wood is very dangerous, thus this decision has cost you your life.<";
            "mountain and old wood is very dangerous, thus this decision has cost you your life.<br>";
    public static final String LAKE_EMPTYING = "The ground begins to rumble and you see a massive wall slowly rise from the water on the far side of the lake, blocking the flow of water from the river into the lake. There's another shudder and the water begins to recede as a huge whirl pool form near the middle of the lake. Soon the water is completely gone, revealing a town that had been submerged only a few moments ago. You can get to the town to the west, down the dam.";

    public static final String WHEEL = "wheel";

    // Directions
    public static final String N = "n";
    public static final String S = "s";
    public static final String E = "e";
    public static final String W = "w";
    public static final String NE = "ne";
    public static final String NW = "nw";
    public static final String SE = "se";
    public static final String SW = "sw";
    public static final String U = "u";
    public static final String D = "d";
    public static final String NORTH = "north";
    public static final String SOUTH = "south";
    public static final String EAST = "east";
    public static final String WEST = "west";
    public static final String NORTHEAST = "northeast";
    public static final String NORTHWEST = "northwest";
    public static final String SOUTHEAST = "southeast";
    public static final String SOUTHWEST = "southwest";
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String IN = "in";
    public static final String ENTER = "enter";
    public static final String OUT = "out";
    public static final String EXIT = "exit";

    public static final List<String> ALL_DIRECTIONS = List.of(N, S, E, W, NE, NW, SE, SW, U, D, NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST, UP, DOWN, IN, ENTER, OUT, EXIT);
}
