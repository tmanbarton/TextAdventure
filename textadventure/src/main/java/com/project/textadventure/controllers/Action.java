package com.project.textadventure.controllers;

public interface Action {
    /**
     *
     * @param verb
     * @param noun
     * @return
     */
    String takeAction(final String verb, final String noun);
}
