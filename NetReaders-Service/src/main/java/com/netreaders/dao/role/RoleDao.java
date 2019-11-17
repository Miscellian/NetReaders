package com.netreaders.dao.role;

import java.sql.SQLException;

import com.netreaders.dao.GenericDao;
import com.netreaders.models.Role;

public interface RoleDao extends GenericDao<Role, Long> {

	public Role findByRoleName(String role) throws SQLException;
}
