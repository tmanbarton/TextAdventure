package com.project.textadventure.game.Graph;

import com.project.textadventure.game.Game;
import com.project.textadventure.game.GameState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.project.textadventure.game.GameUtils.addItemToLocation;
import static com.project.textadventure.game.GameUtils.removeItemFromLocation;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.mockito.MockedStatic;

public class LocationTest {
    @Test
    @DisplayName("Finding an Item that exists in the Location's Item list returns the specified Item.")
    void testGetLocationItemByNameSuccess() {
        final Item expected = new Item(1, "", "", "name 1", 1, 2);
        final Item item1 = new Item(2, "", "", "name 2", 2, 1);
        final Item item2 = new Item(3, "", "", "name 3", 3, 3);
//        Item actual = new Location(
//                "",
//                "",
//                List.of(expected, item1, item2),
//                new ArrayList<>(),
//                false,
//                "");
//                getLocationItemByName(expected.getName());
//        assertEquals(expected, actual);
        // todo fix that ^^
    }

    @Test
    @DisplayName("Finding an Item that doesn't exist in the Location's Item list returns null.")
    void testGetLocationItemByNameFail() {
        final Item item1 = new Item(2, "", "", "name 2", 1, 1);
        final Item item2 = new Item(3, "", "", "name 3", 2, 2);
        final Item item3 = new Item(1, "", "", "name 1", 3, 3);
//        Item actual = new Location(
//                "",
//                "",
//                List.of(item1, item2),
//                new ArrayList<>(),
//                false,
//                "")
//                .getLocationItemByName(item3.getName());
//        assertNull(actual);
        // todo fix that ^^
    }

    @Test
    @DisplayName("Test creating graph by connecting locations to each other.")
    void testConnectLocations() {
        final Location location1 = new Location("full desc1", "short desc1", new ArrayList<>(), new ArrayList<>(), false, "name1");
        final Location location2 = new Location("full desc2", "short desc2", new ArrayList<>(), new ArrayList<>(), false, "name2");
        final LocationConnection expected = new LocationConnection(List.of("east"), location2);

        location1.connectLocation(expected);
        final LocationConnection actual = location1.getLocationConnections().get(0);

        assertEquals(expected, actual, "location1's connectingLocations ArrayList should contain the location that expected ConnectingLocation object.");
    }

    @Test
    void testAddItemToLocation() {
        final Location location = new Location("full desc", "short desc", new ArrayList<>(), new ArrayList<>(), false, "name");

        final Game game = new Game();

        try (final MockedStatic<GameState> mock = mockStatic(GameState.class)) {
            final GameState mockGameState = mock(GameState.class);
            mock.when(GameState::getInstance).thenReturn(mockGameState);
            when(mockGameState.getGame()).thenReturn(game);
            when(game.getCurrentLocation()).thenReturn(location);
        }

        final Game mockGame = mock(Game.class);
        when(mockGame.getCurrentLocation()).thenReturn(location);

        final Item item = new Item(1, "location desc", "inven desc", "name", 12, 3);
        assertEquals(0, location.getItems().size(), "No Items should be at the location.");
        addItemToLocation(item);

        assertEquals(1, location.getItems().size(), "The Location's Items :list should now contain 1 Item.");
        final Item actual = location.getItems().get(0);
        assertEquals(item, actual, "The Location's Items ArrayList should contain the expected Item.");
    }

    @Test
    @DisplayName("Test removing an item from the Location.")
    void testRemoveItemFromLocation() {
        final Location location = new Location("full desc", "short desc", new ArrayList<>(), new ArrayList<>(), false, "name");
        final Item item = new Item(1, "location desc", "inven desc", "name", 23, 5);
        addItemToLocation(item);
        removeItemFromLocation(item);

        List<Item> actual = location.getItems();
        assertTrue(actual.isEmpty(), "The Location's Items ArrayList should not contain the expected Item.");
    }
}
