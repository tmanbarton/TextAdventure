package com.project.textadventure.controllers;

public interface Action {
    /**
     *
     * @param verb
     * @param noun
     * @return
     */
    public String takeAction(String verb, String noun);
}
