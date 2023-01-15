package com.project.textadventure.controller;

public interface Action {
    /**
     *
     * @param verb
     * @param noun
     * @return
     */
    public String takeAction(String verb, String noun);
}
