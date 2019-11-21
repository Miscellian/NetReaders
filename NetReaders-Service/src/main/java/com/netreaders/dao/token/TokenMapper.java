package com.netreaders.dao.token;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

//import com.netreaders.models.Token;

//@Component
//public class TokenMapper implements RowMapper<Token> {
//
//    @Override
//    public Token mapRow(ResultSet rs, int i) throws SQLException {
//
//    	Token token = new Token();
//
//    	token.setTokenId(rs.getInt("registration_link_id"));
//    	token.setUserId(rs.getInt("user_id"));
//    	token.setTokenName(rs.getString("url"));
//    	token.setCreatedDate(rs.getTime("created_time"));
//
//        return token;
//    }
//
//    @Bean
//    public TokenMapper getTokenMapper() {
//        return new TokenMapper();
//    }
//}
