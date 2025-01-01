package com.project.textadventure.constants;

public class ResponseConstants {
    public static final String OK = "OK.";
    public static final String PLEASE_ANSWER_QUESTION = "Please answer the question.";
    public static final String I_DONT_UNDERSTAND = "I don't understand that.";
    public static final String WHAT = "What?";
    public static final String DONT_KNOW_WORD = "I don't know that word.";
    public static final String WHAT_DO_YOU_WANT_TO_GET = "What do you want to get?";
    public static final String ALREADY_CARRYING = "You're already carrying it!";
    public static final String NOT_CARRYING = "You're not carrying anything!";
    public static final String INFO_RESPONSE = "If you want to end your adventure early say \"quit\" or \"restart\". " +
            "To see how well you're doing, say \"score\". To get full credit for a treasure, you must find the safe and deposit them in it, " +
            "though you get some points for finding it. You lose points for getting killed. You also gain points for solving " +
            "problems and getting to places that are difficult to get to. " +
            "The more you explore, the more points you will likely get. You can always say \"help\" for more assistance.";

    public static final String HELP_RESPONSE = "You can move around using cardinal directions like \"north\", \"south\", \"northwest\", \"southwest\", etc., \"up\", or \"down\", " +
            "which can also be shortened \"n\", \"s\", \"e\", \"w\", \"nw\", \"ne\", \"sw\", etc., \"u\", \"d\", \"in\", \"out\". " +
            "You can interact with items using \"get\" or \"take\" to pick them up and \"drop\" or \"throw\" to remove them from your inventory. " +
            "There are also several special ways to interact with specific items, such as filling a jar ." +
            "You can see what items are in your inventory with \"inventory\" or \"i\". " +
            "If you need to see the description of the location you're at again, say \"look\" or \"l\". " +
            "To see the score you have accumulated so far, use \"score\".";
}
