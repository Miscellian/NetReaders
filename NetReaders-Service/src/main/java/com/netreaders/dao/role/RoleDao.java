package com.netreaders.dao.role;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Role;
import com.netreaders.models.User;

import java.util.Collection;

public interface RoleDao extends GenericDao<Role, Integer> {

    Role findByRoleName(String role) throws DataBaseSQLException;

    Collection<Role> findByUserId(Integer id) throws DataBaseSQLException;

    Boolean addUserToRole(Role role, User user) throws DataBaseSQLException;
}
