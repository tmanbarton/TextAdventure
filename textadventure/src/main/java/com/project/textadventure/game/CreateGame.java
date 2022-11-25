package com.project.textadventure.game;

import com.project.textadventure.game.graph.LocationGraph;

public class CreateGame {

    public LocationGraph createNewGame() {
        LocationGraph locationGraph = new LocationGraph();
        System.out.println(locationGraph.antHillDescription);
        return locationGraph;
    }
}
