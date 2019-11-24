package com.netreaders.dao.role;

import java.sql.SQLException;
import java.util.Collection;

import com.netreaders.dao.GenericDao;
import com.netreaders.models.Role;
import com.netreaders.models.User;

public interface RoleDao extends GenericDao<Role, Long> {

	public Role findByRoleName(String role) throws SQLException;
	public Collection<Role> findByUserId(int id) throws SQLException;
	public boolean addUserToRole(Role role, User user) throws SQLException;
}
