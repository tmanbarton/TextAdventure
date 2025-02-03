package com.project.textadventure.constants;

import com.project.textadventure.controllers.GameStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameConstants {
    public static final String GAME_INTRO = "You're on a mountain with several scattered mining towns. It's said that some of the mines are " +
            "still accessible. I will be your eyes and hands. Use cardinal directions to navigate (\"north\", \"east\", \"sw\", etc. also \"up\", " +
            "\"down\", \"in\", \"out\"). I can \"get\" and \"drop\" items, and you can use commands in the form " +
            "\"verb noun\" to interact with them in other ways in certain scenarios. If you would like further information on how the game works, type \"info\".";

    public static final String DEATH_BY_MINE_SHAFT = "OK. I warned you. You walk up to the wooden supports and start to remove the loose nails and, " +
            "before you can even get them out, there is a loud crack and the support you were working on " +
            "snaps and the ceiling comes crashing down on top of you. Unfortunately being crushed by a " +
            "mountain and old wood is very dangerous, thus this decision has cost you your life.";
    public static final String LAKE_EMPTYING = "The ground begins to rumble and you see a massive wall slowly rise from the water on the far " +
            "side of the lake, blocking the flow of water from the river. There's another shudder and the water begins to recede " +
            "as a huge whirl pool forms near the middle of the lake. Soon the lake is completely empty, revealing a town that had been submerged " +
            "only a few moments ago. You can get to the town to the west, down the dam.";
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
    public static final String NE_LONG_SPACE = "north east";
    public static final String NW_LONG = "northwest";
    public static final String NW_LONG_SPACE = "north west";
    public static final String SE_LONG = "southeast";
    public static final String SE_LONG_SPACE = "south east";
    public static final String SW_LONG = "southwest";
    public static final String SW_LONG_SPACE = "south west";
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
    public static final List<String> NE_DIRECTIONS = List.of(NE_SHORT, NE_LONG, NE_LONG_SPACE);
    public static final List<String> NW_DIRECTIONS = List.of(NW_SHORT, NW_LONG, NW_LONG_SPACE);
    public static final List<String> SE_DIRECTIONS = List.of(SE_SHORT, SE_LONG, SE_LONG_SPACE);
    public static final List<String> SW_DIRECTIONS = List.of(SW_SHORT, SW_LONG, SW_LONG_SPACE);
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
    public static final List<String> NE_AND_DOWN_DIRECTIONS = List.of(NE_SHORT, NE_LONG, NE_LONG_SPACE, DOWN_SHORT, DOWN_LONG);
    public static final List<String> NE_AND_UP_DIRECTIONS = List.of(NE_SHORT, NE_LONG, NE_LONG_SPACE, UP_LONG, UP_SHORT);
    public static final List<String> NE_AND_IN_DIRECTIONS = List.of(NE_SHORT, NE_LONG, NE_LONG_SPACE, IN, ENTER);
    public static final List<String> NE_AND_OUT_DIRECTIONS = List.of(NE_SHORT, NE_LONG, NE_LONG_SPACE, OUT, EXIT);
    public static final List<String> NW_AND_DOWN_DIRECTIONS = List.of(NW_SHORT, NW_LONG, NW_LONG_SPACE, DOWN_SHORT, DOWN_LONG);
    public static final List<String> NW_AND_UP_DIRECTIONS = List.of(NW_SHORT, NW_LONG, NW_LONG_SPACE, UP_LONG, UP_SHORT);
    public static final List<String> NW_AND_IN_DIRECTIONS = List.of(NW_SHORT, NW_LONG, NW_LONG_SPACE, IN, ENTER);
    public static final List<String> NW_AND_OUT_DIRECTIONS = List.of(NW_SHORT, NW_LONG, NW_LONG_SPACE, OUT, EXIT);
    public static final List<String> SE_AND_DOWN_DIRECTIONS = List.of(SE_SHORT, SE_LONG, SE_LONG_SPACE, DOWN_SHORT, DOWN_LONG);
    public static final List<String> SE_AND_UP_DIRECTIONS = List.of(SE_SHORT, SE_LONG, SE_LONG_SPACE, UP_LONG, UP_SHORT);
    public static final List<String> SE_AND_IN_DIRECTIONS = List.of(SE_SHORT, SE_LONG, SE_LONG_SPACE, IN, ENTER);
    public static final List<String> SE_AND_OUT_DIRECTIONS = List.of(SE_SHORT, SE_LONG, SE_LONG_SPACE, OUT, EXIT);
    public static final List<String> SW_AND_DOWN_DIRECTIONS = List.of(SW_SHORT, SW_LONG, SW_LONG_SPACE, DOWN_SHORT, DOWN_LONG);
    public static final List<String> SW_AND_UP_DIRECTIONS = List.of(SW_SHORT, SW_LONG, SW_LONG_SPACE, UP_LONG, UP_SHORT);
    public static final List<String> SW_AND_IN_DIRECTIONS = List.of(SW_SHORT, SW_LONG, SW_LONG_SPACE, IN, ENTER);
    public static final List<String> SW_AND_OUT_DIRECTIONS = List.of(SW_SHORT, SW_LONG, SW_LONG_SPACE, OUT, EXIT);

    public static final List<String> ALL_DIRECTIONS = List.of(
            NORTH_SHORT, NORTH_LONG,
            SOUTH_SHORT, SOUTH_LONG,
            EAST_SHORT, EAST_LONG,
            WEST_SHORT, WEST_LONG,
            NE_SHORT, NE_LONG, NE_LONG_SPACE,
            NW_SHORT, NW_LONG, NW_LONG_SPACE,
            SE_SHORT, SE_LONG, SE_LONG_SPACE,
            SW_SHORT, SW_LONG, SW_LONG_SPACE,
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
    // Move commands
    public static final String GO = "go";
    public static final String MOVE = "move";
    public static final String WALK = "walk";
    public static final String RUN = "run";
    // Get commands
    public static final String GET = "get";
    public static final String GRAB = "grab";
    public static final String PICK_UP = "pick up";
    public static final String TAKE = "take";
    // Drop commands
    public static final String DROP = "drop";
    public static final String THROW = "throw";
    public static final String PUT_DOWN = "put down";
    // Inventory commands
    public static final String TAKE_INVENTORY = "take inventory";
    public static final String INVENTORY_LONG = "inventory";
    public static final String INVENTORY_SHORT = "i";
    // Restart commands
    public static final String QUIT = "quit";
    public static final String RESTART = "restart";
    // General commands
    public static final String SCORE = "score";
    public static final String INFO = "info";
    public static final String HELP = "help";
    // Look commands
    public static final String LOOK_LONG = "look";
    public static final String LOOK_SHORT = "l";
    public static final String LOOK_AROUND = "look around";
    // Push commands
    public static final String PUSH = "push";
    public static final String PRESS = "press";
    public static final String POKE = "poke";
    // Other, specific commands
    public static final String FILL = "fill";
    public static final String FILL_UP = "fill up";
    public static final String OPEN = "open";
    public static final String UNLOCK = "unlock";
    public static final String SHOOT = "shoot";
    public static final String TURN = "turn";
    public static final String EAT = "eat";
    public static final String YES_LONG = "yes";
    public static final String YES_SHORT = "y";
    public static final String NO_LONG = "no";
    public static final String NO_SHORT = "n";

    public static final List<String> MOVE_COMMANDS = new ArrayList<>(List.of(GO, MOVE, WALK, RUN));
    static {
        MOVE_COMMANDS.addAll(ALL_DIRECTIONS);
    }

    // Map of possible user inputs to commands
    public static final Map<List<String>, String> MOVE_COMMAND_MAP = Map.of(MOVE_COMMANDS, MOVE);
    public static final Map<List<String>, String> GET_COMMAND_MAP = Map.of(List.of(GET, TAKE, GRAB, PICK_UP), GET);
    public static final Map<List<String>, String> DROP_COMMAND_MAP = Map.of(List.of(DROP, THROW, PUT_DOWN), DROP);
    public static final Map<List<String>, String> INVENTORY_COMMAND_MAP = Map.of(List.of(TAKE_INVENTORY, INVENTORY_LONG, INVENTORY_SHORT), INVENTORY_LONG);
    public static final Map<List<String>, String> RESTART_COMMAND_MAP = Map.of(List.of(QUIT, RESTART), RESTART);
    public static final Map<List<String>, String> HELP_COMMAND_MAP = Map.of(List.of(HELP), HELP);
    public static final Map<List<String>, String> INFO_COMMAND_MAP = Map.of(List.of(INFO), INFO);
    public static final Map<List<String>, String> SCORE_COMMAND_MAP = Map.of(List.of(SCORE), SCORE);
    public static final Map<List<String>, String> LOOK_COMMAND_MAP = Map.of(List.of(LOOK_LONG, LOOK_SHORT, LOOK_AROUND), LOOK_LONG);
    public static final Map<List<String>, String> OPEN_COMMAND_MAP = Map.of(List.of(OPEN), OPEN);
    public static final Map<List<String>, String> UNLOCK_COMMAND_MAP = Map.of(List.of(UNLOCK), UNLOCK);
    public static final Map<List<String>, String> SHOOT_COMMAND_MAP = Map.of(List.of(SHOOT), SHOOT);
    public static final Map<List<String>, String> TURN_COMMAND_MAP = Map.of(List.of(TURN), TURN);
    public static final Map<List<String>, String> EAT_COMMAND_MAP = Map.of(List.of(EAT), EAT);
    public static final Map<List<String>, String> PUSH_COMMAND_MAP = Map.of(List.of(PUSH, PRESS, POKE), PUSH);
    public static final Map<List<String>, String> FILL_COMMAND_MAP = Map.of(List.of(FILL, FILL_UP), FILL);
    public static final Map<List<String>, String> YES_COMMAND_MAP = Map.of(List.of(YES_LONG, YES_SHORT), YES_LONG);
    public static final Map<List<String>, String> NO_COMMAND_MAP = Map.of(List.of(NO_LONG, NO_SHORT), NO_LONG);

    public static final List<String> ALL_COMMANDS = new ArrayList<>(List.of(
            GO, MOVE, WALK, RUN, GET, GRAB, PICK_UP, TAKE, DROP, THROW, PUT_DOWN, TAKE_INVENTORY, INVENTORY_LONG, INVENTORY_SHORT, QUIT, RESTART,
            SCORE, INFO, HELP, LOOK_LONG, LOOK_SHORT, LOOK_AROUND, PUSH, PRESS, POKE, FILL, FILL_UP, OPEN, UNLOCK, SHOOT, TURN, EAT, YES_LONG, YES_SHORT, NO_LONG, NO_SHORT));
    static {
        ALL_COMMANDS.addAll(ALL_DIRECTIONS);
    }
    public static final List<Map<List<String>, String>> ALL_COMMAND_MAPS = List.of(
            MOVE_COMMAND_MAP, GET_COMMAND_MAP, DROP_COMMAND_MAP, INVENTORY_COMMAND_MAP, RESTART_COMMAND_MAP, LOOK_COMMAND_MAP,
            HELP_COMMAND_MAP, INFO_COMMAND_MAP, SCORE_COMMAND_MAP, OPEN_COMMAND_MAP, UNLOCK_COMMAND_MAP, SHOOT_COMMAND_MAP,
            TURN_COMMAND_MAP, EAT_COMMAND_MAP, PUSH_COMMAND_MAP, FILL_COMMAND_MAP, YES_COMMAND_MAP, NO_COMMAND_MAP);

    public static final List<GameStatus> YES_NO_STATES = List.of(GameStatus.NEW, GameStatus.QUITTING, GameStatus.GETTING_NAILS);
}
