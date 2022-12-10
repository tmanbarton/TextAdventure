package com.project.textadventure.game.Locations;

import com.project.textadventure.game.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LocationTest {
    @Test
    @DisplayName("Finding an Item that exists in the Location's Item list returns the specified Item.")
    void testGetLocationItemByNameSuccess() {
        Item expected = new Item(1, "", "", "name 1");
        Item item1 = new Item(2, "", "", "name 2");
        Item item2 = new Item(3, "", "", "name 3");
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
        Item item1 = new Item(2, "", "", "name 2");
        Item item2 = new Item(3, "", "", "name 3");
        Item item3 = new Item(1, "", "", "name 1");
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


}
