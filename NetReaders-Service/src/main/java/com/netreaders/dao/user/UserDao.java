package com.netreaders.dao.user;

import com.netreaders.dao.GenericDao;
import com.netreaders.models.User;

import java.sql.SQLException;
import java.util.Collection;

public interface UserDao extends GenericDao<User, Long> {

    /**
     * Retrieve an object that was previously persisted to the database using
     * the nickname field
     *
     * @param nickname
     * @return the newInstance object of UserDto
     */
    User findByNickname(String nickname) throws SQLException;

    /**
     * Retrieve an object that was previously persisted to the database using
     * the firstName field
     *
     * @param firstName
     * @return the newInstance object of UserDto
     */
    Collection<User> findByFirstName(String firstName);

    /**
     * Delete an object that was previously persisted to the database using
     * the nickname field
     *
     * @param nickname
     * @return the newInstance object of UserDto
     */
    void deleteByNickname(String nickname) throws SQLException;
}
