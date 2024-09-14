package com.project.textadventure.game;

import com.project.textadventure.constants.ItemConstants;
import com.project.textadventure.constants.LocationDescriptions;
import com.project.textadventure.constants.LocationNames;
import com.project.textadventure.controllers.GameStatus;
import com.project.textadventure.game.Graph.LocationConnection;
import com.project.textadventure.game.Graph.Dam;
import com.project.textadventure.game.Graph.Item;
import com.project.textadventure.game.Graph.Location;
import com.project.textadventure.game.Graph.MineEntrance;
import com.project.textadventure.game.Graph.Shed;
import com.project.textadventure.game.Graph.UndergroundLake;

import java.util.ArrayList;
import java.util.List;

import static com.project.textadventure.constants.GameConstants.*;

public class GameState {
    private static GameState instance;
    private Game game;

    public static Location startLocation;
    public static int lifeCount;

    private GameState() {
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
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

    public static void setLifeCount(int lifeCount) {
        GameState.lifeCount = lifeCount;
    }

    Game initializeGame(final GameStatus status) {
        // Create items for locations
        final Item key = new Item(1, ItemConstants.KEY_LOCATION_DESCRIPTION, ItemConstants.KEY_INVENTORY_DESCRIPTION, ItemConstants.KEY_NAME);
        final Item gold = new Item(6, ItemConstants.GOLD_LOCATION_DESCRIPTION, ItemConstants.GOLD_INVENTORY_DESCRIPTION, ItemConstants.GOLD_NAME);
        final Item magnet = new Item(10, ItemConstants.MAGNET_LOCATION_DESCRIPTION, ItemConstants.MAGNET_INVENTORY_DESCRIPTION, ItemConstants.MAGNET_NAME);
        final Item ruby = new Item(11, ItemConstants.RUBY_LOCATION_DESCRIPTION, ItemConstants.RUBY_INVENTORY_DESCRIPTION, ItemConstants.RUBY_NAME);

        // Initialize lists for respective location's items. If the game is already in progress, don't add the item to the location, otherwise add it
        final List<Item> ditchItems = new ArrayList<>(List.of(key));
        final List<Item> mineEntranceItems =  new ArrayList<>(List.of(gold));
        final List<Item> logCabinItems =  new ArrayList<>(List.of(magnet));
        final List<Item> crumpledMineCartItems = new ArrayList<>(List.of(ruby));

        // Create all the locations for the game
        final Location antHill =                    new Location(LocationDescriptions.ANT_HILL_LONG_DESCRIPTION, LocationDescriptions.ANT_HILL_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.ANT_HILL);
        final Location archeryRange =               new Location(LocationDescriptions.ARCHERY_RANGE_LONG_DESCRIPTION, LocationDescriptions.ARCHERY_RANGE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.ARCHERY_RANGE);
        final Location boat =                       new Location(LocationDescriptions.BOAT_LONG_DESCRIPTION, LocationDescriptions.BOAT_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.BOAT);
        final Location bottomOfVerticalMineShaft =  new Location(LocationDescriptions.BOTTOM_OF_VERTICAL_MINE_SHAFT_LONG_DESCRIPTION, LocationDescriptions.BOTTOM_OF_VERTICAL_MINE_SHAFT_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.BOTTOM_OF_VERTICAL_MINE_SHAFT);
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
        final Location graniteRoom =                new Location(LocationDescriptions.GRANITE_ROOM_LONG_DESCRIPTION, LocationDescriptions.GRANITE_ROOM_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.GRANITE_ROOM);
        final Location insideLogCabin =             new Location(LocationDescriptions.INSIDE_LOG_CABIN_LONG_DESCRIPTION, LocationDescriptions.INSIDE_LOG_CABIN_SHORT_DESCRIPTION, logCabinItems, new ArrayList<>(), false, LocationNames.INSIDE_LOG_CABIN);
        final Location insideTavern =               new Location(LocationDescriptions.INSIDE_TAVERN_LONG_DESCRIPTION, LocationDescriptions.INSIDE_TAVERN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.INSIDE_TAVERN);
        final Location intersection =               new Location(LocationDescriptions.INTERSECTION_LONG_DESCRIPTION, LocationDescriptions.INTERSECTION_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.INTERSECTION);
        final Location lake =                       new Location(LocationDescriptions.LAKE_LONG_DESCRIPTION, LocationDescriptions.LAKE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.LAKE);
        final Location lakeTown =                   new Location(LocationDescriptions.LAKE_TOWN_LONG_DESCRIPTION, LocationDescriptions.LAKE_TOWN_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.LAKE_TOWN);
        final Location lightningTree =              new Location(LocationDescriptions.LIGHTNING_TREE_LONG_DESCRIPTION, LocationDescriptions.LIGHTNING_TREE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.LIGHTNING_TREE);
        final Location mineShaft =                  new Location(LocationDescriptions.MINE_SHAFT_LONG_DESCRIPTION, LocationDescriptions.MINE_SHAFT_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.MINE_SHAFT);
        final Location mountainPass =               new Location(LocationDescriptions.MOUNTAIN_PASS_LONG_DESCRIPTION, LocationDescriptions.MOUNTAIN_PASS_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.MOUNTAIN_PASS);
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
        final Dam dam =                             new Dam(LocationDescriptions.DAM_LONG_DESCRIPTION, LocationDescriptions.DAM_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.DAM, false, false);
        final MineEntrance mineEntrance =           new MineEntrance(LocationDescriptions.MINE_ENTRANCE_LONG_DESCRIPTION, LocationDescriptions.MINE_ENTRANCE_SHORT_DESCRIPTION, mineEntranceItems, new ArrayList<>(), false, LocationNames.MINE_ENTRANCE, false);
        final Shed shed =                           new Shed(LocationDescriptions.SHED_LONG_DESCRIPTION, LocationDescriptions.SHED_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.SHED, false, false);
        final UndergroundLake undergroundLakeSE =   new UndergroundLake(LocationDescriptions.UNDERGROUND_LAKE_SE_LONG_DESCRIPTION, LocationDescriptions.UNDERGROUND_LAKE_SE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.UNDERGROUND_LAKE_SE, false);
        final UndergroundLake undergroundLakeNE =   new UndergroundLake(LocationDescriptions.UNDERGROUND_LAKE_NE_LONG_DESCRIPTION, LocationDescriptions.UNDERGROUND_LAKE_NE_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.UNDERGROUND_LAKE_NE, false);
        final UndergroundLake undergroundLakeWest = new UndergroundLake(LocationDescriptions.UNDERGROUND_LAKE_WEST_LONG_DESCRIPTION, LocationDescriptions.UNDERGROUND_LAKE_WEST_SHORT_DESCRIPTION, new ArrayList<>(), new ArrayList<>(), false, LocationNames.UNDERGROUND_LAKE_WEST, true);

        // Connect all the locations together with directions to create a graph
        antHill.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), ditch));
        archeryRange.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), ditch));
        archeryRange.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), privateProperty));
        boat.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), undergroundLakeWest));
        boat.connectLocation(new LocationConnection(List.of(NE_LONG, NE_SHORT), undergroundLakeNE));
        boat.connectLocation(new LocationConnection(List.of(SE_LONG, SE_SHORT), undergroundLakeSE));
        bottomOfVerticalMineShaft.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), dankPassage));
//        bottomOfVerticalMineShaft.connectLocation.(new ConnectingLocation(List.of(IN, ENTER), mine cage));
        brokenRock.connectLocation(new LocationConnection(List.of(DOWN_LONG, DOWN_SHORT), dirtyPassage));
        crumpledMineCart.connectLocation(new LocationConnection(List.of(UP_LONG, UP_SHORT), dynamiteHoles));
        dam.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT, UP_LONG, UP_SHORT), topOfStairs));
        dam.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), lake));
        dam.connectLocation(new LocationConnection(new ArrayList<>(), lakeTown));
        dankPassage.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), narrowCorridor));
        dankPassage.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), bottomOfVerticalMineShaft));
        dankPassage.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), mustyBend));
        dirtRoad.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), intersection));
        dirtRoad.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), driveway));
        dirtyPassage.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), narrowCorridor));
        dirtyPassage.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), undergroundLakeNE));
        dirtyPassage.connectLocation(new LocationConnection(List.of(UP_LONG, UP_SHORT), brokenRock));
        ditch.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), archeryRange));
        ditch.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), antHill));
        ditch.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), lightningTree));
        driveway.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT, DOWN_LONG, DOWN_SHORT), privateProperty));
        driveway.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), dirtRoad));
        driveway.connectLocation(new LocationConnection(List.of(NW_LONG, NW_SHORT), footPath));
        dynamiteHoles.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), mustyBend));
        dynamiteHoles.connectLocation(new LocationConnection(List.of(SW_LONG, SW_SHORT, UP_LONG, UP_SHORT), narrowCorridor));
        dynamiteHoles.connectLocation(new LocationConnection(List.of(DOWN_LONG, DOWN_SHORT), crumpledMineCart));
        eastEndOfSideStreet.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), roadInValley));
        eastEndOfSideStreet.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), outsideTavern));
        eastEndOfSideStreet.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), topOfStairs));
        fieldsOfGrass.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), roadInValley));
        fieldsOfGrass.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), mountainPass));
        fieldsOfGrass.connectLocation(new LocationConnection(List.of(UP_LONG, UP_SHORT), mountainPass));
        footPath.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), driveway));
        footPath.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), topOfHill));
        graniteRoom.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), undergroundLakeSE));
        graniteRoom.connectLocation(new LocationConnection(List.of(OUT, EXIT), undergroundLakeSE));
        insideLogCabin.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT, OUT, EXIT), outsideLogCabin));
        insideLogCabin.connectLocation(new LocationConnection(List.of(UP_LONG, UP_SHORT), upstairsLogCabin));
        intersection.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT, UP_LONG, UP_SHORT), topOfHill));
        intersection.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), tailings));
        intersection.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), dirtRoad));
        insideTavern.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT, OUT, EXIT), outsideTavern));
        lake.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), dam));
        lake.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), tailings));
        lakeTown.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT, UP_LONG, UP_SHORT), dam));
//        lakeTown.connectLocation(new ConnectingLocation(List.of(WEST, W), farther into the town));
        lightningTree.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), ditch));
        mineShaft.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), undergroundLakeWest));
        mineShaft.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT, OUT, EXIT), mineEntrance));
        mineEntrance.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), tailings));
        mineEntrance.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT, IN, ENTER), mineShaft));
        mountainPass.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT, DOWN_LONG, DOWN_SHORT), fieldsOfGrass));
//        mountainPass.connectLocation(new ConnectingLocation(List.of(WEST, W), mine cage));
        mustyBend.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), dynamiteHoles));
        mustyBend.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), dankPassage));
        narrowCorridor.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), dankPassage));
        narrowCorridor.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), dirtyPassage));
        narrowCorridor.connectLocation(new LocationConnection(List.of(NE_LONG, NE_SHORT, DOWN_LONG, DOWN_SHORT), dynamiteHoles));
        outsideLogCabin.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), westEndOfSideStreet));
        outsideLogCabin.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT, IN, ENTER), insideLogCabin));
        outsideTavern.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), eastEndOfSideStreet));
//        outsideTavern.connectLocation(new ConnectingLocation(List.of(SOUTH, S), head towards colorado springs?));
        outsideTavern.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT, IN, ENTER), insideTavern));
        picnicTable.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), shed));
        picnicTable.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), privateProperty));
        privateProperty.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), archeryRange));
        privateProperty.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT, UP_LONG, UP_SHORT), driveway));
        privateProperty.connectLocation(new LocationConnection(List.of(NE_LONG, NE_SHORT), picnicTable));
        roadInValley.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), fieldsOfGrass));
        roadInValley.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), eastEndOfSideStreet));
        shed.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), picnicTable));
        tailings.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), mineEntrance));
        tailings.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), lake));
        tailings.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), intersection));
        topOfHill.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT, DOWN_LONG, DOWN_SHORT), intersection));
        topOfHill.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), footPath));
        topOfStairs.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT, DOWN_LONG, DOWN_SHORT), dam));
        topOfStairs.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), eastEndOfSideStreet));
        topOfStairs.connectLocation(new LocationConnection(List.of(WEST_LONG, WEST_SHORT), westEndOfSideStreet));
        undergroundLakeWest.connectLocation(new LocationConnection(List.of(SOUTH_LONG, SOUTH_SHORT), mineShaft));
        undergroundLakeWest.connectLocation(new LocationConnection(List.of(IN, ENTER), boat));
        undergroundLakeNE.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), dirtyPassage));
        undergroundLakeNE.connectLocation(new LocationConnection(List.of(IN, ENTER), boat));
        undergroundLakeSE.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), graniteRoom));
        undergroundLakeSE.connectLocation(new LocationConnection(List.of(IN, ENTER), boat));
        upstairsLogCabin.connectLocation(new LocationConnection(List.of(DOWN_LONG, DOWN_SHORT), insideLogCabin));
        westEndOfSideStreet.connectLocation(new LocationConnection(List.of(NORTH_LONG, NORTH_SHORT), outsideLogCabin));
        westEndOfSideStreet.connectLocation(new LocationConnection(List.of(EAST_LONG, EAST_SHORT), topOfStairs));

        lifeCount = 3;

        // Set start location by setting it as visited and creating a new Game object with that location as the current location
        // Initialize member variable startLocation so it's available to other classes
        ///// Tip: Change start location for manual debugging. /////
        startLocation = driveway;
        startLocation.setVisited(true);

        ///// Also helpful for manual debugging to change start location and add items to it /////
//        Game game = new Game(new ArrayList<>(), ditch, false);
//        game.addItemToInventory(new Item(5, "There is a jar here", "Jar", "jar"));
//        game.addItemToInventory(new Item(3, "There is a bow here, strung and ready for shooting", "Bow", "bow"));
//        game.die();
//        game.addItemToInventory(new Item(4, "There is an arrow here", "Arrow", "arrow"));
//        game.addItemToInventory(magnet);
//        return game;
        /////

        return new Game(new ArrayList<>(), driveway, status);
    }

    public String restartGame() {
        this.game = initializeGame(GameStatus.IN_PROGRESS);
        return this.game.getCurrentLocation().getDescription();
    }
}
