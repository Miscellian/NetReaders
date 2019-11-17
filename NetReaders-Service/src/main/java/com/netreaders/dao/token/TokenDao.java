package com.netreaders.dao.token;

import java.sql.SQLException;

import com.netreaders.models.Token;
import com.netreaders.models.User;

public interface TokenDao {
	
	public Token findByToken(String token) throws SQLException;

	public Token findByUser(Integer userId) throws SQLException;

	public void create(Token token) throws SQLException;
}
