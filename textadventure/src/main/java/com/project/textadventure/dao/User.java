package com.project.textadventure.dao;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="user_id", columnDefinition="uuid")
    @Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID userId;

    @Column(name="username")
    private String username;

    public User() {}

    public User(UUID userId, String username) {
        super();
        this.userId = userId;
        this.username = username;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
