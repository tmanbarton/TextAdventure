//package com.project.textadventure.controller;
//
//import com.project.textadventure.game.GameState;
//import com.project.textadventure.game.Game;
//
//import java.util.List;
//
//import static com.project.textadventure.game.Actions.*;
//
//public class GameController_ {
//    public String getResponse(String input) {
//        return "";//findAction(input);
//    }
//
//    /**
//     * First check if the player has moved to do the game intro. Then split the input into the verb and noun and call
//     * the method in <code>Actions</code> corresponding to the inputted verb using a switch statement.
//     * @param input user's inputted command
//     * @return the game's response to the inputted command
//     */
//    public String findAction(String[] input) {
//        String verb = input[0];
//        String noun = input.length > 1 ? input[1] : null;
//
//        Game game = GameState.getInstance().getGame();
//        if (!game.hasPlayerMoved()) {
//            if (input.equals("yes") || input.equals("y")) {
//                game.setHasPlayerMoved(true);
//                return "You're on a mountain with several scattered mining towns. It's said that some of the mines are " +
//                        "still accessible, but you've also heard stories that the local miners report seeing " +
//                        "tommyknockers in some of them. I will be your eyes and hands. Use commands in the form " +
//                        "\"verb noun\" to guide me.<br>If you would like further information on how the game works, type " +
//                        "\"info\".<br><br>" + game.getCurrentLocation().getDescription();
//            }
//            else if (input.equals("n") || input.equals("no")) {
//                game.setHasPlayerMoved(true);
//                return game.getCurrentLocation().getDescription();
//            }
//            else {
//                return "Please answer the question";
//            }
//        }
//
//        if (isDirection(verb)) {
//            return move(verb);
//        }
//        String result = switch (verb) {
//            case "get"                           -> noun == null ? "What do you want to get?" : getItem(noun);
//            case "drop", "throw"                 -> noun == null ? "What do you want to drop?" : dropItem(noun);
//            case "look"                          -> look();
//            case "inventory",
//                    "inven",
//                    "invent",
//                    "invento",
//                    "inventor",
//                    "take inventory"             -> inventory();
//            case "unlock"                        -> input.length > 1 ? unlock(noun) : unlock("");
//            case "open"                          -> input.length > 1 ? open(noun) : open("");
//            case "fill"                          -> "fill";
//            case "shoot"                         -> "shoot";
//            case "turn"                          -> input.length > 1 ? turnWheel(noun) : turnWheel("");
//            case "solve"                         -> "solve";
//            case "quit"                          -> "quit";
//            case "score"                         -> "score";
//            case "info"                          -> infoString();
////            case "fuck", "shit", "damn", "bitch" -> curse(input);
//            default                              -> getRandomDontKnowWord();
//        };
//
//        return result;
//    }
//
//    private boolean isDirection(String input) {
//        List<String> directions = List.of("n", "north", "s", "south", "e", "east", "w", "west", "u", "up",
//                "d", "down", "nw", "northwest", "ne", "northeast", "sw", "southwest", "se", "southeast",
//                "in", "out", "enter", "exit");
//        return directions.contains(input);
//    }
//
//    private String infoString() {
//        return "\"quit\" will end your game early. To see your how well you're doing, say \"score\". To move around " +
//                "use cardinal directions. You can shorten the direction to one letter, i.e. \"north\" = \"n\", " +
//                "\"southeast\" = \"se\", etc. You can interact with objects with verb-noun commands. Things like \"get " +
//                "jar\" will add a jar to your inventory or \"drop the key\" will remove a key from your inventory and " +
//                "leave it at your current location. You can \"take inventory\" to see what you're carrying or " +
//                "shorten that to \"inventory\" or even \"inven\". There are various treasures you can find to gain " +
//                "points. To get the full points for a treasure you'll have to leave them it as a gift, but try not " +
//                "to let your treasures get stolen first!";
//    }
//    /*
//    * For a summary of the most recent changes to the game, say "news".
//If you want to end your adventure early, say "quit".  To suspend your
//adventure such that you can continue later, say "suspend" (or "pause"
//or "save").  To see how well you're doing, say "score".  To get full
//credit for a treasure, you must have left it safely in the building,
//though you get partial credit just for locating it.  You lose points
//for getting killed, or for quitting, though the former costs you more.
//There are also points based on how much (if any) of the cave you've
//managed to explore; in particular, there is a large bonus just for
//getting in (to distinguish the beginners from the rest of the pack),
//and there are other ways to determine whether you've been through some
//of the more harrowing sections.  If you think you've found all the
//treasures, just keep exploring for a while.  If nothing interesting
//happens, you haven't found them all yet.  If something interesting
//*DOES* happen (incidentally, there *ARE* ways to hasten things along),
//it means you're getting a bonus and have an opportunity to garner many
//more points in the Master's section.  I may occasionally offer hints
//if you seem to be having trouble.  If I do, I'll warn you in advance
//how much it will affect your score to accept the hints.  Finally, to
//save time, you may specify "brief", which tells me never to repeat the
//full description of a place unless you explicitly ask me to.*/
//}