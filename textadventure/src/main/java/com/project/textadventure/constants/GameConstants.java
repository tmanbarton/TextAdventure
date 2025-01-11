package com.project.textadventure.constants;

import java.util.List;

public class GameConstants {
    public static final String GAME_INTRO = "You're on a mountain with several scattered mining towns. It's said that some of the mines are " +
            "still accessible. I will be your eyes and hands. Use cardinal directions to navigate (\"north\", \"east\", \"sw\", etc. also \"up\", " +
            "\"down\", \"in\", \"out\"). I can \"get\" and \"drop\" items, and you can use commands in the form " +
            "\"verb noun\" to interact with them in other ways in certain scenarios. If you would like further information on how the game works, type \"info\".";

    public static final String DEATH_BY_MINE_SHAFT = "OK. I warned you. You walk up to the wooden supports and start to remove the loose nails and, " +
            "before you can even get them out, there is a loud crack and the support you were working on " +
            "snaps and the ceiling comes crashing down on top of you. Unfortunately being crushed by a " +
            "mountain and old wood is very dangerous, thus this decision has cost you your life.";
    public static final String LAKE_EMPTYING = "The ground begins to rumble and you see a massive wall slowly rise from the water on the far side of the lake, blocking the flow of water from the river into the lake. There's another shudder and the water begins to recede as a huge whirl pool forms near the middle of the lake. Soon the lake is completely empty, revealing a town that had been submerged only a few moments ago. You can get to the town to the west, down the dam.";
    public static final String BAD_DIRECTION = "You can't go that way.";

    // Interactive elements that aren't pick-upable
    public static final String WHEEL = "wheel";
    public static final String BUTTON = "button";

    // Directions
    public static final String NORTH_SHORT = "n";
    public static final String SOUTH_SHORT = "s";
    public static final String EAST_SHORT = "e";
    public static final String WEST_SHORT = "w";
    public static final String NE_SHORT = "ne";
    public static final String NW_SHORT = "nw";
    public static final String SE_SHORT = "se";
    public static final String SW_SHORT = "sw";
    public static final String UP_SHORT = "u";
    public static final String DOWN_SHORT = "d";
    public static final String NORTH_LONG = "north";
    public static final String SOUTH_LONG = "south";
    public static final String EAST_LONG = "east";
    public static final String WEST_LONG = "west";
    public static final String NE_LONG = "northeast";
    public static final String NW_LONG = "northwest";
    public static final String SE_LONG = "southeast";
    public static final String SW_LONG = "southwest";
    public static final String UP_LONG = "up";
    public static final String DOWN_LONG = "down";
    public static final String IN = "in";
    public static final String ENTER = "enter";
    public static final String OUT = "out";
    public static final String EXIT = "exit";
    public static final List<String> WEST_DIRECTIONS = List.of(WEST_SHORT, WEST_LONG);
    public static final List<String> EAST_DIRECTIONS = List.of(EAST_SHORT, EAST_LONG);
    public static final List<String> NORTH_DIRECTIONS = List.of(NORTH_SHORT, NORTH_LONG);
    public static final List<String> SOUTH_DIRECTIONS = List.of(SOUTH_SHORT, SOUTH_LONG);
    public static final List<String> NE_DIRECTIONS = List.of(NE_SHORT, NE_LONG);
    public static final List<String> NW_DIRECTIONS = List.of(NW_SHORT, NW_LONG);
    public static final List<String> SE_DIRECTIONS = List.of(SE_SHORT, SE_LONG);
    public static final List<String> SW_DIRECTIONS = List.of(SW_SHORT, SW_LONG);
    public static final List<String> UP_DIRECTIONS = List.of(UP_SHORT, UP_LONG);
    public static final List<String> DOWN_DIRECTIONS = List.of(DOWN_SHORT, DOWN_LONG);
    public static final List<String> IN_DIRECTIONS = List.of(IN, ENTER);
    public static final List<String> OUT_DIRECTIONS = List.of(OUT, EXIT);

    public static final List<String> WEST_AND_DOWN_DIRECTIONS = List.of(WEST_SHORT, WEST_LONG, DOWN_SHORT, DOWN_LONG);
    public static final List<String> WEST_AND_UP_DIRECTIONS = List.of(WEST_SHORT, WEST_LONG, UP_LONG, UP_SHORT);
    public static final List<String> WEST_AND_IN_DIRECTIONS = List.of(WEST_SHORT, WEST_LONG, IN, ENTER);
    public static final List<String> WEST_AND_OUT_DIRECTIONS = List.of(WEST_SHORT, WEST_LONG, OUT, EXIT);
    public static final List<String> EAST_AND_DOWN_DIRECTIONS = List.of(EAST_SHORT, EAST_LONG, DOWN_SHORT, DOWN_LONG);
    public static final List<String> EAST_AND_UP_DIRECTIONS = List.of(EAST_SHORT, EAST_LONG, UP_LONG, UP_SHORT);
    public static final List<String> EAST_AND_IN_DIRECTIONS = List.of(EAST_SHORT, EAST_LONG, IN, ENTER);
    public static final List<String> EAST_AND_OUT_DIRECTIONS = List.of(EAST_SHORT, EAST_LONG, OUT, EXIT);
    public static final List<String> NORTH_AND_DOWN_DIRECTIONS = List.of(NORTH_SHORT, NORTH_LONG, DOWN_SHORT, DOWN_LONG);
    public static final List<String> NORTH_AND_UP_DIRECTIONS = List.of(NORTH_SHORT, NORTH_LONG, UP_LONG, UP_SHORT);
    public static final List<String> NORTH_AND_IN_DIRECTIONS = List.of(NORTH_SHORT, NORTH_LONG, IN, ENTER);
    public static final List<String> NORTH_AND_OUT_DIRECTIONS = List.of(NORTH_SHORT, NORTH_LONG, OUT, EXIT);
    public static final List<String> SOUTH_AND_DOWN_DIRECTIONS = List.of(SOUTH_SHORT, SOUTH_LONG, DOWN_SHORT, DOWN_LONG);
    public static final List<String> SOUTH_AND_UP_DIRECTIONS = List.of(SOUTH_SHORT, SOUTH_LONG, UP_LONG, UP_SHORT);
    public static final List<String> SOUTH_AND_IN_DIRECTIONS = List.of(SOUTH_SHORT, SOUTH_LONG, IN, ENTER);
    public static final List<String> SOUTH_AND_OUT_DIRECTIONS = List.of(SOUTH_SHORT, SOUTH_LONG, OUT, EXIT);
    public static final List<String> NE_AND_DOWN_DIRECTIONS = List.of(NE_SHORT, NE_LONG, DOWN_SHORT, DOWN_LONG);
    public static final List<String> NE_AND_UP_DIRECTIONS = List.of(NE_SHORT, NE_LONG, UP_LONG, UP_SHORT);
    public static final List<String> NE_AND_IN_DIRECTIONS = List.of(NE_SHORT, NE_LONG, IN, ENTER);
    public static final List<String> NE_AND_OUT_DIRECTIONS = List.of(NE_SHORT, NE_LONG, OUT, EXIT);
    public static final List<String> NW_AND_DOWN_DIRECTIONS = List.of(NW_SHORT, NW_LONG, DOWN_SHORT, DOWN_LONG);
    public static final List<String> NW_AND_UP_DIRECTIONS = List.of(NW_SHORT, NW_LONG, UP_LONG, UP_SHORT);
    public static final List<String> NW_AND_IN_DIRECTIONS = List.of(NW_SHORT, NW_LONG, IN, ENTER);
    public static final List<String> NW_AND_OUT_DIRECTIONS = List.of(NW_SHORT, NW_LONG, OUT, EXIT);
    public static final List<String> SE_AND_DOWN_DIRECTIONS = List.of(SE_SHORT, SE_LONG, DOWN_SHORT, DOWN_LONG);
    public static final List<String> SE_AND_UP_DIRECTIONS = List.of(SE_SHORT, SE_LONG, UP_LONG, UP_SHORT);
    public static final List<String> SE_AND_IN_DIRECTIONS = List.of(SE_SHORT, SE_LONG, IN, ENTER);
    public static final List<String> SE_AND_OUT_DIRECTIONS = List.of(SE_SHORT, SE_LONG, OUT, EXIT);
    public static final List<String> SW_AND_DOWN_DIRECTIONS = List.of(SW_SHORT, SW_LONG, DOWN_SHORT, DOWN_LONG);
    public static final List<String> SW_AND_UP_DIRECTIONS = List.of(SW_SHORT, SW_LONG, UP_LONG, UP_SHORT);
    public static final List<String> SW_AND_IN_DIRECTIONS = List.of(SW_SHORT, SW_LONG, IN, ENTER);
    public static final List<String> SW_AND_OUT_DIRECTIONS = List.of(SW_SHORT, SW_LONG, OUT, EXIT);

    public static final List<String> ALL_DIRECTIONS = List.of(
            NORTH_SHORT, NORTH_LONG,
            SOUTH_SHORT, SOUTH_LONG,
            EAST_SHORT, EAST_LONG,
            WEST_SHORT, WEST_LONG,
            NE_SHORT, NE_LONG,
            NW_SHORT, NW_LONG,
            SE_SHORT, SE_LONG,
            SW_SHORT, SW_LONG,
            UP_SHORT, UP_LONG,
            DOWN_SHORT, DOWN_LONG,
            IN, ENTER, OUT, EXIT);

    public static final List<String> TREE_LOCATIONS = List.of(
            LocationNames.ANT_HILL,
            LocationNames.ARCHERY_RANGE,
            LocationNames.DAM,
            LocationNames.DIRT_ROAD,
            LocationNames.DITCH,
            LocationNames.DRIVEWAY,
            LocationNames.WEST_END_SIDE_STREET,
            LocationNames.FOOT_PATH,
            LocationNames.INTERSECTION,
            LocationNames.LAKE,
            LocationNames.LIGHTNING_TREE,
            LocationNames.MOUNTAIN_PASS,
            LocationNames.OUTSIDE_LOG_CABIN,
            LocationNames.PICNIC_TABLE,
            LocationNames.PRIVATE_PROPERTY,
            LocationNames.SHED,
            LocationNames.TOP_OF_HILL);

    // Commands
    public static final String GET = "get";
    public static final String GO = "go";
    public static final String TAKE = "take";
    public static final String DROP = "drop";
    public static final String THROW = "throw";
    public static final String FILL = "fill";
    public static final String INVENTORY_LONG = "inventory";
    public static final String INVENTORY_MEDIUM = "inven";
    public static final String INVENTORY_SHORT = "i";
    public static final String QUIT = "quit";
    public static final String RESTART = "restart";
    public static final String SCORE = "score";
    public static final String INFO = "info";
    public static final String HELP = "help";
    public static final String LOOK_LONG = "look";
    public static final String LOOK_SHORT = "l";
    public static final String OPEN = "open";
    public static final String UNLOCK = "unlock";
    public static final String SHOOT = "shoot";
    public static final String TURN = "turn";
    public static final String EAT = "eat";
    public static final String PUSH = "push";
    public static final String PRESS = "press";
}
