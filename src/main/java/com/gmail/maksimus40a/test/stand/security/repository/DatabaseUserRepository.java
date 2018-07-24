package com.gmail.maksimus40a.test.stand.security.repository;

import com.gmail.maksimus40a.test.stand.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
@Qualifier("jdbc")
public class DatabaseUserRepository implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                " id       INT(11)      NOT NULL AUTO_INCREMENT," +
                " username VARCHAR(100) NOT NULL," +
                " password VARCHAR(100) NOT NULL," +
                " primary key (id)" +
                ")";
        jdbcTemplate.execute(sql);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User authUser = jdbcTemplate.queryForObject(sql, new Object[]{userName}, (resultSet, i) -> {
            User user = new User();
            user.setId(resultSet.getLong(1));
            user.setUsername(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            return user;
        });
        return Optional.of(authUser);
    }

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps;
        }, generatedKeyHolder);
        Integer newUserId = generatedKeyHolder.getKey().intValue();
        user.setId(Long.valueOf(newUserId));
        return user;
    }
}