package com.netreaders.dao.user;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.User;

import java.util.Collection;

public interface UserDao extends GenericDao<User, Integer> {

    User findByUsername(String username) throws DataBaseSQLException;

    Collection<User> findByFirstName(String firstName) throws DataBaseSQLException;

    void deleteByUsername(String username) throws DataBaseSQLException;
    User getById(Integer id) throws DataBaseSQLException;
}
