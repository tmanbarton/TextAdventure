//package com.project.textadventure.game;
//
//import com.project.textadventure.game.Locations.Dam;
//import com.project.textadventure.game.Locations.Location;
//import com.project.textadventure.game.Locations.MineEntrance;
//import com.project.textadventure.game.Locations.Shed;
//
//import java.util.*;
//
//public class Actions implements Comparator<Item> {
//    /**
//     * Loop over the current location's connecting locations. Check if the inputted direction isn't null (would only
//     * happen for locations that are not connected to other locations until sa specific action is taken) and it matches
//     * one of the connecting location's directions. Update the current location to that connecting location if so.
//     * @param direction
//     * @return the description of the location the user has moved to, or "You can't go that way." if the inputted direction isn't allowed
//     */
//    public static String move(String direction) {
//        Game game = GameState.getInstance().getGame();
//        List<ConnectingLocation> connectingLocations = game.getCurrentLocation().getConnectingLocations();
//
//        for(ConnectingLocation connectingLocation : connectingLocations) {
//            if (connectingLocation.getDirections() != null && connectingLocation.getDirections().contains(direction)) {
//                Location connection = connectingLocation.getLocation();
//                game.setCurrentLocation(connection);
//                if (connection.isVisited()) {
//                    return connection.getShortDescription() + new Actions().listLocationItems(connection.getItems());
//                }
//                connection.setVisited(true);
//                return connection.getDescription()
//                        + new Actions().listLocationItems(connection.getItems());
//            }
//        }
//        return "You can't go that way.";
//    }
//
//    private String listLocationItems(List<Item> items) {
//        StringBuilder result = new StringBuilder();
//        for(Item item : items) {
//            result.append("<br>").append(item.getLocationDescription());
//        }
//        return result.isEmpty() ? "" : "<br>" + result;
//    }
//
//    /**
//     * Split the input to get the last word, which should be the item to get. Try to get the item by name from the
//     * location, and if it's there, remove it from the location and add it to the player's inventory.
//     * @param input Everything after "get" from the input, e.g. "the key" if input was "get the key" or "jar" if
//     *              input was "get jar"
//     * @return ResponseConstants.OK if the item is at the location and the get command was entered correctly ("get [item]" or "get the [item]"),
//     * "I don't see that here." if there's no item at the location that matches the input, or some other unique response
//     * for a specific input, e.g. trying to get a tree results in a special output.
//     */
//    public static String getItem(String input) {
//        Game game = GameState.getInstance().getGame();
//        Location currentLocation = game.getCurrentLocation();
//        String[] splitInput = input.split(" ");
//        Item item = currentLocation.getLocationItemByName(splitInput[splitInput.length - 1]);
//
//        String result = "I don't see that here.";
//        // Either input is "get item" or "get the item", or trying to get nails before you've shot them off with arrow
//        if (item != null && (splitInput.length == 1 || (splitInput.length == 2 && splitInput[0].equals("the")))
//                || new Actions().gettingNailsAtMineEntrance(splitInput[splitInput.length - 1], currentLocation)) {
//            game.addItemToInventory(item);
//
//            currentLocation.removeItemFromLocation(item);
//            result = ResponseConstants.OK;
//
//        }
//        else if (input.equals("tree") && new Actions().locationHasTrees(currentLocation)) {
//            result = "You walk to the nearest tree and start pulling. After a couple minutes of this you give up.";
//        }
//        else if (splitInput[splitInput.length - 1].equals("nails")) {
//            return "Are you sure about that? The structure is very fragile and may fall apart and onto you.";
//        }
//        return result;
//    }
//
//    private boolean gettingNailsAtMineEntrance(String input, Location location) {
//        return location instanceof MineEntrance && input.equals("nails");
//    }
//
//    private boolean locationHasTrees(Location location) {
//        String name = location.getName();
//        return name.equals("ant hill") || name.equals("archery range") || name.equals("dirt road") || name.equals("ditch")
//                || name.equals("driveway") || name.equals("foot path") || name.equals("intersection")
//                || name.equals("lightning tree") || name.equals("outside log cabin") || name.equals("picnic table")
//                || name.equals("private property") || name.equals("shed") || name.equals("top of hill") || name.equals("mountain pass");
//    }
//
//    /**
//     * Split the input to get the last word, which should be the item to get. Try to get the item by name from the
//     * player's inventory, and if it's there, remove it from the inventory and add it to the location.
//     * @param input Everything after "drop" from the input, e.g. "the key" if input was "drop the key" or "magnet" if
//     *              input was "drop magnet"
//     * @return ResponseConstants.OK if the item is at the location and the get command was entered correctly ("drop [item]" or
//     * "drop the [item]"), "You're not carrying it!" if there's no item in the player's inventory that matches the
//     * input, or some other unique action happens for a specific input at a specific place, e.g. dropping magnet at
//     * the dam allows you to be able to turn the wheel
//     */
//    public static String dropItem(String input) {
//        Game game = GameState.getInstance().getGame();
//        String[] splitInput = input.split(" ");
//
//        Item item = game.getInventoryItemByName(splitInput[splitInput.length - 1]);
//        // Either input is "drop item" or "drop the item"
//        if (item != null && (splitInput.length == 1 || (splitInput.length == 2 && splitInput[0].equals("the")))) {
//            if (game.getCurrentLocation() instanceof Dam && splitInput[splitInput.length - 1].equals("magnet")) {
//                ((Dam) game.getCurrentLocation()).setMagnetDropped(true);
//            }
//            game.removeItemFromInventory(item);
//            game.getCurrentLocation().addItemToLocation(item);
//            return ResponseConstants.OK;
//        }
//        return "You're not carrying it!";
//    }
//
//    public static String look() {
//        Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();
//        return currentLocation.getDescription()
//                + new Actions().listLocationItems(currentLocation.getItems());
//    }
//
//    public static String inventory() {
//        Game game = GameState.getInstance().getGame();
//        if (game.getInventory().isEmpty()) {
//            return "You're not carrying anything!";
//        }
//        StringBuilder result = new StringBuilder();
//        for(Item item : game.getInventory()) {
//            result.append("<br>").append(item.getInventoryDescription()).append(" ");
//        }
//        return "You're carrying:" + result;
//    }
//
//    /**
//     * Check if player is trying to unlock the shed or unlock and open the shed. Verify conditions are met for unlocking
//     * shed and return correct response based on the conditions of key in inventory, current location, and locked/unlocked status.
//     * @param input Everything after "unlock" from the input, e.g. "the shed" if input was "unlock the shed", or "shed" if
//     *              input was "unlock shed", or just "" if input was "unlock"
//     * @return <ul>
//     *     <li>"The shed is now unlocked" if the player has the key and is at the shed</li>
//     *     <li>"You need a key to unlock the shed" if the player is at the shed without a key</li>
//     *     <li>"The shed is already unlocked" if the player is at the shed and has already unlocked the shed</li>
//     *     <li>"You can't unlock something that doesn't have a lock." if the player is anywhere other than the shed</li>
//     *     <li>The result of opening the shed if the player says to unlock and open the shed in one command and the conditions for unlocking are met</li>
//     * </ul>
//     */
//    public static String unlock(String input) {
//        String result = getRandomDontKnowWord();
//        Item key = GameState.getInstance().getGame().getInventoryItemByName("key");
//        Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();
//
//        if (!(currentLocation instanceof Shed)) {
//            result = "There's nothing here to unlock.";
//        }
//        else {
//            if (input.equals("shed") || input.equals("") || input.equals("the shed")) {
//                if (key == null) {
//                    result = "You need a key to unlock the shed.";
//                }
//                else if (((Shed) currentLocation).isUnlocked()) {
//                    result = "The shed is already unlocked.";
//                }
//                else {
//                    ((Shed) currentLocation).unlockShed();
//                    result = "The shed is now unlocked.";
//                }
//            }
//            else if (new Actions().unlockingAndOpeningShed(input)) {
//                if (key == null) {
//                    result = "You need a key to unlock the shed.";
//                }
//                else {
//                    result = open("");
//                }
//            }
//        }
//        return result;
//    }
//
//    private boolean unlockingAndOpeningShed(String input) {
//        return input.equals("the shed and open it") ||
//                input.equals("the shed and open") ||
//                input.equals("the shed then open it") ||
//                input.equals("the shed then open") ||
//                input.equals("and open the shed") ||
//                input.equals("and open shed") ||
//                input.equals("and open") ||
//                input.equals("then open the shed") ||
//                input.equals("then open shed") ||
//                input.equals("then open") ||
//                input.equals("shed then open shed") ||
//                input.equals("shed and open shed") ||
//                input.equals("shed and open") ||
//                input.equals("shed then open");
//    }
//
//    /**
//     * Verify conditions are met for unlocking shed and return correct response based on the conditions of key in
//     * inventory, current location, and locked/unlocked/open/unopened status.
//     * @param input Everything after "open" from the input, e.g. "the shed" if input was "open the shed", or "shed" if
//     *              input was "open shed", or just "" if input was "open"
//     * @return <ul>
//     *     <li>"The shed is now open" if the shed was unlocked and the player is at the shed</li>
//     *     <li>"You need a key to unlock the shed" if the player is at the shed without a key</li>
//     *     <li>"The shed is already open" if the player is at the shed and has already opened the shed</li>
//     *     <li>"There's nothing here to open." if the player is anywhere other than the shed</li>
//     *     <li>"(First unlocking the shed) The shed is now open." if the user has the key, but has not unlocked the shed</li>
//     * </ul>
//     */
//    public static String open(String input) {
//        String result = getRandomDontKnowWord();
//        Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();
//
//        if (input.equals("shed") || input.equals("") || input.equals("the shed")) {
//            if (!(currentLocation instanceof Shed)) {
//                result = "There's nothing here to open.";
//            }
//            else if (!((Shed) currentLocation).isUnlocked()) {
//                String unlockResult = unlock("");
//                if (!unlockResult.equals("The shed is now unlocked.")) {
//                    return unlockResult;
//                }
//                ((Shed) currentLocation).openShed();
//                result = "(First unlocking the shed) The shed is now open." + new Actions().listLocationItems(currentLocation.getItems());
//            }
//            else if (!((Shed) currentLocation).isOpen()) {
//                ((Shed) currentLocation).openShed();
//                result = "The shed is now open." + new Actions().listLocationItems(currentLocation.getItems());
//            }
//            else {
//                result = "The shed is already open.";
//            }
//        }
//        return result;
//    }
//
//    /**
//     *
//     * @param input Everything after "turn" from the input, e.g. "the wheel" if input was "turn the wheel", or "wheel" if
//     *              input was "turn wheel", or just "" if input was "turn"
//     * @return <ul>
//     *     <li>Description of the lake draining if the player is at the dam and the magnet has been dropped.</li>
//     *     <li>"The wheel is locked firmly in place." if the player is at the dam but the magnet hasn't been dropped.</li>
//     *     <li>"There's nothing to turn here." if the player us anywhere other than the dam.</li>
//     * </ul>
//     */
//    public static String turnWheel(String input) {
//        String result = "There's nothing to turn here.";
//        Location currentLocation = GameState.getInstance().getGame().getCurrentLocation();
//        if (currentLocation instanceof Dam && (input.equals("wheel") || input.equals("") || input.equals("the wheel"))) {
//            if (((Dam) currentLocation).isMagnetDropped()) {
//                ((Dam) currentLocation).turnWheel();
//                result = "Wheel hath been turned.";
//            }
//            else {
//                result = "The wheel is locked firmly in place.";
//            }
//        }
//        return result;
//    }
//
//    public static String curse(String input) {
//        return switch (input) {
//            case "fuck this", "fuck this game" -> "Maybe you need to take a break.";
//            case "fuck you" -> "Well fuck you too, that's not very nice.";
//            case "shit" -> "Poop is gross.";
//            case "damn", "damn it" -> "There's a dam around here, but no damn.";
//            case "bitch" -> ":(";
//            default -> "Hey, watch your language.";
//        };
//    }
//
//    public static String getRandomDontKnowWord() {
//        Random r = new Random();
//        int randomNum = r.nextInt(3);
//        return switch (randomNum) {
//          case 0 -> "I don't understand that.";
//          case 1 -> "What?";
//          default -> "I don't know that word.";
//        };
//    }
//
//    @Override
//    public int compare(Item item1, Item item2) {
//        return item1.getOrder() - item2.getOrder();
//    }
//}
