package com.netreaders.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netreaders.models.RegistrationToken;
import com.netreaders.models.User;
import com.netreaders.dao.registrationtoken.RegistrationTokenDao;

@Service
public class RegistrationTokenService {
	@Autowired
	private RegistrationTokenDao tokenDao;
	
	public RegistrationToken createToken(User user) {
		RegistrationToken token = new RegistrationToken();
		token.setToken(UUID.nameUUIDFromBytes(user.getUsername().getBytes()).toString());
		token.setUserId(user.getUserId());
		token.setCreatedDateTime(LocalDateTime.now());
		try {
			tokenDao.create(token);
			return token;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public RegistrationToken getByToken(String token){
		try {
			return tokenDao.findByToken(token);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public RegistrationToken getById(int id){
		try {
			return tokenDao.getById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public RegistrationToken getByUser(User user){
		try {
			return tokenDao.findByUser(user.getUserId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean tokenIsValid(String token) {
		RegistrationToken registrationToken;
		try {
			registrationToken = tokenDao.findByToken(token);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if(registrationToken == null) return false;
		return LocalDateTime.now().isBefore(registrationToken.calculateExpiryDate());
	}
	
	public void removeToken(String token) {
		RegistrationToken registrationToken = getByToken(token);
		try {
			tokenDao.delete(registrationToken);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
