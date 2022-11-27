package com.project.textadventure.game;

import com.project.textadventure.game.Locations.Dam;
import com.project.textadventure.game.Locations.Location;
import com.project.textadventure.game.Locations.MineEntrance;
import com.project.textadventure.game.Locations.Shed;

import java.util.*;

public class GameState {
    private static GameState instance;
    private Map<UUID, Player> map = new HashMap<>();
    private Player player;

    private GameState() {
    }

    public static GameState getInstance() {
        if(instance == null) {
            instance = new GameState();
        }
        return instance;
    }

//    public Map<UUID, LocationGraph> getMap() {
//        return map;
//    }

    public Player getGame() {
        if(player == null) {
            player = createGame();
        }
        return player;
    }

//    private Location getGame(UUID userId) {
//        Player player = map.get(userId);
//        if(player == null) {
//            player = createGame();
//        }
//        return player;
//    }

    private Player createGame() {
        String antHillDescription = "Nearby is an ant hill with little black ants scurrying about doing their business."; //TODO if input is "dig" and you have the shovel, reveal a salamander in ant hill. Then you can get the salamander
        String archeryRangeDescription = "You step in front of two archery targets made of hay bales and spray-painted circles that are in an archery range made from a rope tied to four trees, creating a rectangle. There's a ditch to the north and a long driveway leading south.";
        String boatDescription = "You're sitting in a rickety wooden boat in a large underground lake with passages to the west, east, and northeast.";
        String bottomOfVerticalMineShaftDescription = "You are at the bottom of a vertical mine shaft with a working mine cage. Next to the mine cage is a button labeled \"Up/Down\". A mine railway starts here and runs west";
        String brokenRockDescription = "The only thing up here is a bunch of broken rock.";
        String damDescription = "You're on a short dam that created this lake by stopping up a large river. The dam goes north and south along the east end of the lake. Close by is a wheel with its axel extending deep into the dam. Its orange metal has faded to rust except for some different metal at the center, shining in the sun. South leads around the lake and to the north there's a set of stairs.";
        String dankPassageDescription = "The walls and floor here are wet. The dank atmosphere of this place makes it a little muggy. The rails go west and you can see some metal equipment to the east.";
        String dirtRoadDescription = "You are on a badly washboarded dirt road in dire need of maintenance that extends far west. It runs winding down the hill to the east. Pine forests hug the road on both sides.";
        String dirtyPassageDescription = "You're in a dirty broken passage. To the west the passage gets wider. To the east the passage narrows. Above you part of the ceiling caved in, leaving a hole and a pile of debris that cover the rails.";
        String ditchDescription = "You are in the middle of the forest standing in a small ditch running east and west.";
        String drivewayDescription = "You are at the west end of a dirt road surrounded by a forest of pine trees. There is a small gap to the north that exposes a steep, dirt driveway sloping down into the forest. Looking down the road to the east you can see over the trees and into the valley. There you see what might be the shimmering of a lake in the mountain sun. There's also a foot path going northwest.";
        String dynamiteHolesDescription = "There are a bunch of holes drilled in the wall here. The miners of old must have thought about using dynamite to blow up this part but changed their minds. The rails split again. The railway now leads down, north, and up a slope to the sw.";
        String eastEndOfSideStreetDescription = "You are at the east end of a side street in an abandoned gold mining town. The main street goes north and south form here.";
        String fieldsOfGrassDescription = "Fields of grass surround you. The road goes south into the valley and to the east it winds up the mountain, turning north.";
        String footPathDescription = "You're on a foot path in the middle of a dense forest. Large pine trees are all around you. The path goes south and east.";
        String graniteRoomDescription = "On a polished granite pedestal, black as night, in the middle of this room with walls of the same black rock sits a plastic puzzle. It is a 3 x 3 x 3 cube with different colored stickers for each side in stark contrast of the black consuming the room. The sides can be turned. It is scrambled. The only exit is to the west.";
        String insideLogCabinDescription = "You are inside a well-kept log cabin with a huge fireplace burning a magnificent fire inside. There's a little wooden sign hanging on the wall next to the fireplace that reads \"Run\" with 5 rectangles in a line carved into the wood. In one corner there's a spiral staircase going upstairs."; // TODO Put a deck of cards somewhere and fill the 5 rectangles with a run of cards
        String intersectionDescription = "You have reached an intersection in the road. It leads into the forest to the north and west. The southern road goes into a thinner part of the forest. The shimmering, which is now in the south, looks very much like a lake now.";
        String lakeDescription = "You are on the south side of a lake. The water sparkles in the intense sun and you can see far into the clear water but the lake is very deep and there's nothing to see but lake bottom from here. There's a path going west and there's a dam to the north.";    // TODO Get rid of lake when wheel turned
        String lakeTownDescription = "You are in what once was a charming little town. Now there is dripping wet plant life from the recently drained lake clinging to the buildings. The muddy ground squelches as you walk. To the east is the dam and you can go farther into the town to the west.";
        String lightningTreeDescription = "You're in a little clearing with a large tree in the middle that looks like it was struck by lightning a long time ago. The bark has long since fallen off and the remaining part of the tree is a reddish color.";
        String mineEntranceDescription = "You've come to the entrance to this abandoned gold mine. The supports on it are looking a little worn and there are some loose nails that might come in handy if you could safely get them out of the rotten wood. You could enter to the east if you're very careful. Piles of tailings are all over leaving one path away from the entrance to the north.";
        String mineShaftDescription = "This is the mine shaft. It looks like it could cave in at any moment. There's a small wooden sign here that says \"BEWARE OF TOMMYKNOCKERS\" with an ugly picture of one the green-skinned, dwarf-sized creatures. The mine shaft continues east.";
        String mustyBendDescription = "You're at a bend in the rails with a musty feel in the air. The rails go east and south here.";
        String narrowCorridorDescription = "You are in a long, narrow corridor. At the end of the corridor the rails split. One set going down to the ne and one continuing on north. At the western end the corridor widens a bit.";
        String outsideLogCabinDescription = "You are in front of a log cabin that looks much less run-down than the rest of the town. A warm light shines from the windows. A road goes south.";
        String outsideMineCageDescription = "";
        String outsideTavernDescription = "On the east side of the road is a tavern with a wooden sign hanging above the door with \"Tommyknocker Tavern\" etched into it. Here is the former heartbeat of the town, now a dump of a place. Its roof has caved in and the walls look as if they could collapse at any moment. The road goes north from here.";
        String picnicTableDescription = "A sturdy looking picnic table is in this little clearing you've stepped into and farther north a shed peeks through the trees. A tidy trail leads south.";
        String privatePropertyDescription = "All around you is a dense pine forest that gives the air a friendly smell. There are a couple \"public Property! Keep Off!\" signs nailed to trees, but no gates or anything so the owners aren't too concerned about dealing with trespassers. A driveway continues north and a tidy trail leads off into the forest to the northeast.";
        String roadInValleyDescription = "You're on a north-south road in the middle of a lush, green valley with grazing pastures all around. There is a little stream runs under the road where you are.";
        String rubyOnRailsDescription = "You've reached a dead end. A crumpled mine cart, no longer able to run on the rails, has fallen on its side and dumped a large ruby onto the rails.";
        String shedDescription = "Here is a cheerful shed with wood matching that of the picnic table's. Its doors are firmly shut and locked, the one and only thing that is on this plot of land.";
        String insideTavernDescription = "The Tommyknocker Tavern looks just as shabby on the inside as it does on the outside with all of its furnishings falling apart and a dusty smell in the air.";
        String tailingsDescription = "All around are piles of tailings that look like they have been puked into this valley. There's not much else to be seen except the entrance to a mine to the south. The shimmering is definitely a lake and there's a path leading in that direction and another to the north.";
        String topOfHillDescription = "You are at the top of a steep hill and have a magnificent view of the lush, green valley. The road goes into the trees to the west and down the hill to the south.";
        String mountainPassDescription = "The road you're on has reached a mountain pass. This vantage point gives a magnificent view of the valley. The road goes down the mountain to the south and there's a working mine cage to the west.";
        String topOfStairsDescription = "You are at the top of a set of wooden stairs embedded in the hill. The stairs are next to a street running east and west in the abandoned gold mining town. A dam is to the south at the bottom of the stairs.";
        String upstairsLogCabinDescription = "The second floor of this cabin isn't nearly as well kept as the first floor. There are cob webs all over and dust blankets every uncovered surface. There's a spiral staircase going back to the first floor.";
        String undergroundLakeWestDescription = "You are on the west side of a large underground lake with a rickety wooden boat at the shore. It looks like the place flooded long after it was abandoned. There are two passages across the lake from where you are standing: one going east and one going northeast. There's a dim light coming from around a corner to the south.";
        String undergroundLakeEastDescription = "You are on the east side of a large underground lake. There are passages to the west an ne across the lake. There's a dim light coming from the west one and the tunnel you're in now continues to the east.";
        String undergroundLakeNEDescription = "You are on the ne side of a large underground lake. There are passages to the west and east across the lake. Mine rails lead into a passage to the east from where you are.";
        String westEndOfSideStreetDescription = "You are at the west and of a side street in an abandoned gold mining town. Branching off from this street is a smaller road to the north.";

        Item key = new Item(1, "There is a shiny key here", "Shiny key", "key");
        Item gold = new Item(6, "There are some gold flakes on the ground here.", "Gold flakes in jar", "gold");
        Item magnet = new Item(10, "There is a thick, circular magnet here, about the size of your palm.", "Magnet", "magnet");
        Item ruby = new Item(11, "A large ruby lays on the ground", "Ruby", "ruby");
//        Item cube = new Cube(12, "There is a plastic cube puzzle lying on the ground", "Cube", "cube", false, false);

        Location antHill = new Location(antHillDescription, "You're at a large ant hill.", new ArrayList<>(), new ArrayList<>(), false, "ant hill");
        Location archeryRange = new Location(archeryRangeDescription, "You're at Archery Range", new ArrayList<>(), new ArrayList<>(), false, "archery range");
        Location bottomOfVerticalMineShaft = new Location(bottomOfVerticalMineShaftDescription, "You are at the bottom of a vertical mine shaft", new ArrayList<>(), new ArrayList<>(), false, "bottom of vertical mine shaft");
        Location brokenRock = new Location(brokenRockDescription, "The only thing up here is a bunch of broken rock.", new ArrayList<>(), new ArrayList<>(), false, "broken rock");
        Location dankPassage = new Location(dankPassageDescription, "You're at Dank Passage", new ArrayList<>(), new ArrayList<>(), false, "dank passage");
        Location dirtRoad = new Location(dirtRoadDescription, "You're at Dirt Road", new ArrayList<>(), new ArrayList<>(), false, "dirt road");
        Location dirtyPassage = new Location(dirtyPassageDescription, "You're at Dirty Passage", new ArrayList<>(), new ArrayList<>(), false, "dirty passage");
        Location ditch = new Location(ditchDescription, "You're in a ditch", new ArrayList<>(Collections.singletonList(key)), new ArrayList<>(), false, "ditch");
        Location driveway = new Location(drivewayDescription, "You're at Driveway", new ArrayList<>(), new ArrayList<>(), false, "driveway");
        Location dynamiteHoles = new Location(dynamiteHolesDescription, "You're by a section of wall with holes drilled for dynamite", new ArrayList<>(), new ArrayList<>(), false, "dynamite holes");
        Location eastEndOfSideStreet = new Location(eastEndOfSideStreetDescription, "You're at the east end of the main street", new ArrayList<>(), new ArrayList<>(), false, "east end of side street");
        Location fieldsOfGrass = new Location(fieldsOfGrassDescription, "You're surrounded by fields of grass", new ArrayList<>(), new ArrayList<>(), false, "fields of grass");
        Location footPath = new Location(footPathDescription, "You're on a foot path", new ArrayList<>(), new ArrayList<>(), false, "foot path");
        Location insideLogCabin = new Location(insideLogCabinDescription, "You're inside Log Cabin", new ArrayList<>(Collections.singletonList(magnet)), new ArrayList<>(), false, "inside log cabin");
        Location intersection = new Location(intersectionDescription, "You're at an intersection in the road", new ArrayList<>(), new ArrayList<>(), false, "intersection");
        Location lake = new Location(lakeDescription, "You're on the south side of a lake", new ArrayList<>(), new ArrayList<>(), false, "lake");
        Location lakeTown = new Location(lakeTownDescription, "You are next to Lake Town", new ArrayList<>(), new ArrayList<>(), false, "lake town");
        Location lightningTree = new Location(lightningTreeDescription, "You're at Lightning Tree", new ArrayList<>(), new ArrayList<>(), false, "lightning tree");
        Location mineShaft = new Location(mineShaftDescription, "You're in Mine Shaft", new ArrayList<>(), new ArrayList<>(), false, "mine shaft");
        Location mountainPass = new Location(mountainPassDescription, "You're at the top of a mountain pass", new ArrayList<>(), new ArrayList<>(), false, "mountain pass");
        Location mustyBend = new Location(mustyBendDescription, "You're at a musty bend", new ArrayList<>(), new ArrayList<>(), false, "musty bend");
        Location narrowCorridor = new Location(narrowCorridorDescription, "You're in a narrow corridor", new ArrayList<>(), new ArrayList<>(), false, "narrow corridor");
        Location outsideLogCabin = new Location(outsideLogCabinDescription, "You're outside Log Cabin", new ArrayList<>(), new ArrayList<>(), false, "outside log cabin");
        Location outsideTavern = new Location(outsideTavernDescription, "You're outside Tommyknocker Tavern", new ArrayList<>(), new ArrayList<>(), false, "outside Tommyknocker Tavern");
        Location picnicTable = new Location(picnicTableDescription, "You're at Picnic Table", new ArrayList<>(), new ArrayList<>(), false, "picnic table");
        Location privateProperty = new Location(privatePropertyDescription, "You're at Private Property", new ArrayList<>(), new ArrayList<>(), false, "private property");
        Location roadInValley = new Location(roadInValleyDescription, "You're on a road in a valley", new ArrayList<>(), new ArrayList<>(), false, "road in a valley");
        Location insideTavern = new Location(insideTavernDescription, "You're inside the Tommyknocker Tavern", new ArrayList<>(), new ArrayList<>(), false, "Tommyknocker Tavern");
        Location tailings = new Location(tailingsDescription, "You're amongst piles of tailings", new ArrayList<>(), new ArrayList<>(), false, "abandoned gold mine");
        Location topOfHill = new Location(topOfHillDescription, "You're at Top of Hill", new ArrayList<>(), new ArrayList<>(), false, "top of hill");
        Location topOfStairs = new Location(topOfStairsDescription, "You're at Top of Stairs", new ArrayList<>(), new ArrayList<>(), false, "top of stairs");
        Location undergroundLakeWest = new Location(undergroundLakeWestDescription, "You're on the west side of an underground lake", new ArrayList<>(), new ArrayList<>(), false, "underground lake west");
        Location undergroundLakeEast = new Location(undergroundLakeEastDescription, "You're on the east side of an underground lake", new ArrayList<>(), new ArrayList<>(), false, "underground lake east");
        Location undergroundLakeNE = new Location(undergroundLakeNEDescription, "You're on the ne side of an underground lake", new ArrayList<>(), new ArrayList<>(), false, "underground lake ne");
        Location upstairsLogCabin = new Location(upstairsLogCabinDescription, "You're on the second floor of the log cabin", new ArrayList<>(), new ArrayList<>(), false, "upstairs log cabin");
        Location westEndOfSideStreet = new Location(westEndOfSideStreetDescription, "You're at the west end of the main street", new ArrayList<>(), new ArrayList<>(), false, "west end of side street");
//        Boat boat = new Boat(boatDescription, "You're in a rickety wooden boat in an underground lake", new ArrayList<>(), new ArrayList<>(), new Boat(), false, "rickety wooden boat", false);
        Dam dam = new Dam(damDescription, "You're at Dam", new ArrayList<>(), new ArrayList<>(), false, "dam", false, false);
//        GraniteRoom graniteRoom = new GraniteRoom(graniteRoomDescription, "You're at Granite Room", new ArrayList<>(Collections.singletonList(cube)), new ArrayList<>(), false, "granite room", false);
        MineEntrance mineEntrance = new MineEntrance(mineEntranceDescription, "You're at the entrance of an abandoned gold mine", new ArrayList<>(Collections.singletonList(gold)), new ArrayList<>(), false, "mine entrance", false);
//        RubyOnRails rubyOnRails = new RubyOnRails(rubyOnRailsDescription, "You're at Ruby On Rails", new ArrayList<>(Collections.singletonList(ruby)), new ArrayList<>(), false, "ruby on rails", false);
        Shed shed = new Shed(shedDescription, "You're standing before a cheerful little shed", new ArrayList<>(), new ArrayList<>(), false, "shed", false, false);

        antHill.connectLocation(new ConnectingLocation(List.of("west", "w"), ditch));
        archeryRange.connectLocation(new ConnectingLocation(List.of("north", "n"), ditch));
        archeryRange.connectLocation(new ConnectingLocation(List.of("south", "s"), privateProperty));
//        boat.connectLocation(new ConnectingLocation(List.of("east", "e"), undergroundLakeEast));
//        boat.connectLocation(new ConnectingLocation(List.of("east", "e"), undergroundLakeEast));
//        boat.connectLocation(new ConnectingLocation(List.of("west", "w"), undergroundLakeWest));
//        boat.connectLocation(new ConnectingLocation(List.of("northeast", "ne"), undergroundLakeNE));
        bottomOfVerticalMineShaft.connectLocation(new ConnectingLocation(List.of("west", "w"), dankPassage));
//        bottomOfVerticalMineShaft.connectLocation.(new ConnectingLocation(List.of("in", "enter"), mine cage));
        brokenRock.connectLocation(new ConnectingLocation(List.of("down", "d"), dirtyPassage));
        dam.connectLocation(new ConnectingLocation(List.of("north", "n"), topOfStairs));
        dam.connectLocation(new ConnectingLocation(List.of("south", "s"), lake));
        dam.connectLocation(new ConnectingLocation(List.of("up", "u"), topOfStairs));
//        dam.connectLocation(new ConnectingLocation(null, lakeTown));
//        dam.connectLocation(new ConnectingLocation(null, lakeTown));
        dankPassage.connectLocation(new ConnectingLocation(List.of("south", "s"), narrowCorridor));
        dankPassage.connectLocation(new ConnectingLocation(List.of("east", "e"), bottomOfVerticalMineShaft));
        dankPassage.connectLocation(new ConnectingLocation(List.of("west", "w"), mustyBend));
        dirtRoad.connectLocation(new ConnectingLocation(List.of("east", "e"), intersection));
        dirtRoad.connectLocation(new ConnectingLocation(List.of("west", "w"), driveway));
        dirtyPassage.connectLocation(new ConnectingLocation(List.of("east", "e"), narrowCorridor));
        dirtyPassage.connectLocation(new ConnectingLocation(List.of("west", "w"), undergroundLakeNE));
        dirtyPassage.connectLocation(new ConnectingLocation(List.of("up", "u"), brokenRock));
        ditch.connectLocation(new ConnectingLocation(List.of("south", "s"), archeryRange));
        ditch.connectLocation(new ConnectingLocation(List.of("east", "e"), antHill));
        ditch.connectLocation(new ConnectingLocation(List.of("west", "w"), lightningTree));
        driveway.connectLocation(new ConnectingLocation(List.of("north", "n"), privateProperty));
        driveway.connectLocation(new ConnectingLocation(List.of("down", "d"), privateProperty));
        driveway.connectLocation(new ConnectingLocation(List.of("east", "e"), dirtRoad));
        driveway.connectLocation(new ConnectingLocation(List.of("northwest", "nw"), footPath));
        dynamiteHoles.connectLocation(new ConnectingLocation(List.of("north", "n"), mustyBend));
        dynamiteHoles.connectLocation(new ConnectingLocation(List.of("southwest", "sw"), narrowCorridor));
        dynamiteHoles.connectLocation(new ConnectingLocation(List.of("up", "u"), narrowCorridor));
//        dynamiteHoles.connectingLocations.add(new ConnectingLocation(down, rubyOnRails));
        eastEndOfSideStreet.connectLocation(new ConnectingLocation(List.of("north", "n"), roadInValley));
        eastEndOfSideStreet.connectLocation(new ConnectingLocation(List.of("south", "s"), outsideTavern));
        eastEndOfSideStreet.connectLocation(new ConnectingLocation(List.of("west", "w"), topOfStairs));
        fieldsOfGrass.connectLocation(new ConnectingLocation(List.of("south", "s"), roadInValley));
        fieldsOfGrass.connectLocation(new ConnectingLocation(List.of("east", "e"), mountainPass));
        fieldsOfGrass.connectLocation(new ConnectingLocation(List.of("up", "u"), mountainPass));
        footPath.connectLocation(new ConnectingLocation(List.of("south", "s"), driveway));
        footPath.connectLocation(new ConnectingLocation(List.of("east", "e"), topOfHill));
//        graniteRoom.connectLocation(new ConnectingLocation(List.of("west", "w"), undergroundLakeEast));
//        graniteRoom.connectLocation(new ConnectingLocation(List.of("out", "exit"), undergroundLakeEast));
        insideLogCabin.connectLocation(new ConnectingLocation(List.of("east", "e"), outsideLogCabin));
        insideLogCabin.connectLocation(new ConnectingLocation(List.of("up", "u"), upstairsLogCabin));
        insideLogCabin.connectLocation(new ConnectingLocation(List.of("out", "exit"), outsideLogCabin));
        intersection.connectLocation(new ConnectingLocation(List.of("north", "n"), topOfHill));
        intersection.connectLocation(new ConnectingLocation(List.of("south", "s"), tailings));
        intersection.connectLocation(new ConnectingLocation(List.of("west", "w"), dirtRoad));
        insideTavern.connectLocation(new ConnectingLocation(List.of("west", "w"), outsideTavern));
        insideTavern.connectLocation(new ConnectingLocation(List.of("out", "exit"), outsideTavern));
        lake.connectLocation(new ConnectingLocation(List.of("north", "n"), dam));
        lake.connectLocation(new ConnectingLocation(List.of("west", "w"), tailings));
        lakeTown.connectLocation(new ConnectingLocation(List.of("east", "e"), dam));
//        lakeTown.connectLocation(new ConnectingLocation(List.of("west", "w"), farther into the town));
        lakeTown.connectLocation(new ConnectingLocation(List.of("up", "u"), dam));
        lightningTree.connectLocation(new ConnectingLocation(List.of("east", "e"), ditch));
        mineShaft.connectLocation(new ConnectingLocation(List.of("east", "e"), undergroundLakeWest));
        mineShaft.connectLocation(new ConnectingLocation(List.of("west", "w"), mineEntrance));
        mineShaft.connectLocation(new ConnectingLocation(List.of("out", "exit"), mineEntrance));
        mineEntrance.connectLocation(new ConnectingLocation(List.of("north", "n"), tailings));
        mineEntrance.connectLocation(new ConnectingLocation(List.of("east", "e"), mineShaft));
        mineEntrance.connectLocation(new ConnectingLocation(List.of("in", "enter"), mineShaft));
        mountainPass.connectLocation(new ConnectingLocation(List.of("south", "s"), fieldsOfGrass));
//        mountainPass.connectLocation(new ConnectingLocation(List.of("west", "w"), mine cage));
        mountainPass.connectLocation(new ConnectingLocation(List.of("down", "d"), fieldsOfGrass));
        mustyBend.connectLocation(new ConnectingLocation(List.of("south", "s"), dynamiteHoles));
        mustyBend.connectLocation(new ConnectingLocation(List.of("east", "e"), dankPassage));
        narrowCorridor.connectLocation(new ConnectingLocation(List.of("north", "n"), dankPassage));
        narrowCorridor.connectLocation(new ConnectingLocation(List.of("west", "w"), dirtyPassage));
        narrowCorridor.connectLocation(new ConnectingLocation(List.of("northeast", "ne"), dynamiteHoles));
        narrowCorridor.connectLocation(new ConnectingLocation(List.of("down", "d"), dynamiteHoles));
        outsideLogCabin.connectLocation(new ConnectingLocation(List.of("south", "s"), westEndOfSideStreet));
        outsideLogCabin.connectLocation(new ConnectingLocation(List.of("west", "w"), insideLogCabin));
        outsideLogCabin.connectLocation(new ConnectingLocation(List.of("in", "enter"), insideLogCabin));
        outsideTavern.connectLocation(new ConnectingLocation(List.of("north", "n"), eastEndOfSideStreet));
//        outsideTavern.connectLocation(new ConnectingLocation(List.of("south", "s"), head towards colorado springs?));
        outsideTavern.connectLocation(new ConnectingLocation(List.of("east", "e"), insideTavern));
        outsideTavern.connectLocation(new ConnectingLocation(List.of("in", "enter"), insideTavern));
        picnicTable.connectLocation(new ConnectingLocation(List.of("north", "n"), shed));
        picnicTable.connectLocation(new ConnectingLocation(List.of("south", "s"), privateProperty));
        privateProperty.connectLocation(new ConnectingLocation(List.of("north", "n"), archeryRange));
        privateProperty.connectLocation(new ConnectingLocation(List.of("south", "s"), driveway));
        privateProperty.connectLocation(new ConnectingLocation(List.of("up", "u"), driveway));
        privateProperty.connectLocation(new ConnectingLocation(List.of("northeast", "ne"), picnicTable));
        roadInValley.connectLocation(new ConnectingLocation(List.of("north", "n"), fieldsOfGrass));
        roadInValley.connectLocation(new ConnectingLocation(List.of("south", "s"), eastEndOfSideStreet));
//        rubyOnRails.connectLocation(new ConnectingLocation(List.of("up", "u"), dynamiteHoles));
        shed.connectLocation(new ConnectingLocation(List.of("south", "s"), picnicTable));
        tailings.connectLocation(new ConnectingLocation(List.of("south", "s"), mineEntrance));
        tailings.connectLocation(new ConnectingLocation(List.of("east", "e"), lake));
        tailings.connectLocation(new ConnectingLocation(List.of("north", "n"), intersection));
        topOfHill.connectLocation(new ConnectingLocation(List.of("south", "s"), intersection));
        topOfHill.connectLocation(new ConnectingLocation(List.of("west", "w"), footPath));
        topOfHill.connectLocation(new ConnectingLocation(List.of("down", "d"), intersection));
        topOfStairs.connectLocation(new ConnectingLocation(List.of("south", "s"), dam));
        topOfStairs.connectLocation(new ConnectingLocation(List.of("east", "e"), eastEndOfSideStreet));
        topOfStairs.connectLocation(new ConnectingLocation(List.of("west", "w"), westEndOfSideStreet));
        topOfStairs.connectLocation(new ConnectingLocation(List.of("down", "d"), dam));
        undergroundLakeWest.connectLocation(new ConnectingLocation(List.of("south", "s"), mineShaft));
//        undergroundLakeWest.connectLocation(new ConnectingLocation(List.of("in", "enter"), boat));
        undergroundLakeNE.connectLocation(new ConnectingLocation(List.of("east", "e"), dirtyPassage));
//        undergroundLakeNE.connectLocation(new ConnectingLocation(List.of("in", "enter"), boat));
//        undergroundLakeEast.connectLocation(new ConnectingLocation(List.of("east", "e"), graniteRoom));
//        undergroundLakeEast.connectLocation(new ConnectingLocation(List.of("in", "enter"), boat));
        upstairsLogCabin.connectLocation(new ConnectingLocation(List.of("down", "d"), insideLogCabin));
        westEndOfSideStreet.connectLocation(new ConnectingLocation(List.of("north", "n"), outsideLogCabin));
        westEndOfSideStreet.connectLocation(new ConnectingLocation(List.of("east", "e"), topOfStairs));

        driveway.setVisited(true);
//        return new Player(new ArrayList<>(), driveway, false);
        Player player = new Player(new ArrayList<>(), dam, false);
//        player.addItemToInventory(new Item(5, "There is a jar here", "Jar", "jar"));
        return player;

    }
}
