package com.netreaders.dao.user;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.User;

import java.sql.SQLException;
import java.util.Collection;

public interface UserDao extends GenericDao<User, Long> {

    User findByNickname(String nickname) throws DataBaseSQLException;

    Collection<User> findByFirstName(String firstName) throws DataBaseSQLException;

    void deleteByNickname(String nickname) throws DataBaseSQLException;
}
