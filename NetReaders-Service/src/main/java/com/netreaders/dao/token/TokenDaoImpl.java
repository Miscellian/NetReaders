package com.netreaders.dao.token;


import java.sql.SQLException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;


//import com.netreaders.models.Token;


import lombok.extern.log4j.Log4j;

//@Log4j
//@PropertySource("classpath:query.properties")
//@Repository
//public class TokenDaoImpl implements TokenDao {
//
//	@Autowired
//    private TokenMapper tokenMapper;
//	
//	@Autowired
//    private Environment env;
//	
//	@Autowired
//	private JdbcTemplate template;
//
//	@Override
//	public void create(Token newUserToken) throws SQLException {
//		
//		final String sql_query = env.getProperty("token.create");
//		
//		int recordCount = template.update(sql_query,
//				newUserToken.getTokenId(),
//				newUserToken.getUserId(),
//				newUserToken.getTokenName(),
//				newUserToken.getCreatedDate());
//		
//		if (recordCount == 0) {
//            log.debug(String.format("Dont create any token by id '%d'", newUserToken.getTokenId()));
//        } else if (recordCount == 1) {
//            log.debug(String.format("Update token by id '%d'", newUserToken.getTokenId()));
//        } else {
//            log.error(String.format("Update more than one token by id '%d'", newUserToken.getTokenId()));
//            throw new SQLException("Internal sql exception");
//        }
//	}
//	
//	@Override
//	public Token findByToken(String tokenName)  throws SQLException{
//		
//		String sql_query = env.getProperty("token.findByToken");
//		
//		List<Token> tokens = template.query(sql_query, tokenMapper, tokenName);
//		
//		if (tokens.isEmpty()) {
//            log.debug(String.format("Dont find any token by tokenName '%s'", tokenName));
//            return null;
//        } else if (tokens.size() == 1) {
//            log.debug(String.format("Find a token by tokenName '%s'", tokenName));
//            return tokens.get(0);
//        } else {
//            log.error(String.format("Find more than one token by tokenName '%s'", tokenName));
//            throw new SQLException("Internal sql exception");
//        }
//	}
//
//	@Override
//	public Token findByUser(Integer userId) throws SQLException {
//
//		String sql_query = env.getProperty("token.findByUser");
//		
//		List<Token> tokens = template.query(sql_query, tokenMapper, userId);
//
//        if (tokens.isEmpty()) {
//            log.debug(String.format("Dont find any token by userId '%s'", userId));
//            return null;
//        } else if (tokens.size() == 1) {
//            log.debug(String.format("Find a token by userId '%s'", userId));
//            return tokens.get(0);
//        } else {
//            log.error(String.format("Find more than one token by userId '%s'", userId));
//            throw new SQLException("Internal sql exception");
//        }
//	}
//
//	
//	
//}
