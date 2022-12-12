package com.project.textadventure.game;

import com.project.textadventure.game.Locations.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.project.textadventure.game.Actions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActionsTest {
//    Player player;
    
//    @BeforeEach
//    void setup() {
//        player = GameState.getInstance().createGame();
//    }
    
    @Test
    @DisplayName("Test moving to a location that hasn't been visited")
    void testValidMoveNotVisitedLocation() {
        Player player = GameState.getInstance().getGame();
        final Location currentLocation = new Location("full desc1", "short desc1", new ArrayList<>(), new ArrayList<>(), true, "name1");
        final Location destinationLocation = new Location("full desc2", "short desc2", new ArrayList<>(), new ArrayList<>(), false, "name2");
        final ConnectingLocation connectingLocation = new ConnectingLocation(List.of("n", "north"), destinationLocation);
        currentLocation.connectLocation(connectingLocation);

        player.setCurrentLocation(currentLocation);

        final String newLocationDescription = move("n");

        assertEquals(destinationLocation.getDescription(), newLocationDescription, "The player's updated current position should equal the destinationLocation's description.");
    }

    @Test
    @DisplayName("Test moving to a location that has already been visited")
    void testValidMoveVisitedLocation() {
        Player player = GameState.getInstance().getGame();
        final Location currentLocation = new Location("full desc1", "short desc1", new ArrayList<>(), new ArrayList<>(), true, "name1");
        final Location destinationLocation = new Location("full desc2", "short desc2", new ArrayList<>(), new ArrayList<>(), true, "name2");
        final ConnectingLocation connectingLocation = new ConnectingLocation(List.of("n", "north"), destinationLocation);
        currentLocation.connectLocation(connectingLocation);

        player.setCurrentLocation(currentLocation);

        final String newLocationDescription = move("n");

        assertEquals("short desc2", newLocationDescription, "The player's updated current position should equal the destinationLocation's description.");
    }

    @Test
    @DisplayName("Trying to move an invalid direction.")
    void testInvalidMove() {
        Player player = GameState.getInstance().getGame();
        final Location currentLocation = new Location("full desc1", "short desc1", new ArrayList<>(), new ArrayList<>(), true, "name1");
        final Location destinationLocation = new Location("full desc2", "short desc2", new ArrayList<>(), new ArrayList<>(), false, "name2");
        final ConnectingLocation connectingLocation = new ConnectingLocation(List.of("n", "north"), destinationLocation);
        currentLocation.connectLocation(connectingLocation);

        player.setCurrentLocation(currentLocation);

        final String newLocationDescription = move("s");

        assertEquals("You can't go that way.", newLocationDescription, "The player's updated current position should equal the destinationLocation's description.");
    }

    @Test
    @DisplayName("The string that is returned should have the list of items at the location.")
    void moveToLocationWithItems() {
        Player player = GameState.getInstance().getGame();
        final Item item1 = new Item(1, "test location description 1", "", "name1");
        final Item item2 = new Item(2, "test location description 2", "", "name2");
        final Location currentLocation = new Location("", "", new ArrayList<>(), new ArrayList<>(), true, "");
        final Location destinationLocation = new Location("", "", List.of(item1, item2), new ArrayList<>(), false, "");
        final ConnectingLocation connectingLocation = new ConnectingLocation(List.of("n"), destinationLocation);
        currentLocation.connectLocation(connectingLocation);

        player.setCurrentLocation(currentLocation);

        final String newLocationDescription = move("n");

        assertEquals("<br><br>test location description 1<br>test location description 2", newLocationDescription, "The description of the player's updated current position should contain a list of the items at that location.");
    }

    @Test
    @DisplayName("Get an item that is at the current location.")
    void testGetItemSuccess() {
        Player player = GameState.getInstance().getGame();
        final Item item = new Item(1, "", "", "item");
        
        final List<Item> items = new ArrayList<>();
        items.add(item);
        player.setCurrentLocation(new Location("", "", items, new ArrayList<>(), true, ""));
        final String result = getItem("item");
        assertEquals("OK", result, "Response from successfully getting the item should by 'OK'.");
        assertFalse(player.getInventory().isEmpty(), "The item should have been added to the inventory.");
    }

    @Test
    @DisplayName("Try to get an item that is not at the current location.")
    void testGetItemFail() {
        Player player = GameState.getInstance().getGame();
        final Item item = new Item(1, "", "", "item");
        
        final List<Item> items = new ArrayList<>();
        items.add(item);
        player.setCurrentLocation(new Location("", "", items, new ArrayList<>(), true, ""));
        final String result = getItem("invalid item");
        assertEquals("I don't see that here.", result, "Response from trying to get an item that isn't at the location should be 'I don't see that here.'.");
        assertTrue(player.getInventory().isEmpty(), "The item should not have been added to the inventory.");
    }

    @Test
    @DisplayName("Get tree corner case when player is at a location with trees.")
    void testGetTreeValid() {
        Player player = GameState.getInstance().getGame();
        player.setCurrentLocation(new Location("", "", new ArrayList<>(), new ArrayList<>(), true, "treeless location"));
        final String result = getItem("tree");
        assertEquals("I don't see that here.", result, "Response from trying to get an item that isn't at the location should be 'I don't see that here.'.");
    }

    @Test
    @DisplayName("Get tree corner case when player is at a location with no trees.")
    void testGetTreeInvalid() {
        Player player = GameState.getInstance().getGame();
        player.setCurrentLocation(new Location("", "", new ArrayList<>(), new ArrayList<>(), true, "driveway"));
        final String result = getItem("tree");
        assertEquals("You walk to the nearest tree and start pulling. After a couple minutes of this you give up.", result, "If the player is at a location that has trees, the special response for getting a tree should be returned.");
    }

//    @Test
//    @DisplayName("Drop an item that is in the player's inventory.")
//    void testDropItemSuccess() {
//        final Item item = new Item(1, "", "", "item");
//        Player player = GameState.getInstance().getGame();
//        player.addItemToInventory(item);
//        Location currentLocation = new Location("", "", new ArrayList<>(), new ArrayList<>(), true, "");
//        player.setCurrentLocation(currentLocation);
//        String result = dropItem("item");
//        assertEquals("OK", result, "Response from successfully getting the item should by 'OK'.");
//        assertTrue(player.getInventory().isEmpty(), "The item should have been added to the inventory.");
//        assertFalse(currentLocation.getItems().isEmpty(), "The item should have been moved from the player's inventory to the location.");
//    }
//
//    @Test
//    @DisplayName("Try to drop an item that isn't in the player's inventory.")
//    void testDropItemFail() {
//        final Item item = new Item(1, "", "", "item");
//        Player player = GameState.getInstance().getGame();
//        player.addItemToInventory(item);
//        Location currentLocation = new Location("", "", new ArrayList<>(), new ArrayList<>(), true, "");
//        player.setCurrentLocation(currentLocation);
//        String result = dropItem("item");
//        assertEquals("OK", result, "Response from successfully getting the item should by 'OK'.");
//        assertFalse(player.getInventory().isEmpty(), "The item should have been added to the inventory.");
//        assertTrue(currentLocation.getItems().isEmpty(), "The item should have been moved from the player's inventory to the location.");
//    }


    // Drop magnet at dam
    // Drop magnet not at dam

    @Test
    @DisplayName("Look command")
    void testLook() {
        
    }

    // inventory
    // unlock not at shed
    // unlock at shed not unlocked no key
    // unlock at shed not unlocked with key
    // unlock shed already unlocked
    // unlock and open shed
    // open not at shed
    // open at shed not unlocked
    // open at shed already open
    // turn not at dam
    // turn at dam no magnet dropped
    // turn at dam magnet dropped
}
