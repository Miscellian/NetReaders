package com.netreaders.dao.user;

import com.netreaders.dao.GenericDao;
import com.netreaders.models.User;
import org.springframework.jdbc.support.xml.SqlXmlFeatureNotImplementedException;

import java.sql.SQLException;
import java.util.Collection;

public interface UserDao extends GenericDao<User, Integer> {

    User findByNickname(String nickname) throws SQLException;

    Collection<User> findByFirstName(String firstName);

    void deleteByNickname(String nickname) throws SQLException;

    User create(final User user) throws SQLException;
    User getById(Integer id) throws SQLException;
}
