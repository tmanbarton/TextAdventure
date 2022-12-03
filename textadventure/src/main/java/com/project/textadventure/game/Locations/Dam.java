package com.project.textadventure.game.Locations;

import com.project.textadventure.game.ConnectingLocation;
import com.project.textadventure.game.Item;

import java.util.ArrayList;
import java.util.List;

public class Dam extends Location {
    private boolean wheelTurned;
    private boolean magnetDropped;

    public Dam(String fullDescription, String shortDescription, List<Item> items, List<ConnectingLocation> connectingLocations, boolean visited, String name, boolean wheelTurned, boolean magnetDropped) {
        super(fullDescription, shortDescription, items, connectingLocations, visited, name);
        this.wheelTurned = wheelTurned;
        this.magnetDropped = magnetDropped;
    }

    public boolean isWheelTurned() {
        return wheelTurned;
    }

    public boolean isMagnetDropped() {
        return magnetDropped;
    }

    public void setMagnetDropped(boolean magnetDropped) {
        this.magnetDropped = magnetDropped;
    }

    /**
     * Connect the lake town location to the dam location.
     * Change the description of all locations that reference the lake to not include the lake
     * since there is no more lake.
     */
    public void turnWheel() {
        this.getConnectingLocations().get(3).setDirections(List.of("east", "e"));
        this.getConnectingLocations().get(4).setDirections(List.of("down", "d"));

        Location lake = this.getConnectingLocations().get(1).getLocation();
        lake.setDescription("You are on the south side of an empty lake. There's a path going west and there's a dam to the north.");
        lake.setShortDescription("You're on the south side of an empty lake.");

        Location tailings = lake.getConnectingLocations().get(1).getLocation();
        tailings.setDescription("All around are piles of tailings that look like they have been puked into this valley. There's not much else to be seen except the entrance to a mine to the south. There's a path leading east and another going north.");

        Location intersection = tailings.getConnectingLocations().get(2).getLocation();
        intersection.setDescription("You have reached an intersection in the road. It leads into the forest to the north and west and a southern road goes into a thinner part of the forest.");

        Location driveway = intersection.getConnectingLocations().get(2).getLocation()
                .getConnectingLocations().get(1).getLocation();
        driveway.setDescription("You are at the west end of a dirt road surrounded by a forest of pine trees. There is a small gap to the north that exposes a steep, dirt driveway sloping down into the forest. Looking down the road to the east you can see over the trees and into the valley. There's also a foot path going northwest.");

        wheelTurned = true;
    }
}
