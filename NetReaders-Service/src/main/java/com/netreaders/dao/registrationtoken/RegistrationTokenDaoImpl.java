package com.netreaders.dao.registrationtoken;


import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import com.netreaders.models.Author;
import com.netreaders.models.RegistrationToken;


import lombok.extern.log4j.Log4j;

@Log4j
@PropertySource("classpath:query.properties")
@Repository
public class RegistrationTokenDaoImpl implements RegistrationTokenDao {

	@Autowired
    private RegistrationTokenMapper tokenMapper;
	
	@Autowired
    private Environment env;
	
	@Autowired
	private JdbcTemplate template;
	
	@Override
	public RegistrationToken findByToken(String token)  throws SQLException{
		
		String sql_query = env.getProperty("registrationToken.findByToken");
		
		List<RegistrationToken> tokens = template.query(sql_query,tokenMapper, token);
		
		if (tokens.isEmpty()) {
            log.debug(String.format("Dont find any token by tokenName '%s'", token));
            return null;
        } else if (tokens.size() == 1) {
            log.debug(String.format("Find a token by tokenName '%s'", token));
            return tokens.get(0);
        } else {
            log.error(String.format("Find more than one token by tokenName '%s'", token));
            throw new SQLException("Internal sql exception");
        }
	}

	@Override
	public RegistrationToken findByUser(Integer userId) throws SQLException {

		String sql_query = env.getProperty("registrationToken.findByUser");
		
		List<RegistrationToken> tokens = template.query(sql_query, tokenMapper, userId);

        if (tokens.isEmpty()) {
            log.debug(String.format("Dont find any token by userId '%s'", userId));
            return null;
        } else if (tokens.size() == 1) {
            log.debug(String.format("Find a token by userId '%s'", userId));
            return tokens.get(0);
        } else {
            log.error(String.format("Find more than one token by userId '%s'", userId));
            throw new SQLException("Internal sql exception");
        }
	}

	@Override
	public RegistrationToken getById(Integer id) throws SQLException {
		String sql_query = env.getProperty("registrationToken.read");

        List<RegistrationToken> tokens = template.query(sql_query, tokenMapper, id);
        if (tokens.isEmpty()) {
            log.debug(String.format("Dont find any token by tokenId '%s'", id));
            return null;
        } else if (tokens.size() == 1) {
            log.debug(String.format("Find a token by tokenId '%s'", id));
            return tokens.get(0);
        } else {
            log.error(String.format("Find more than one token by tokenId '%s'", id));
            throw new SQLException("Internal sql exception");
        }
	}

	@Override
	public void update(RegistrationToken persistentObject) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(RegistrationToken token) throws SQLException {
		String sql_query = env.getProperty("registrationToken.delete");

        long id = token.getTokenId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Dont delete any token by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Delete token by id '%d'", id));
        } else {
            log.error(String.format("Delete more than one token by id '%d'", id));
            throw new SQLException("Internal sql exception");
        }
		
	}

	@Override
	public Collection<RegistrationToken> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegistrationToken create(RegistrationToken token) throws SQLException {
		final String sql_query = env.getProperty("registrationToken.create");
		
		int recordCount = template.update(sql_query,
				token.getUserId(),
				token.getToken(),
				token.getCreatedDateTime());
		
		if (recordCount == 0) {
            log.debug(String.format("Dont create any token by id '%d'", token.getTokenId()));
        } else if (recordCount == 1) {
            log.debug(String.format("Update token by id '%d'", token.getTokenId()));
        } else {
            log.error(String.format("Update more than one token by id '%d'", token.getTokenId()));
            throw new SQLException("Internal sql exception");
        }
		return token;
	}
}
