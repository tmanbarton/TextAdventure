package com.project.textadventure.game;

import com.project.textadventure.game.graph.LocationGraph;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateGame implements CommandLineRunner {

    @Override
    public void run(String... args) {
        LocationGraph locationGraph = new LocationGraph();
        System.out.println(locationGraph.antHillDescription);
    }
}
