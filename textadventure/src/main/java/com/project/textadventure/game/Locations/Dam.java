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

    public void turnWheel() {
        this.getConnectingLocations().get(4).setDirections(List.of("east", "e"));
        this.getConnectingLocations().get(5).setDirections(List.of("down", "d"));
        wheelTurned = true;
    }
}
