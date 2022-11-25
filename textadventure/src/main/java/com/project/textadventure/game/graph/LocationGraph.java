package com.project.textadventure.game.graph;

import com.project.textadventure.game.Item;
import com.project.textadventure.game.Locations.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LocationGraph {

    public final String tailingsDescription = "All around are piles of tailings that look like they have been puked into this valley. There's not\nmuch else to be seen except the entrance to a mine to the south. The shimmering is definitely a lake\nand there's a path leading in that direction and another to the north.";
    public final String antHillDescription = "Nearby is an ant hill with little black ants scurrying about doing their business."; //TODO if input is "dig" and you have the shovel, reveal a salamander in ant hill. Then you can get the salamander
    public final String archeryRangeDescription = "You step in front of two archery targets made of hay bales and spray-painted circles that are in an\narchery range made from a rope tied to four trees, creating a rectangle. There's a ditch to the north\nand a long driveway leading south.";
    public final String boatDescription = "You're sitting in a rickety wooden boat in a large underground lake with passages to the west,\neast, and northeast.";
    public final String bottomOfVerticalMineShaftDescription = "You are at the bottom of a vertical mine shaft with a working mine cage. Next to the mine cage is a\nbutton labeled \"Up/Down\". A mine railway starts here and runs west";
    public final String brokenRockDescription = "The only thing up here is a bunch of broken rock.";
    public final String damDescription = "You're on a short dam that created this lake by stopping up a large river. The dam\ngoes north and south along the east end of the lake. Close by is a wheel with its axel extending\ndeep into the dam. Its orange metal has faded to rust except for some different metal at the center,\nshining in the sun. South leads around the lake and to the north there's a set of stairs.";
    public final String dankPassageDescription = "The walls and floor here are wet. The dank atmosphere of this place makes it a little muggy. The\nrails go west and you can see some metal equipment to the east.";
    public final String dirtRoadDescription = "You are on a badly washboarded dirt road in dire need of maintenance that extends far west. It runs\nwinding down the hill to the east. Pine forests hug the road on both sides.";
    public final String dirtyPassageDescription = "You're in a dirty broken passage. To the west the passage gets wider. To the east the passage\nnarrows. Above you part of the ceiling caved in, leaving a hole and a pile of debris that cover the\nrails.";
    public final String ditchDescription = "You are in the middle of the forest standing in a small ditch running east and west.";
    public final String drivewayDescription = "You are at the west end of a dirt road surrounded by a forest of pine trees. There is a\nsmall gap to the north that exposes a steep, dirt driveway sloping down into the forest. Looking\ndown the road to the east you can see over the trees and into the valley. There you see what might\nbe the shimmering of a lake in the mountain sun. There's also a foot path going northwest.";
    public final String dynamiteHolesDescription = "There are a bunch of holes drilled in the wall here. The miners of old must have thought about\nusing dynamite to blow up this part but changed their minds. The rails split again. The railway now\nleads down, north, and up a slope to the sw.";
    public final String eastEndOfSideStreetDescription = "You are at the east end of a side street in an abandoned gold mining town. The main street goes\nnorth and south form here.";
    public final String fieldsOfGrassDescription = "Fields of grass surround you. The road goes south into the valley and to the east it winds up the\nmountain, turning north.";
    public final String footPathDescription = "You're on a foot path in the middle of a dense forest. Large pine trees are all around you. The path\ngoes south and east.";
    public final String graniteRoomDescription = "On a polished granite pedestal, black as night, in the middle of this room\nwith walls of the same black rock sits a plastic puzzle. It is a 3 x 3 x 3 cube with different\ncolored stickers for each side in stark contrast of the black consuming the room. The sides can be\nturned. It is scrambled. The only exit is to the west.";
    public final String insideLogCabinDescription = "You are inside a well-kept log cabin with a huge fireplace burning a magnificent fire inside.\nThere's a little wooden sign hanging on the wall next to the fireplace that reads \"Run\" with 5\nrectangles in a line carved into the wood. In one corner there's a spiral staircase going upstairs."; // TODO Put a deck of cards somewhere and fill the 5 rectangles with a run of cards
    public final String intersectionDescription = "You have reached an intersection in the road. It leads into the forest to the north and west. The\nsouthern road goes into a thinner part of the forest. The shimmering, which is now in the south,\nlooks very much like a lake now.";
    public final String lakeDescription = "You are on the south side of a lake. The water sparkles in the intense sun and you can see far into\nthe clear water but the lake is very deep and there's nothing to see but lake bottom from here.\nThere's a path going west and there's a dam to the north.";    // TODO Get rid of lake when wheel turned
    public final String lakeTownDescription = "You are in what once was a charming little town. Now there is dripping wet plant life from the\nrecently drained lake clinging to the buildings. The muddy ground squelches as you walk. To the\neast is the dam and you can go farther into the town to the west.";
    public final String lightningTreeDescription = "You're in a little clearing with a large tree in the middle that looks like it was struck by\nlightning a long time ago. The bark has long since fallen off and the remaining part of the tree\nis a reddish color.";
    public final String mineEntranceDescription = "You've come to the entrance to this abandoned gold mine. The supports on it are looking a little\nworn and there are some loose nails that might come in handy if you could safely get them out of\nthe rotten wood. You could enter to the east if you're very careful. Piles of tailings are all\nover leaving one path away from the entrance to the north.";
    public final String mineShaftDescription = "This is the mine shaft. It looks like it could cave in at any moment. There's a small wooden sign\nhere that says \"BEWARE OF TOMMYKNOCKERS\" with an ugly picture of one the green-skinned, dwarf-sized\ncreatures. The mine shaft continues east.";
    public final String mustyBendDescription = "You're at a bend in the rails with a musty feel in the air. The rails go east and south here.";
    public final String narrowCorridorDescription = "You are in a long, narrow corridor. At the end of the corridor the rails split. One set going down\nto the ne and one continuing on north. At the western end the corridor widens a bit.";
    public final String outsideLogCabinDescription = "You are in front of a log cabin that looks much less run-down than the rest of the town. A warm\nlight shines from the windows. A road goes south.";
    public final String outsideMineCageDescription = "";
    public final String outsideTavernDescription = "On the east side of the road is a tavern with a wooden sign hanging above the door with\n\"Tommyknocker Tavern\" etched into it. Here is the former heartbeat of the town, now a dump of a\nplace. Its roof has caved in and the walls look as if they could collapse at any moment. The road\ngoes north from here.";
    public final String picnicTableDescription = "A sturdy looking picnic table is in this little clearing you've stepped into and farther north a\nshed peeks through the trees. A tidy trail leads south.";
    public final String privatePropertyDescription = "All around you is a dense pine forest that gives the air a friendly smell. There are a couple\n\"public Property! Keep Off!\" signs nailed to trees, but no gates or anything so the owners aren't\ntoo concerned about dealing with trespassers. A driveway continues north and a tidy trail leads off\ninto the forest to the northeast.";
    public final String roadInValleyDescription = "You're on a north-south road in the middle of a lush, green valley with grazing pastures all around.\nThere is a little stream runs under the road where you are.";
    public final String rubyOnRailsDescription = "You've reached a dead end. A crumpled mine cart, no longer able to run on the rails, has fallen on its\nside and dumped a large ruby onto the rails.";
    public final String shedDescription = "Here is a cheerful shed with wood matching that of the picnic table's. Its doors are firmly shut\nand locked, the one and only thing that is on this plot of land.";
    public final String insideTavernDescription = "The Tommyknocker Tavern looks just as shabby on the inside as it does on the outside with all of its\nfurnishings falling apart and a dusty smell in the air.";
    public final String topOfHillDescription = "You are at the top of a steep hill and have a magnificent view of the lush, green valley. The road\ngoes into the trees to the west and down the hill to the south.";
    public final String mountainPassDescription = "The road you're on has reached a mountain pass. This vantage point gives a magnificent view of the\nvalley. The road goes down the mountain to the south and there's a working mine cage to the west.";
    public final String topOfStairsDescription = "You are at the top of a set of wooden stairs embedded in the hill. The stairs are next to a street\nrunning east and west in the abandoned gold mining town. A dam is to the south at the bottom of\nthe stairs.";
    public final String upstairsLogCabinDescription = "The second floor of this cabin isn't nearly as well kept as the first floor. There are cob webs\nall over and dust blankets every uncovered surface. There's a spiral staircase going back to the\nfirst floor.";
    public final String undergroundLakeWestDescription = "You are on the west side of a large underground lake with a rickety wooden boat at the shore. It\nlooks like the place flooded long after it was abandoned. There are two passages across the lake\nfrom where you are standing: one going east and one going northeast. There's a dim light coming from\naround a corner to the south.";
    public final String undergroundLakeEastDescription = "You are on the east side of a large underground lake. There are passages to the west an ne across\nthe lake. There's a dim light coming from the west one and the tunnel you're in now continues to the east.";
    public final String undergroundLakeNEDescription = "You are on the ne side of a large underground lake. There are passages to the west and east across\nthe lake. Mine rails lead into a passage to the east from where you are.";
    public final String westEndOfSideStreetDescription = "You are at the west and of a side street in an abandoned gold mining town. Branching off from this\nstreet is a smaller road to the north.";

    public final Item key = new Item(1, "There is a shiny key here", "Shiny key", "key");
    public final Item hammer = new Item(2, "There is a hammer here", "Hammer", "hammer");
    public final Item bow = new Item(3, "There is a bow here, strung an ready for shooting", "Bow", "bow");
    public final Item arrow = new Item(4, "There is an arrow here", "Arrow", "arrow");
    public final Item jar = new Item(5, "There is a jar here", "Jar", "jar");
    public final Item gold = new Item(6, "There are some gold flakes on the ground here.", "Gold flakes in jar", "gold");
    public final Item shovel = new Item(8, "There is a shovel here.", "Shovel", "shovel");
    public final Item tent = new Item(9, "There is a tent here, packed neatly in a bag.", "Tent in bag", "tent");
    public final Item magnet = new Item(10, "There is a thick, circular magnet here, about the size of your palm.", "Magnet", "magnet");
    public final Item ruby = new Item(11, "A large ruby lays on the ground", "Ruby", "ruby");
//    public final Item cube = new Cube(12, "There is a plastic cube puzzle lying on the ground", "Cube", "cube", false, false);

    Location antHill = new Location(antHillDescription, "You're at a large ant hill.", new ArrayList<>(), new ArrayList<>(), new Location(), false, "ant hill");
    Location archeryRange  = new Location(archeryRangeDescription, "You're at Archery Range", new ArrayList<>(), new ArrayList<>(), new Location(),false, "archery range");
    Location bottomOfVerticalMineShaft = new Location(bottomOfVerticalMineShaftDescription, "You are at the bottom of a vertical mine shaft", new ArrayList<>(), new ArrayList<>(), new Location(), false, "bottom of vertical mine shaft");
    Location brokenRock = new Location(brokenRockDescription, "The only thing up here is a bunch of broken rock.", new ArrayList<>(), new ArrayList<>(), new Location(), false, "broken rock");
    Location dankPassage = new Location(dankPassageDescription, "You're at Dank Passage", new ArrayList<>(), new ArrayList<>(), new Location(), false, "dank passage");
    Location dirtRoad = new Location(dirtRoadDescription, "You're at Dirt Road", new ArrayList<>(), new ArrayList<>(), new Location(),false, "dirt road");
    Location dirtyPassage = new Location(dirtyPassageDescription, "You're at Dirty Passage", new ArrayList<>(), new ArrayList<>(), new Location(), false, "dirty passage");
    Location ditch = new Location(ditchDescription, "You're in a ditch", new ArrayList<>(Collections.singletonList(key)), new ArrayList<>(), new Location(), false, "ditch");
    Location driveway = new Location(drivewayDescription, "You're at Driveway", new ArrayList<>(), new ArrayList<>(), new Location(), false, "driveway");
    Location dynamiteHoles = new Location(dynamiteHolesDescription, "You're by a section of wall with holes drilled for dynamite", new ArrayList<>(), new ArrayList<>(), new Location(), false, "dynamite holes");
    Location eastEndOfSideStreet = new Location(eastEndOfSideStreetDescription, "You're at the east end of the main street", new ArrayList<>(), new ArrayList<>(), new Location(), false, "east end of side street");
    Location fieldsOfGrass = new Location(fieldsOfGrassDescription, "You're surrounded by fields of grass", new ArrayList<>(), new ArrayList<>(), new Location(), false, "fields of grass");
    Location footPath = new Location(footPathDescription, "You're on a foot path", new ArrayList<>(), new ArrayList<>(), new Location(), false, "foot path");
    Location insideLogCabin = new Location(insideLogCabinDescription, "You're inside Log Cabin", new ArrayList<>(Collections.singletonList(magnet)), new ArrayList<>(), new Location(), false, "inside log cabin");
    Location intersection = new Location(intersectionDescription, "You're at an intersection in the road", new ArrayList<>(), new ArrayList<>(), new Location(), false, "intersection");
    Location lake = new Location(lakeDescription, "You're on the south side of a lake", new ArrayList<>(), new ArrayList<>(), new Location(), false, "lake");
    Location lakeTown = new Location(lakeTownDescription, "You are next to Lake Town", new ArrayList<>(), new ArrayList<>(), new Location(), false, "lake town");
    Location lightningTree = new Location(lightningTreeDescription, "You're at Lightning Tree", new ArrayList<>(), new ArrayList<>(), new Location(), false, "lightning tree");
    Location mineShaft = new Location(mineShaftDescription, "You're in Mine Shaft", new ArrayList<>(), new ArrayList<>(), new Location(), false, "mine shaft");
    Location mountainPass = new Location(mountainPassDescription, "You're at the top of a mountain pass", new ArrayList<>(), new ArrayList<>(), new Location(), false, "mountain pass");
    Location mustyBend = new Location(mustyBendDescription, "You're at a musty bend", new ArrayList<>(), new ArrayList<>(), new Location(), false, "musty bend");
    Location narrowCorridor = new Location(narrowCorridorDescription, "You're in a narrow corridor", new ArrayList<>(), new ArrayList<>(), new Location(), false, "narrow corridor");
    Location outsideLogCabin = new Location(outsideLogCabinDescription, "You're outside Log Cabin", new ArrayList<>(), new ArrayList<>(), new Location(), false, "outside log cabin");
    Location outsideTavern = new Location(outsideTavernDescription, "You're outside Tommyknocker Tavern", new ArrayList<>(), new ArrayList<>(), new Location(), false, "outside Tommyknocker Tavern");
    Location picnicTable = new Location(picnicTableDescription, "You're at Picnic Table", new ArrayList<>(), new ArrayList<>(), new Location(), false, "picnic table");
    Location privateProperty = new Location(privatePropertyDescription, "You're at Private Property", new ArrayList<>(), new ArrayList<>(), new Location(), false, "private property");
    Location roadInValley = new Location(roadInValleyDescription, "You're on a road in a valley", new ArrayList<>(), new ArrayList<>(), new Location(), false, "road in a valley");
    Location insideTavern = new Location(insideTavernDescription, "You're inside the Tommyknocker Tavern", new ArrayList<>(), new ArrayList<>(), new Location(), false, "Tommyknocker Tavern");
    Location tailings = new Location(tailingsDescription, "You're amongst piles of tailings", new ArrayList<>(), new ArrayList<>(), new Location(),  false, "abandoned gold mine");
    Location topOfHill = new Location(topOfHillDescription, "You're at Top of Hill", new ArrayList<>(), new ArrayList<>(), new Location(), false, "top of hill");
    Location topOfStairs = new Location(topOfStairsDescription, "You're at Top of Stairs", new ArrayList<>(), new ArrayList<>(), new Location(), false, "top of stairs");
    Location undergroundLakeWest = new Location(undergroundLakeWestDescription, "You're on the west side of an underground lake", new ArrayList<>(), new ArrayList<>(), new Location(), false, "underground lake west");
    Location undergroundLakeEast = new Location(undergroundLakeEastDescription, "You're on the east side of an underground lake", new ArrayList<>(), new ArrayList<>(), new Location(), false, "underground lake east");
    Location undergroundLakeNE = new Location(undergroundLakeNEDescription, "You're on the ne side of an underground lake", new ArrayList<>(), new ArrayList<>(), new Location(), false, "underground lake ne");
    Location upstairsLogCabin = new Location(upstairsLogCabinDescription, "You're on the second floor of the log cabin", new ArrayList<>(), new ArrayList<>(), new Location(), false, "upstairs log cabin");
    Location westEndOfSideStreet = new Location(westEndOfSideStreetDescription, "You're at the west end of the main street", new ArrayList<>(), new ArrayList<>(), new Location(), false, "west end of side street");
//    Boat boat = new Boat(boatDescription, "You're in a rickety wooden boat in an underground lake", new ArrayList<>(), new ArrayList<>(), new Boat(), false, "rickety wooden boat", false);
//    Dam dam = new Dam(damDescription, "You're at Dam", new ArrayList<>(), new ArrayList<>(), new Dam(), false, "dam", false, false);
//    GraniteRoom graniteRoom = new GraniteRoom(graniteRoomDescription, "You're at Granite Room", new ArrayList<>(Collections.singletonList(cube)), new ArrayList<>(), new Location(), false, "granite room", false);
//    MineEntrance mineEntrance = new MineEntrance(mineEntranceDescription, "You're at the entrance of an abandoned gold mine", new ArrayList<>(Collections.singletonList(gold)), new ArrayList<>(), new MineEntrance(), false, "mine entrance", false);
//    RubyOnRails rubyOnRails = new RubyOnRails(rubyOnRailsDescription, "You're at Ruby On Rails", new ArrayList<>(Collections.singletonList(ruby)), new ArrayList<>(), new Location(), false, "ruby on rails", false);
//    Shed shed = new Shed(shedDescription, "You're standing before a cheerful little shed", new ArrayList<>(), new ArrayList<>(), new Shed(), false, "shed", false, false);
    
    public LocationGraph() {
        antHill.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), ditch));
        archeryRange.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), ditch));
        archeryRange.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), privateProperty));
//        boat.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), undergroundLakeEast));
//        boat.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), undergroundLakeWest));
//        boat.connectingLocations.add(new ConnectingLocation(List.of("northeast", "ne"), undergroundLakeNE));
        bottomOfVerticalMineShaft.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), dankPassage));
//        bottomOfVerticalMineShaft.connectingLocations.add(new ConnectingLocation(List.of("in"), mine cage));
        brokenRock.connectingLocations.add(new ConnectingLocation(List.of("down", "d"), dirtyPassage));
//        dam.connectingLocations.add(new ConnectingLocation(List.of("north"), topOfStairs));
//        dam.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), lake));
//        dam.connectingLocations.add(new ConnectingLocation(List.of("up", "u"), topOfStairs));
//        dam.connectingLocations.add(new ConnectingLocation(null, lakeTown));
//        dam.connectingLocations.add(new ConnectingLocation(null, lakeTown));
        dankPassage.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), narrowCorridor));
        dankPassage.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), bottomOfVerticalMineShaft));
        dankPassage.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), mustyBend));
        dirtRoad.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), intersection));
        dirtRoad.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), driveway));
        dirtyPassage.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), narrowCorridor));
        dirtyPassage.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), undergroundLakeNE));
        dirtyPassage.connectingLocations.add(new ConnectingLocation(List.of("up", "u"), brokenRock));
        ditch.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), archeryRange));
        ditch.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), antHill));
        ditch.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), lightningTree));
        driveway.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), privateProperty));
        driveway.connectingLocations.add(new ConnectingLocation(List.of("down", "d"), privateProperty));
        driveway.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), dirtRoad));
        driveway.connectingLocations.add(new ConnectingLocation(List.of("northwest", "nw"), footPath));
        dynamiteHoles.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), mustyBend));
        dynamiteHoles.connectingLocations.add(new ConnectingLocation(List.of("southwest", "sw"), narrowCorridor));
        dynamiteHoles.connectingLocations.add(new ConnectingLocation(List.of("up", "u"), narrowCorridor));
//        dynamiteHoles.connectingLocations.add(new ConnectingLocation(down, rubyOnRails));
        eastEndOfSideStreet.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), roadInValley));
        eastEndOfSideStreet.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), outsideTavern));
        eastEndOfSideStreet.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), topOfStairs));
        fieldsOfGrass.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), roadInValley));
        fieldsOfGrass.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), mountainPass));
        fieldsOfGrass.connectingLocations.add(new ConnectingLocation(List.of("up", "u"), mountainPass));
        footPath.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), driveway));
        footPath.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), topOfHill));
//        graniteRoom.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), undergroundLakeEast));
//        graniteRoom.connectingLocations.add(new ConnectingLocation(List.of("out"), undergroundLakeEast));
        insideLogCabin.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), outsideLogCabin));
        insideLogCabin.connectingLocations.add(new ConnectingLocation(List.of("up", "u"), upstairsLogCabin));
        insideLogCabin.connectingLocations.add(new ConnectingLocation(List.of("out"), outsideLogCabin));
        intersection.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), topOfHill));
        intersection.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), tailings));
        intersection.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), dirtRoad));
        insideTavern.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), outsideTavern));
        insideTavern.connectingLocations.add(new ConnectingLocation(List.of("out"), outsideTavern));
//        lake.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), dam));
        lake.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), tailings));
//        lakeTown.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), dam));
//        lakeTown.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), farther into the town));
//        lakeTown.connectingLocations.add(new ConnectingLocation(List.of("up", "u"), dam));
        lightningTree.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), ditch));
        mineShaft.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), undergroundLakeWest));
//        mineShaft.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), mineEntrance));
//        mineShaft.connectingLocations.add(new ConnectingLocation(List.of("out"), mineEntrance));
//        mineEntrance.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), tailings));
//        mineEntrance.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), mineShaft));
//        mineEntrance.connectingLocations.add(new ConnectingLocation(List.of("in"), mineShaft));
        mountainPass.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), fieldsOfGrass));
//        mountainPass.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), mine cage));
        mountainPass.connectingLocations.add(new ConnectingLocation(List.of("down", "d"), fieldsOfGrass));
        mustyBend.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), dynamiteHoles));
        mustyBend.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), dankPassage));
        narrowCorridor.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), dankPassage));
        narrowCorridor.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), dirtyPassage));
        narrowCorridor.connectingLocations.add(new ConnectingLocation(List.of("northeast", "ne"), dynamiteHoles));
        narrowCorridor.connectingLocations.add(new ConnectingLocation(List.of("down", "d"), dynamiteHoles));
        outsideLogCabin.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), westEndOfSideStreet));
        outsideLogCabin.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), insideLogCabin));
        outsideLogCabin.connectingLocations.add(new ConnectingLocation(List.of("in"), insideLogCabin));
        outsideTavern.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), eastEndOfSideStreet));
//        outsideTavern.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), head towards colorado springs?));
        outsideTavern.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), insideTavern));
        outsideTavern.connectingLocations.add(new ConnectingLocation(List.of("in"), insideTavern));
//        picnicTable.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), shed));
        picnicTable.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), privateProperty));
        privateProperty.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), archeryRange));
        privateProperty.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), driveway));
        privateProperty.connectingLocations.add(new ConnectingLocation(List.of("up", "u"), driveway));
        privateProperty.connectingLocations.add(new ConnectingLocation(List.of("northeast", "ne"), picnicTable));
        roadInValley.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), fieldsOfGrass));
        roadInValley.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), eastEndOfSideStreet));
//        rubyOnRails.connectingLocations.add(new ConnectingLocation(List.of("up", "u"), dynamiteHoles));
//        shed.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), picnicTable));
//        tailings.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), mineEntrance));
        tailings.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), lake));
        tailings.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), intersection));
        topOfHill.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), intersection));
        topOfHill.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), footPath));
        topOfHill.connectingLocations.add(new ConnectingLocation(List.of("down", "d"), intersection));
//        topOfStairs.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), dam));
        topOfStairs.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), eastEndOfSideStreet));
        topOfStairs.connectingLocations.add(new ConnectingLocation(List.of("west", "w"), westEndOfSideStreet));
//        topOfStairs.connectingLocations.add(new ConnectingLocation(List.of("down", "d"), dam));
        undergroundLakeWest.connectingLocations.add(new ConnectingLocation(List.of("south", "s"), mineShaft));
//        undergroundLakeWest.connectingLocations.add(new ConnectingLocation(List.of("in"), boat));
        undergroundLakeNE.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), dirtyPassage));
//        undergroundLakeNE.connectingLocations.add(new ConnectingLocation(List.of("in"), boat));
//        undergroundLakeEast.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), graniteRoom));
//        undergroundLakeEast.connectingLocations.add(new ConnectingLocation(List.of("in"), boat));
        upstairsLogCabin.connectingLocations.add(new ConnectingLocation(List.of("down", "d"), insideLogCabin));
        westEndOfSideStreet.connectingLocations.add(new ConnectingLocation(List.of("north", "n"), outsideLogCabin));
        westEndOfSideStreet.connectingLocations.add(new ConnectingLocation(List.of("east", "e"), topOfStairs));
    }

    public LocationGraph getGraph() {
        
    }
    
//    LocationGraph(user id) {}
}
