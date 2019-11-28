package com.netreaders.dao.registrationtoken;

import java.sql.SQLException;

import com.netreaders.dao.GenericDao;
import com.netreaders.models.RegistrationToken;

public interface RegistrationTokenDao extends GenericDao<RegistrationToken, Integer>  {
	
	public RegistrationToken findByToken(String token) throws SQLException;

	public RegistrationToken findByUser(Integer userId) throws SQLException;

	public RegistrationToken create(RegistrationToken token) throws SQLException;
}
