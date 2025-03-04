package com.project.textadventure.game;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.constants.LocationDescriptions;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.controllers.GameStatus;
import com.project.textadventure.game.Graph.Boat;
import com.project.textadventure.game.Graph.LocationConnection;
import com.project.textadventure.game.Graph.Dam;
import com.project.textadventure.game.Graph.Item;
import com.project.textadventure.game.Graph.Location;
import com.project.textadventure.game.Graph.MineEntrance;
import com.project.textadventure.game.Graph.MineShaft;
import com.project.textadventure.game.Graph.Shed;
import com.project.textadventure.game.Graph.UndergroundLake;

import java.util.ArrayList;
import java.util.List;

import static com.project.textadventure.constants.GameConstants.*;
import static com.project.textadventure.game.GameUtils.addItemToInventory;

public class GameState {
    private static GameState instance;
    private Game game;

    private static int lifeCount;
    private static double score;

    private GameState() {
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    /**
     * Only used for testing since singletons can't be mocked with Mockito.
     */
    public GameState(final Game game) {
        this.game = game;
    }

    public Game getGame() {
        if (game == null) {
            game = initializeGame(GameStatus.NEW);
        }
        return game;
    }

    public int getLifeCount() {
        return lifeCount;
    }

    public double getScore() {
        return score;
    }

    public void incrementScore(final double points) {
        GameState.score += points;
    }

    public void decrementScore(final double points) {
        GameState.score -= points;
    }

    public void decrementLifeCount() {
        GameState.lifeCount--;
    }

    Game initializeGame(final GameStatus status) {
        // Create items for locations
        final Item key = new Item(1, ItemConstants.KEY_LOCATION_DESCRIPTION, ItemConstants.KEY_INVENTORY_DESCRIPTION, ItemConstants.KEY_NAME, 0, 1);
        final Item gold = new Item(6, ItemConstants.GOLD_LOCATION_DESCRIPTION, ItemConstants.GOLD_INVENTORY_DESCRIPTION, ItemConstants.GOLD_NAME, 30, 4);
        final Item magnet = new Item(10, ItemConstants.MAGNET_LOCATION_DESCRIPTION, ItemConstants.MAGNET_INVENTORY_DESCRIPTION, ItemConstants.MAGNET_NAME, 0, 1);
        final Item ruby = new Item(11, ItemConstants.RUBY_LOCATION_DESCRIPTION, ItemConstants.RUBY_INVENTORY_DESCRIPTION, ItemConstants.RUBY_NAME, 50, 5);
        final Item pie = new Item(12, ItemConstants.PIE_LOCATION_DESCRIPTION, ItemConstants.PIE_INVENTORY_DESCRIPTION, ItemConstants.PIE_NAME, 0, 1);

        // Initialize lists for respective location's items. If the game is already in progress, don't add the item to the location, otherwise add it
        final List<Item> ditchItems = new ArrayList<>(List.of(key));
        final List<Item> mineEntranceItems = new ArrayList<>(List.of(gold));
        final List<Item> logCabinItems = new ArrayList<>(List.of(magnet));
        final List<Item> crumpledMineCartItems = new ArrayList<>(List.of(ruby));
        final List<Item> graniteRoomItems = new ArrayList<>(List.of(pie));

        // Create all the locations for the game
        final Location antHill =                    new Location(LocationDescriptions.ANT_HILL_LONG_DESCRIPTION, LocationDescriptions.ANT_HILL_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.ANT_HILL);
        final Location archeryRange =               new Location(LocationDescriptions.ARCHERY_RANGE_LONG_DESCRIPTION, LocationDescriptions.ARCHERY_RANGE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.ARCHERY_RANGE);
        final Location brokenRock =                 new Location(LocationDescriptions.BROKEN_ROCK_LONG_DESCRIPTION, LocationDescriptions.BROKEN_ROCK_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.BROKEN_ROCK);
        final Location crumpledMineCart =           new Location(LocationDescriptions.CRUMPLED_MINE_CART_LONG_DESCRIPTION, LocationDescriptions.CRUMPLED_MINE_CART_SHORT_DESCRIPTION, crumpledMineCartItems, new ArrayList<>(), false, LocationNames.CRUMPLED_MINE_CART);
        final Location dankPassage =                new Location(LocationDescriptions.DANK_PASSAGE_LONG_DESCRIPTION, LocationDescriptions.DANK_PASSAGE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DANK_PASSAGE);
        final Location dirtRoad =                   new Location(LocationDescriptions.DIRT_ROAD_LONG_DESCRIPTION, LocationDescriptions.DIRT_ROAD_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DIRT_ROAD);
        final Location dirtyPassage =               new Location(LocationDescriptions.DIRTY_PASSAGE_LONG_DESCRIPTION, LocationDescriptions.DIRTY_PASSAGE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DIRTY_PASSAGE);
        final Location ditch =                      new Location(LocationDescriptions.DITCH_LONG_DESCRIPTION, LocationDescriptions.DITCH_SHORT_DESCRIPTION, ditchItems, new ArrayList<>(), false, LocationNames.DITCH);
        final Location driveway =                   new Location(LocationDescriptions.DRIVEWAY_LONG_DESCRIPTION, LocationDescriptions.DRIVEWAY_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DRIVEWAY);
        final Location dynamiteHoles =              new Location(LocationDescriptions.DYNAMITE_HOLES_LONG_DESCRIPTION, LocationDescriptions.DYNAMITE_HOLES_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DYNAMITE_HOLES);
        final Location eastEndOfSideStreet =        new Location(LocationDescriptions.EAST_END_SIDE_STREET_LONG_DESCRIPTION, LocationDescriptions.EAST_END_SIDE_STREET_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.EAST_END_SIDE_STREET);
        final Location fieldsOfGrass =              new Location(LocationDescriptions.FIELDS_OF_GRASS_LONG_DESCRIPTION, LocationDescriptions.FIELDS_OF_GRASS_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.FIELDS_OF_GRASS);
        final Location footPath =                   new Location(LocationDescriptions.FOOT_PATH_LONG_DESCRIPTION, LocationDescriptions.FOOT_PATH_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.FOOT_PATH);
        final Location graniteRoom =                new Location(LocationDescriptions.GRANITE_ROOM_LONG_DESCRIPTION, LocationDescriptions.GRANITE_ROOM_SHORT_DESCRIPTION, graniteRoomItems, new ArrayList<>(), false, LocationNames.GRANITE_ROOM);
        final Location insideLogCabin =             new Location(LocationDescriptions.INSIDE_LOG_CABIN_LONG_DESCRIPTION, LocationDescriptions.INSIDE_LOG_CABIN_SHORT_DESCRIPTION, logCabinItems, new ArrayList<>(), false, LocationNames.INSIDE_LOG_CABIN);
        final Location insideTavern =               new Location(LocationDescriptions.INSIDE_TAVERN_LONG_DESCRIPTION, LocationDescriptions.INSIDE_TAVERN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.INSIDE_TAVERN);
        final Location intersection =               new Location(LocationDescriptions.INTERSECTION_LONG_DESCRIPTION, LocationDescriptions.INTERSECTION_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.INTERSECTION);
        final Location lake =                       new Location(LocationDescriptions.LAKE_LONG_DESCRIPTION, LocationDescriptions.LAKE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.LAKE);
        final Location lakeTown =                   new Location(LocationDescriptions.LAKE_TOWN_LONG_DESCRIPTION, LocationDescriptions.LAKE_TOWN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.LAKE_TOWN);
        final Location lightningTree =              new Location(LocationDescriptions.LIGHTNING_TREE_LONG_DESCRIPTION, LocationDescriptions.LIGHTNING_TREE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.LIGHTNING_TREE);
        final Location mineShaft =                  new Location(LocationDescriptions.MINE_SHAFT_LONG_DESCRIPTION, LocationDescriptions.MINE_SHAFT_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.MINE_SHAFT);
        final Location mustyBend =                  new Location(LocationDescriptions.MUSTY_BEND_LONG_DESCRIPTION, LocationDescriptions.MUSTY_BEND_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.MUSTY_BEND);
        final Location narrowCorridor =             new Location(LocationDescriptions.NARROW_CORRIDOR_LONG_DESCRIPTION, LocationDescriptions.NARROW_CORRIDOR_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.NARROW_CORRIDOR);
        final Location outsideLogCabin =            new Location(LocationDescriptions.OUTSIDE_LOG_CABIN_LONG_DESCRIPTION, LocationDescriptions.OUTSIDE_LOG_CABIN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.OUTSIDE_LOG_CABIN);
        final Location outsideTavern =              new Location(LocationDescriptions.OUTSIDE_TAVERN_LONG_DESCRIPTION, LocationDescriptions.OUTSIDE_TAVERN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.OUTSIDE_TAVERN);
        final Location picnicTable =                new Location(LocationDescriptions.PICNIC_TABLE_LONG_DESCRIPTION, LocationDescriptions.PICNIC_TABLE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.PICNIC_TABLE);
        final Location privateProperty =            new Location(LocationDescriptions.PRIVATE_PROPERTY_LONG_DESCRIPTION, LocationDescriptions.PRIVATE_PROPERTY_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.PRIVATE_PROPERTY);
        final Location roadInValley =               new Location(LocationDescriptions.ROAD_IN_VALLEY_LONG_DESCRIPTION, LocationDescriptions.ROAD_IN_VALLEY_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.ROAD_IN_VALLEY);
        final Location tailings =                   new Location(LocationDescriptions.TAILINGS_LONG_DESCRIPTION, LocationDescriptions.TAILINGS_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.TAILINGS);
        final Location topOfHill =                  new Location(LocationDescriptions.TOP_OF_HILL_LONG_DESCRIPTION, LocationDescriptions.TOP_OF_HILL_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.TOP_OF_HILL);
        final Location topOfStairs =                new Location(LocationDescriptions.TOP_OF_STAIRS_LONG_DESCRIPTION, LocationDescriptions.TOP_OF_STAIRS_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.TOP_OF_STAIRS);
        final Location upstairsLogCabin =           new Location(LocationDescriptions.UPSTAIRS_LOG_CABIN_LONG_DESCRIPTION, LocationDescriptions.UPSTAIRS_LOG_CABIN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.UPSTAIRS_LOG_CABIN);
        final Location westEndOfSideStreet =        new Location(LocationDescriptions.WEST_END_SIDE_STREET_LONG_DESCRIPTION, LocationDescriptions.WEST_END_SIDE_STREET_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.WEST_END_SIDE_STREET);
        // Special ones
        final Boat boat =                           new Boat(LocationDescriptions.BOAT_LONG_DESCRIPTION, LocationDescriptions.BOAT_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.BOAT);
        final Dam dam =                             new Dam(LocationDescriptions.DAM_LONG_DESCRIPTION, LocationDescriptions.DAM_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DAM, false, false);
        final MineEntrance mineEntrance =           new MineEntrance(LocationDescriptions.MINE_ENTRANCE_LONG_DESCRIPTION, LocationDescriptions.MINE_ENTRANCE_SHORT_DESCRIPTION, mineEntranceItems, new ArrayList<>(), false, LocationNames.MINE_ENTRANCE, false);
        final Shed shed =                           new Shed(LocationDescriptions.SHED_LONG_DESCRIPTION, LocationDescriptions.SHED_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.SHED, false, false);
        final UndergroundLake undergroundLakeSE =   new UndergroundLake(LocationDescriptions.UNDERGROUND_LAKE_SE_LONG_DESCRIPTION_NO_BOAT, LocationDescriptions.UNDERGROUND_LAKE_SE_SHORT_DESCRIPTION_NO_BOAT, new ArrayList<>(), new ArrayList<>(), false, LocationNames.UNDERGROUND_LAKE_SE, false);
        final UndergroundLake undergroundLakeNE =   new UndergroundLake(LocationDescriptions.UNDERGROUND_LAKE_NE_LONG_DESCRIPTION_NO_BOAT, LocationDescriptions.UNDERGROUND_LAKE_NE_SHORT_DESCRIPTION_NO_BOAT, new ArrayList<>(), new ArrayList<>(), false, LocationNames.UNDERGROUND_LAKE_NE, false);
        final UndergroundLake undergroundLakeWest = new UndergroundLake(LocationDescriptions.UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION_WITH_BOAT, LocationDescriptions.UNDERGROUND_LAKE_WEST_SHORT_DESCRIPTION_NO_BOAT, new ArrayList<>(), new ArrayList<>(), false, LocationNames.UNDERGROUND_LAKE_WEST, true);
        final MineShaft bottomOfVerticalMineShaft = new MineShaft(LocationDescriptions.BOTTOM_MINE_SHAFT_NO_CAGE_LONG_DESCRIPTION, LocationDescriptions.BOTTOM_OF_VERTICAL_MINE_SHAFT_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.BOTTOM_OF_VERTICAL_MINE_SHAFT);
        final MineShaft mountainPass =              new MineShaft(LocationDescriptions.MOUNTAIN_PASS_WITH_CAGE_LONG_DESCRIPTION, LocationDescriptions.MOUNTAIN_PASS_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.MOUNTAIN_PASS);
        final MineShaft mineCage =                  new MineShaft(LocationDescriptions.MINE_CAGE_LONG_DESCRIPTION, LocationDescriptions.MINE_CAGE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.MINE_CAGE);

        // Connect all the locations together with directions to create a graph
        antHill.connectLocation(new LocationConnection(WEST_DIRECTIONS, ditch));
        archeryRange.connectLocation(new LocationConnection(NORTH_DIRECTIONS, ditch));
        archeryRange.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, privateProperty));
        boat.connectLocation(new LocationConnection(WEST_DIRECTIONS, undergroundLakeWest));
        boat.connectLocation(new LocationConnection(NE_DIRECTIONS, undergroundLakeNE));
        boat.connectLocation(new LocationConnection(SE_DIRECTIONS, undergroundLakeSE));
        bottomOfVerticalMineShaft.connectLocation(new LocationConnection(WEST_DIRECTIONS, dankPassage));
        brokenRock.connectLocation(new LocationConnection(DOWN_DIRECTIONS, dirtyPassage));
        crumpledMineCart.connectLocation(new LocationConnection(UP_DIRECTIONS, dynamiteHoles));
        dam.connectLocation(new LocationConnection(NORTH_AND_UP_DIRECTIONS, topOfStairs));
        dam.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, lake));
        dam.connectLocation(new LocationConnection(new ArrayList<>(), lakeTown));
        dankPassage.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, narrowCorridor));
        dankPassage.connectLocation(new LocationConnection(EAST_DIRECTIONS, bottomOfVerticalMineShaft));
        dankPassage.connectLocation(new LocationConnection(WEST_DIRECTIONS, mustyBend));
        dirtRoad.connectLocation(new LocationConnection(EAST_DIRECTIONS, intersection));
        dirtRoad.connectLocation(new LocationConnection(WEST_DIRECTIONS, driveway));
        dirtyPassage.connectLocation(new LocationConnection(EAST_DIRECTIONS, narrowCorridor));
        dirtyPassage.connectLocation(new LocationConnection(WEST_DIRECTIONS, undergroundLakeNE));
        dirtyPassage.connectLocation(new LocationConnection(UP_DIRECTIONS, brokenRock));
        ditch.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, archeryRange));
        ditch.connectLocation(new LocationConnection(EAST_DIRECTIONS, antHill));
        ditch.connectLocation(new LocationConnection(WEST_DIRECTIONS, lightningTree));
        driveway.connectLocation(new LocationConnection(NORTH_AND_DOWN_DIRECTIONS, privateProperty));
        driveway.connectLocation(new LocationConnection(EAST_DIRECTIONS, dirtRoad));
        driveway.connectLocation(new LocationConnection(NW_DIRECTIONS, footPath));
        dynamiteHoles.connectLocation(new LocationConnection(NORTH_DIRECTIONS, mustyBend));
        dynamiteHoles.connectLocation(new LocationConnection(SW_AND_UP_DIRECTIONS, narrowCorridor));
        dynamiteHoles.connectLocation(new LocationConnection(DOWN_DIRECTIONS, crumpledMineCart));
        eastEndOfSideStreet.connectLocation(new LocationConnection(NORTH_DIRECTIONS, roadInValley));
        eastEndOfSideStreet.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, outsideTavern));
        eastEndOfSideStreet.connectLocation(new LocationConnection(WEST_DIRECTIONS, topOfStairs));
        fieldsOfGrass.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, roadInValley));
        fieldsOfGrass.connectLocation(new LocationConnection(NORTH_DIRECTIONS, mountainPass));
        fieldsOfGrass.connectLocation(new LocationConnection(UP_DIRECTIONS, mountainPass));
        footPath.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, driveway));
        footPath.connectLocation(new LocationConnection(EAST_DIRECTIONS, topOfHill));
        graniteRoom.connectLocation(new LocationConnection(WEST_AND_OUT_DIRECTIONS, undergroundLakeSE));
        insideLogCabin.connectLocation(new LocationConnection(EAST_AND_OUT_DIRECTIONS, outsideLogCabin));
        insideLogCabin.connectLocation(new LocationConnection(UP_DIRECTIONS, upstairsLogCabin));
        intersection.connectLocation(new LocationConnection(NORTH_AND_UP_DIRECTIONS, topOfHill));
        intersection.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, tailings));
        intersection.connectLocation(new LocationConnection(WEST_DIRECTIONS, dirtRoad));
        insideTavern.connectLocation(new LocationConnection(WEST_AND_OUT_DIRECTIONS, outsideTavern));
        lake.connectLocation(new LocationConnection(NORTH_DIRECTIONS, dam));
        lake.connectLocation(new LocationConnection(WEST_DIRECTIONS, tailings));
        lakeTown.connectLocation(new LocationConnection(EAST_AND_UP_DIRECTIONS, dam));
//        lakeTown.connectLocation(new ConnectingLocation(List.of(WEST, W), farther into the town));
        lightningTree.connectLocation(new LocationConnection(EAST_DIRECTIONS, ditch));
        mineCage.connectLocation(new LocationConnection(EAST_AND_OUT_DIRECTIONS, mountainPass));
        mineShaft.connectLocation(new LocationConnection(EAST_DIRECTIONS, undergroundLakeWest));
        mineShaft.connectLocation(new LocationConnection(WEST_AND_OUT_DIRECTIONS, mineEntrance));
        mineEntrance.connectLocation(new LocationConnection(NORTH_DIRECTIONS, tailings));
        mineEntrance.connectLocation(new LocationConnection(EAST_AND_IN_DIRECTIONS, mineShaft));
        mountainPass.connectLocation(new LocationConnection(SOUTH_AND_DOWN_DIRECTIONS, fieldsOfGrass));
        mountainPass.connectLocation(new LocationConnection(WEST_AND_IN_DIRECTIONS, mineCage));
        mustyBend.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, dynamiteHoles));
        mustyBend.connectLocation(new LocationConnection(EAST_DIRECTIONS, dankPassage));
        narrowCorridor.connectLocation(new LocationConnection(NORTH_DIRECTIONS, dankPassage));
        narrowCorridor.connectLocation(new LocationConnection(WEST_DIRECTIONS, dirtyPassage));
        narrowCorridor.connectLocation(new LocationConnection(NE_AND_DOWN_DIRECTIONS, dynamiteHoles));
        outsideLogCabin.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, westEndOfSideStreet));
        outsideLogCabin.connectLocation(new LocationConnection(WEST_AND_IN_DIRECTIONS, insideLogCabin));
        outsideTavern.connectLocation(new LocationConnection(NORTH_DIRECTIONS, eastEndOfSideStreet));
//        outsideTavern.connectLocation(new ConnectingLocation(List.of(SOUTH, S), head towards colorado springs?));
        outsideTavern.connectLocation(new LocationConnection(EAST_AND_IN_DIRECTIONS, insideTavern));
        picnicTable.connectLocation(new LocationConnection(NORTH_DIRECTIONS, shed));
        picnicTable.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, privateProperty));
        privateProperty.connectLocation(new LocationConnection(NORTH_DIRECTIONS, archeryRange));
        privateProperty.connectLocation(new LocationConnection(SOUTH_AND_UP_DIRECTIONS, driveway));
        privateProperty.connectLocation(new LocationConnection(NE_DIRECTIONS, picnicTable));
        roadInValley.connectLocation(new LocationConnection(NORTH_DIRECTIONS, fieldsOfGrass));
        roadInValley.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, eastEndOfSideStreet));
        shed.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, picnicTable));
        tailings.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, mineEntrance));
        tailings.connectLocation(new LocationConnection(EAST_DIRECTIONS, lake));
        tailings.connectLocation(new LocationConnection(NORTH_DIRECTIONS, intersection));
        topOfHill.connectLocation(new LocationConnection(SOUTH_AND_DOWN_DIRECTIONS, intersection));
        topOfHill.connectLocation(new LocationConnection(WEST_DIRECTIONS, footPath));
        topOfStairs.connectLocation(new LocationConnection(SOUTH_AND_DOWN_DIRECTIONS, dam));
        topOfStairs.connectLocation(new LocationConnection(EAST_DIRECTIONS, eastEndOfSideStreet));
        topOfStairs.connectLocation(new LocationConnection(WEST_DIRECTIONS, westEndOfSideStreet));
        undergroundLakeWest.connectLocation(new LocationConnection(SOUTH_DIRECTIONS, mineShaft));
        undergroundLakeWest.connectLocation(new LocationConnection(IN_DIRECTIONS, boat));
        undergroundLakeNE.connectLocation(new LocationConnection(EAST_DIRECTIONS, dirtyPassage));
        undergroundLakeNE.connectLocation(new LocationConnection(List.of(), boat));
        undergroundLakeSE.connectLocation(new LocationConnection(EAST_DIRECTIONS, graniteRoom));
        undergroundLakeSE.connectLocation(new LocationConnection(List.of(), boat));
        upstairsLogCabin.connectLocation(new LocationConnection(DOWN_DIRECTIONS, insideLogCabin));
        westEndOfSideStreet.connectLocation(new LocationConnection(NORTH_DIRECTIONS, outsideLogCabin));
        westEndOfSideStreet.connectLocation(new LocationConnection(EAST_DIRECTIONS, topOfStairs));

        lifeCount = 3;
        score = 0;

        // Set start location by setting it as visited and creating a new Game object with that location as the current location
        // Initialize member variable startLocation so it's available to other classes
        driveway.setVisited(true);

        ////// Helpful for manual debugging to change start location and add items to it //////
        game = new Game(new ArrayList<>(), mineEntrance, status);
//        addItemToInventory(pie);
        addItemToInventory(new Item(5, ItemConstants.JAR_LOCATION_DESCRIPTION, ItemConstants.JAR_INVENTORY_DESCRIPTION, ItemConstants.JAR_NAME, 0, 1));
//        addItemToInventory(new Item(4, ItemConstants.ARROW_LOCATION_DESCRIPTION, ItemConstants.ARROW_INVENTORY_DESCRIPTION, ItemConstants.ARROW_NAME, 0, 1));
//        addItemToInventory(new Item(3, ItemConstants.BOW_LOCATION_DESCRIPTION, ItemConstants.BOW_INVENTORY_DESCRIPTION, ItemConstants.BOW_NAME, 0, 3));
        return game;
        //////

//        return new Game(new ArrayList<>(), driveway, status);
    }

    public void restartGame() {
        this.game = initializeGame(GameStatus.IN_PROGRESS);
    }
}
