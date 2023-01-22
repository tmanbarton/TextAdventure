package com.project.textadventure.mapper;

import com.project.textadventure.dao.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId((UUID) resultSet.getObject("user_id"));
        user.setUsername(resultSet.getString("username"));
        return user;
    }
}
