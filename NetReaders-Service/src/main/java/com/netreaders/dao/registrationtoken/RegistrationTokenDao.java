package com.netreaders.dao.registrationtoken;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.RegistrationToken;

public interface RegistrationTokenDao extends GenericDao<RegistrationToken, Integer> {

    RegistrationToken findByToken(String token) throws DataBaseSQLException;

    RegistrationToken findByUser(Integer userId) throws DataBaseSQLException;
}
