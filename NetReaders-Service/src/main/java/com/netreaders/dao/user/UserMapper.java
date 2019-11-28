package com.netreaders.dao.user;

import com.netreaders.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {

        User user = new User();

        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("user_name"));
        user.setUserPassword(rs.getString("user_password").trim());
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setProfilePhoto(rs.getInt("profile_photo"));

        return user;
    }

    @Bean
    public UserMapper getUserMapper() {
        return new UserMapper();
    }
}
