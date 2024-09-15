package com.project.textadventure.game.Graph;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    @Test
    @DisplayName("Finding an Item that exists in the Location's Item list returns the specified Item.")
    void testGetLocationItemByNameSuccess() {
        Item expected = new Item(1, "", "", "name 1", 1);
        Item item1 = new Item(2, "", "", "name 2", 2);
        Item item2 = new Item(3, "", "", "name 3", 3);
        Item actual = new Location(
                "",
                "",
                List.of(expected, item1, item2),
                new ArrayList<>(),
                false,
                "")
                .getLocationItemByName(expected.getName());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Finding an Item that doesn't exist in the Location's Item list returns null.")
    void testGetLocationItemByNameFail() {
        Item item1 = new Item(2, "", "", "name 2", 1);
        Item item2 = new Item(3, "", "", "name 3", 2);
        Item item3 = new Item(1, "", "", "name 1", 3);
        Item actual = new Location(
                "",
                "",
                List.of(item1, item2),
                new ArrayList<>(),
                false,
                "")
                .getLocationItemByName(item3.getName());
        assertNull(actual);
    }

    @Test
    @DisplayName("Test creating graph by connecting locations to each other.")
    void testConnectLocations() {
        Location location1 = new Location("full desc1", "short desc1", new ArrayList<>(), new ArrayList<>(), false, "name1");
        Location location2 = new Location("full desc2", "short desc2", new ArrayList<>(), new ArrayList<>(), false, "name2");
        LocationConnection expected = new LocationConnection(List.of("east"), location2);

        location1.connectLocation(expected);
        LocationConnection actual = location1.getLocationConnections().get(0);

        assertEquals(expected, actual, "location1's connectingLocations ArrayList should contain the location that expected ConnectingLocation object.");
    }

    @Test
    @DisplayName("Test adding an item to the Location.")
    void testAddItemToLocation() {
        Location location = new Location("full desc", "short desc", new ArrayList<>(), new ArrayList<>(), false, "name");
        Item expected = new Item(1, "location desc", "inven desc", "name", 12);
        location.addItemToLocation(expected);

        Item actual = location.getItems().get(0);
        assertEquals(expected, actual, "The Location's Items ArrayList should contain the expected Item.");
    }

    @Test
    @DisplayName("Test removing an item from the Location.")
    void testRemoveItemFromLocation() {
        Location location = new Location("full desc", "short desc", new ArrayList<>(), new ArrayList<>(), false, "name");
        Item item = new Item(1, "location desc", "inven desc", "name", 23);
        location.addItemToLocation(item);
        location.removeItemFromLocation(item);

        List<Item> actual = location.getItems();
        assertTrue(actual.isEmpty(), "The Location's Items ArrayList should not contain the expected Item.");
    }
}
