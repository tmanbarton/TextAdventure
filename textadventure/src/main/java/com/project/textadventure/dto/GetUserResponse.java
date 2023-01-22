package com.project.textadventure.dto;

import com.project.textadventure.dao.User;

import java.util.List;

public class GetUserResponse {
    private final List<User> user;

    public GetUserResponse(List<User> user) {
        this.user = user;
    }

    public List<User> getUser() {
        return user;
    }
}
