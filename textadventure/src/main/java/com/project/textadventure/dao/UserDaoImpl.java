package com.project.textadventure.dao;

import com.project.textadventure.mapper.UserRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
    NamedParameterJdbcTemplate template;

    public UserDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }
    @Override
    public List<User> findAll() {
        return template.query("SELECT * FROM users", new UserRowMapper());
    }

    @Override
    public void insertUser(User user) {
        final String query = "INSERT INTO users (user_id, username) VALUES(:user_id, :username)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("user_id", user.getUserId())
                .addValue("username", user.getUsername());
        template.update(query, param, holder);
    }

    @Override
    public void updateUser(User user) {
        final String query = "UPDATE users SET username=:username WHERE user_id=:user_id";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("user_id", user.getUserId())
                .addValue("username", user.getUsername());
        template.update(query, param, holder);
    }

    @Override
    public void executeUpdateUser(User user) {
        final String query = "UPDATE users SET username=:username WHERE user_id=:user_id";

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", user.getUserId());
        map.put("username", user.getUsername());

        template.execute(query, map, (PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
    }

    @Override
    public void deleteUser(User user) {
        final String query = "DELETE FROM users WHERE user_id=:user_id";

        Map<String, Object> map =  new HashMap<>();
        map.put("user_id", user.getUserId());

        template.execute(query, map, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                return preparedStatement.executeUpdate();
            }
        });
    }
}
