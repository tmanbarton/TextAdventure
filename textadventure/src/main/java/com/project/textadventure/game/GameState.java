package com.project.textadventure.game;

import com.project.textadventure.constants.Constants;
import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.constants.LocationDescriptions;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.game.Locations.Dam;
import com.project.textadventure.game.Locations.Location;
import com.project.textadventure.game.Locations.MineEntrance;
import com.project.textadventure.game.Locations.Shed;
import com.project.textadventure.game.Locations.UndergroundLake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.UUID;

public class GameState {
    private static GameState instance;
    private final Map<UUID, Game> map = new HashMap<>();
    private Game game;

    private GameState() {
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

//    public Map<UUID, LocationGraph> getMap() {
//        return map;
//    }

    public Game getGame() {
        if (game == null) {
            game = createGame();
        }
        return game;
    }

//    public Location getGame(UUID userId) {
//        Player player = map.get(userId);
//        if (player == null) {
//            player = createGame();
//        }
//        return player;
//    }

    Game createGame() {
        // Create items for locations
        final Item key = new Item(1, ItemConstants.KEY_LOCATION_DESCRIPTION, ItemConstants.KEY_INVENTORY_DESCRIPTION, ItemConstants.KEY_NAME);
        final Item gold = new Item(6, ItemConstants.GOLD_LOCATION_DESCRIPTION, ItemConstants.GOLD_INVENTORY_DESCRIPTION, ItemConstants.GOLD_NAME);
        final Item magnet = new Item(10, ItemConstants.MAGNET_LOCATION_DESCRIPTION, ItemConstants.MAGNET_INVENTORY_DESCRIPTION, ItemConstants.MAGNET_NAME);
        final Item ruby = new Item(11, ItemConstants.RUBY_LOCATION_DESCRIPTION, ItemConstants.RUBY_INVENTORY_DESCRIPTION, ItemConstants.RUBY_NAME);

        // Initialize lists for respective location's items. If the game is already in progress, don't add the item to the location, otherwise add it 
        final List<Item> ditchItems = this.game != null &&
                this.game.getInventoryItemByName(ItemConstants.KEY_NAME) != null ?
                new ArrayList<>() : new ArrayList<>(List.of(key));
        final List<Item> mineEntranceItems = this.game != null && this.game.getInventoryItemByName(ItemConstants.GOLD_NAME) != null ? 
                new ArrayList<>() :  new ArrayList<>(List.of(gold));
        final List<Item> logCabinItems = this.game != null && this.game.getInventoryItemByName(ItemConstants.MAGNET_NAME) != null ?
                new ArrayList<>() :  new ArrayList<>(List.of(magnet));
        final List<Item> crumpledMineCartItems = this.game != null && this.game.getInventoryItemByName(ItemConstants.RUBY_NAME) != null ?
                new ArrayList<>() : new ArrayList<>(List.of(ruby));

        // Create all the locations for the game
        final Location antHill = new Location(LocationDescriptions.ANT_HILL_LONG_DESCRIPTION, LocationDescriptions.ANT_HILL_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.ANT_HILL);
        final Location archeryRange = new Location(LocationDescriptions.ARCHERY_RANGE_LONG_DESCRIPTION, LocationDescriptions.ARCHERY_RANGE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.ARCHERY_RANGE);
        final Location boat = new Location(LocationDescriptions.BOAT_LONG_DESCRIPTION, LocationDescriptions.BOAT_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.BOAT);
        final Location bottomOfVerticalMineShaft = new Location(LocationDescriptions.BOTTOM_OF_VERTICAL_MINE_SHAFT_LONG_DESCRIPTION, LocationDescriptions.BOTTOM_OF_VERTICAL_MINE_SHAFT_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.BOTTOM_OF_VERTICAL_MINE_SHAFT);
        final Location brokenRock = new Location(LocationDescriptions.BROKEN_ROCK_LONG_DESCRIPTION, LocationDescriptions.BROKEN_ROCK_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.BROKEN_ROCK);
        final Location crumpledMineCart = new Location(LocationDescriptions.CRUMPLED_MINE_CART_LONG_DESCRIPTION, LocationDescriptions.CRUMPLED_MINE_CART_SHORT_DESCRIPTION, crumpledMineCartItems, new ArrayList<>(), false, LocationNames.CRUMPLED_MINE_CART);
        final Location dankPassage = new Location(LocationDescriptions.DANK_PASSAGE_LONG_DESCRIPTION, LocationDescriptions.DANK_PASSAGE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DANK_PASSAGE);
        final Location dirtRoad = new Location(LocationDescriptions.DIRT_ROAD_LONG_DESCRIPTION, LocationDescriptions.DIRT_ROAD_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DIRT_ROAD);
        final Location dirtyPassage = new Location(LocationDescriptions.DIRTY_PASSAGE_LONG_DESCRIPTION, LocationDescriptions.DIRTY_PASSAGE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DIRTY_PASSAGE);
        final Location ditch = new Location(LocationDescriptions.DITCH_LONG_DESCRIPTION, LocationDescriptions.DITCH_SHORT_DESCRIPTION, ditchItems, new ArrayList<>(), false, LocationNames.DITCH);
        final Location driveway = new Location(LocationDescriptions.DRIVEWAY_LONG_DESCRIPTION, LocationDescriptions.DRIVEWAY_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DRIVEWAY);
        final Location dynamiteHoles = new Location(LocationDescriptions.DYNAMITE_HOLES_LONG_DESCRIPTION, LocationDescriptions.DYNAMITE_HOLES_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DYNAMITE_HOLES);
        final Location eastEndOfSideStreet = new Location(LocationDescriptions.EAST_END_SIDE_STREET_LONG_DESCRIPTION, LocationDescriptions.EAST_END_SIDE_STREET_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.EAST_END_SIDE_STREET);
        final Location fieldsOfGrass = new Location(LocationDescriptions.FIELDS_OF_GRASS_LONG_DESCRIPTION, LocationDescriptions.FIELDS_OF_GRASS_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.FIELDS_OF_GRASS);
        final Location footPath = new Location(LocationDescriptions.FOOT_PATH_LONG_DESCRIPTION, LocationDescriptions.FOOT_PATH_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.FOOT_PATH);
        final Location graniteRoom = new Location(LocationDescriptions.GRANITE_ROOM_LONG_DESCRIPTION, LocationDescriptions.GRANITE_ROOM_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.GRANITE_ROOM);
        final Location insideLogCabin = new Location(LocationDescriptions.INSIDE_LOG_CABIN_LONG_DESCRIPTION, LocationDescriptions.INSIDE_LOG_CABIN_SHORT_DESCRIPTION, logCabinItems, new ArrayList<>(), false, LocationNames.INSIDE_LOG_CABIN);
        final Location insideTavern = new Location(LocationDescriptions.INSIDE_TAVERN_LONG_DESCRIPTION, LocationDescriptions.INSIDE_TAVERN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.INSIDE_TAVERN);
        final Location intersection = new Location(LocationDescriptions.INTERSECTION_LONG_DESCRIPTION, LocationDescriptions.INTERSECTION_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.INTERSECTION);
        final Location lake = new Location(LocationDescriptions.LAKE_LONG_DESCRIPTION, LocationDescriptions.LAKE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.LAKE);
        final Location lakeTown = new Location(LocationDescriptions.LAKE_TOWN_LONG_DESCRIPTION, LocationDescriptions.LAKE_TOWN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.LAKE_TOWN);
        final Location lightningTree = new Location(LocationDescriptions.LIGHTNING_TREE_LONG_DESCRIPTION, LocationDescriptions.LIGHTNING_TREE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.LIGHTNING_TREE);
        final Location mineShaft = new Location(LocationDescriptions.MINE_SHAFT_LONG_DESCRIPTION, LocationDescriptions.MINE_SHAFT_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.MINE_SHAFT);
        final Location mountainPass = new Location(LocationDescriptions.MOUNTAIN_PASS_LONG_DESCRIPTION, LocationDescriptions.MOUNTAIN_PASS_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.MOUNTAIN_PASS);
        final Location mustyBend = new Location(LocationDescriptions.MUSTY_BEND_LONG_DESCRIPTION, LocationDescriptions.MUSTY_BEND_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.MUSTY_BEND);
        final Location narrowCorridor = new Location(LocationDescriptions.NARROW_CORRIDOR_LONG_DESCRIPTION, LocationDescriptions.NARROW_CORRIDOR_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.NARROW_CORRIDOR);
        final Location outsideLogCabin = new Location(LocationDescriptions.OUTSIDE_LOG_CABIN_LONG_DESCRIPTION, LocationDescriptions.OUTSIDE_LOG_CABIN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.OUTSIDE_LOG_CABIN);
        final Location outsideTavern = new Location(LocationDescriptions.OUTSIDE_TAVERN_LONG_DESCRIPTION, LocationDescriptions.OUTSIDE_TAVERN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.OUTSIDE_TAVERN);
        final Location picnicTable = new Location(LocationDescriptions.PICNIC_TABLE_LONG_DESCRIPTION, LocationDescriptions.PICNIC_TABLE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.PICNIC_TABLE);
        final Location privateProperty = new Location(LocationDescriptions.PRIVATE_PROPERTY_LONG_DESCRIPTION, LocationDescriptions.PRIVATE_PROPERTY_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.PRIVATE_PROPERTY);
        final Location roadInValley = new Location(LocationDescriptions.ROAD_IN_VALLEY_LONG_DESCRIPTION, LocationDescriptions.ROAD_IN_VALLEY_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.ROAD_IN_VALLEY);
        final Location tailings = new Location(LocationDescriptions.TAILINGS_LONG_DESCRIPTION, LocationDescriptions.TAILINGS_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.TAILINGS);
        final Location topOfHill = new Location(LocationDescriptions.TOP_OF_HILL_LONG_DESCRIPTION, LocationDescriptions.TOP_OF_HILL_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.TOP_OF_HILL);
        final Location topOfStairs = new Location(LocationDescriptions.TOP_OF_STAIRS_LONG_DESCRIPTION, LocationDescriptions.TOP_OF_STAIRS_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.TOP_OF_STAIRS);
        final Location upstairsLogCabin = new Location(LocationDescriptions.UPSTAIRS_LOG_CABIN_LONG_DESCRIPTION, LocationDescriptions.UPSTAIRS_LOG_CABIN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.UPSTAIRS_LOG_CABIN);
        final Location westEndOfSideStreet = new Location(LocationDescriptions.WEST_END_SIDE_STREET_LONG_DESCRIPTION, LocationDescriptions.WEST_END_SIDE_STREET_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.WEST_END_SIDE_STREET);
        final Dam dam = new Dam(LocationDescriptions.DAM_LONG_DESCRIPTION, LocationDescriptions.DAM_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DAM, false, false);
        final MineEntrance mineEntrance = new MineEntrance(LocationDescriptions.MINE_ENTRANCE_LONG_DESCRIPTION, LocationDescriptions.MINE_ENTRANCE_SHORT_DESCRIPTION, mineEntranceItems, new ArrayList<>(), false, LocationNames.MINE_ENTRANCE, false);
        final Shed shed = new Shed(LocationDescriptions.SHED_LONG_DESCRIPTION, LocationDescriptions.SHED_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.SHED, false, false);
        final UndergroundLake undergroundLakeSE = new UndergroundLake(LocationDescriptions.UNDERGROUND_LAKE_SE_LONG_DESCRIPTION, LocationDescriptions.UNDERGROUND_LAKE_SE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.UNDERGROUND_LAKE_SE, false);
        final UndergroundLake undergroundLakeNE = new UndergroundLake(LocationDescriptions.UNDERGROUND_LAKE_NE_LONG_DESCRIPTION, LocationDescriptions.UNDERGROUND_LAKE_NE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.UNDERGROUND_LAKE_NE, false);
        final UndergroundLake undergroundLakeWest = new UndergroundLake(LocationDescriptions.UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION, LocationDescriptions.UNDERGROUND_LAKE_WEST_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.UNDERGROUND_LAKE_WEST, true);

        // Connect all the locations together with directions to create a graph
        antHill.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), ditch));
        archeryRange.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), ditch));
        archeryRange.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), privateProperty));
        boat.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), undergroundLakeWest));
        boat.connectLocation(new ConnectingLocation(List.of(Constants.NORTHEAST, Constants.NE), undergroundLakeNE));
        boat.connectLocation(new ConnectingLocation(List.of(Constants.SOUTHEAST, Constants.SE), undergroundLakeSE));
        bottomOfVerticalMineShaft.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), dankPassage));
//        bottomOfVerticalMineShaft.connectLocation.(new ConnectingLocation(List.of(Constants.IN, Constants.ENTER), mine cage));
        brokenRock.connectLocation(new ConnectingLocation(List.of(Constants.DOWN, Constants.D), dirtyPassage));
        crumpledMineCart.connectLocation(new ConnectingLocation(List.of(Constants.UP, Constants.U), dynamiteHoles));
        dam.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N, Constants.UP, Constants.U), topOfStairs));
        dam.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), lake));
        dam.connectLocation(new ConnectingLocation(new ArrayList<>(), lakeTown));
        dankPassage.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), narrowCorridor));
        dankPassage.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), bottomOfVerticalMineShaft));
        dankPassage.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), mustyBend));
        dirtRoad.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), intersection));
        dirtRoad.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), driveway));
        dirtyPassage.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), narrowCorridor));
        dirtyPassage.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), undergroundLakeNE));
        dirtyPassage.connectLocation(new ConnectingLocation(List.of(Constants.UP, Constants.U), brokenRock));
        ditch.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), archeryRange));
        ditch.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), antHill));
        ditch.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), lightningTree));
        driveway.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N, Constants.DOWN, Constants.D), privateProperty));
        driveway.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), dirtRoad));
        driveway.connectLocation(new ConnectingLocation(List.of(Constants.NORTHWEST, Constants.NW), footPath));
        dynamiteHoles.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), mustyBend));
        dynamiteHoles.connectLocation(new ConnectingLocation(List.of(Constants.SOUTHWEST, Constants.SW, Constants.UP, Constants.U), narrowCorridor));
        dynamiteHoles.connectLocation(new ConnectingLocation(List.of(Constants.DOWN, Constants.D), crumpledMineCart));
        eastEndOfSideStreet.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), roadInValley));
        eastEndOfSideStreet.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), outsideTavern));
        eastEndOfSideStreet.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), topOfStairs));
        fieldsOfGrass.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), roadInValley));
        fieldsOfGrass.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), mountainPass));
        fieldsOfGrass.connectLocation(new ConnectingLocation(List.of(Constants.UP, Constants.U), mountainPass));
        footPath.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), driveway));
        footPath.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), topOfHill));
        graniteRoom.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), undergroundLakeSE));
        graniteRoom.connectLocation(new ConnectingLocation(List.of(Constants.OUT, Constants.EXIT), undergroundLakeSE));
        insideLogCabin.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E, Constants.OUT, Constants.EXIT), outsideLogCabin));
        insideLogCabin.connectLocation(new ConnectingLocation(List.of(Constants.UP, Constants.U), upstairsLogCabin));
        intersection.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N, Constants.UP, Constants.U), topOfHill));
        intersection.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), tailings));
        intersection.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), dirtRoad));
        insideTavern.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W, Constants.OUT, Constants.EXIT), outsideTavern));
        lake.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), dam));
        lake.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), tailings));
        lakeTown.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E, Constants.UP, Constants.U), dam));
//        lakeTown.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), farther into the town));
        lightningTree.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), ditch));
        mineShaft.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), undergroundLakeWest));
        mineShaft.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W, Constants.OUT, Constants.EXIT), mineEntrance));
        mineEntrance.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), tailings));
        mineEntrance.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E, Constants.IN, Constants.ENTER), mineShaft));
        mountainPass.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S, Constants.DOWN, Constants.D), fieldsOfGrass));
//        mountainPass.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), mine cage));
        mustyBend.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), dynamiteHoles));
        mustyBend.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), dankPassage));
        narrowCorridor.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), dankPassage));
        narrowCorridor.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), dirtyPassage));
        narrowCorridor.connectLocation(new ConnectingLocation(List.of(Constants.NORTHEAST, Constants.NE, Constants.DOWN, Constants.D), dynamiteHoles));
        outsideLogCabin.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), westEndOfSideStreet));
        outsideLogCabin.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W, Constants.IN, Constants.ENTER), insideLogCabin));
        outsideTavern.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), eastEndOfSideStreet));
//        outsideTavern.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), head towards colorado springs?));
        outsideTavern.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E, Constants.IN, Constants.ENTER), insideTavern));
        picnicTable.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), shed));
        picnicTable.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), privateProperty));
        privateProperty.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), archeryRange));
        privateProperty.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S, Constants.UP, Constants.U), driveway));
        privateProperty.connectLocation(new ConnectingLocation(List.of(Constants.NORTHEAST, Constants.NE), picnicTable));
        roadInValley.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), fieldsOfGrass));
        roadInValley.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), eastEndOfSideStreet));
        shed.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), picnicTable));
        tailings.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), mineEntrance));
        tailings.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), lake));
        tailings.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), intersection));
        topOfHill.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S, Constants.DOWN, Constants.D), intersection));
        topOfHill.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), footPath));
        topOfStairs.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S, Constants.DOWN, Constants.D), dam));
        topOfStairs.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), eastEndOfSideStreet));
        topOfStairs.connectLocation(new ConnectingLocation(List.of(Constants.WEST, Constants.W), westEndOfSideStreet));
        undergroundLakeWest.connectLocation(new ConnectingLocation(List.of(Constants.SOUTH, Constants.S), mineShaft));
        undergroundLakeWest.connectLocation(new ConnectingLocation(List.of(Constants.IN, Constants.ENTER), boat));
        undergroundLakeNE.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), dirtyPassage));
        undergroundLakeNE.connectLocation(new ConnectingLocation(List.of(Constants.IN, Constants.ENTER), boat));
        undergroundLakeSE.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), graniteRoom));
        undergroundLakeSE.connectLocation(new ConnectingLocation(List.of(Constants.IN, Constants.ENTER), boat));
        upstairsLogCabin.connectLocation(new ConnectingLocation(List.of(Constants.DOWN, Constants.D), insideLogCabin));
        westEndOfSideStreet.connectLocation(new ConnectingLocation(List.of(Constants.NORTH, Constants.N), outsideLogCabin));
        westEndOfSideStreet.connectLocation(new ConnectingLocation(List.of(Constants.EAST, Constants.E), topOfStairs));

        // Set start location by setting it as visited and creating a new Game object with that location as the current location
        driveway.setVisited(true);
        return new Game(new ArrayList<>(), driveway, false);

        //// Change start location for manual debugging. ////
        //// Make sure to comment back out; it will make tests fail ////
//        dam.setVisited(true);
//        Game game = new Game(new ArrayList<>(), dam, false);
////        game.addItemToInventory(new Item(5, "There is a jar here", "Jar", "jar"));
////        game.addItemToInventory(new Item(3, "There is a bow here, strung and ready for shooting", "Bow", "bow"));
////        game.die();
//        game.addItemToInventory(new Item(4, "There is an arrow here", "Arrow", "arrow"));
//        game.addItemToInventory(magnet);
//        return game;
        ///////////////////
    }
}
