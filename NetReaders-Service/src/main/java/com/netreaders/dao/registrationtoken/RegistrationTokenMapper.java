package com.netreaders.dao.registrationtoken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.netreaders.models.RegistrationToken;

@Component
public class RegistrationTokenMapper implements RowMapper<RegistrationToken> {

    @Override
    public RegistrationToken mapRow(ResultSet rs, int i) throws SQLException {

    	RegistrationToken token = new RegistrationToken();

    	token.setTokenId(rs.getInt("registration_token_id"));
    	token.setUserId(rs.getInt("user_id"));
    	token.setToken(rs.getString("token_value"));
    	token.setCreatedDateTime(rs.getObject("created_time", LocalDateTime.class));

        return token;
    }
}
