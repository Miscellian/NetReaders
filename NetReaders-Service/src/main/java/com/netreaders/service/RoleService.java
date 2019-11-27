package com.netreaders.service;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.netreaders.dao.role.RoleDao;
import com.netreaders.models.Role;

@Service
public class RoleService {
	private final RoleDao roleDao;

	public RoleService(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public Role findByRoleName(String roleName){
		Role role;
		try {
			role = roleDao.findByRoleName(roleName);
			return role;
		} catch (SQLException e) {
			return null;
		}
	};
	public Collection<Role> findByUserId(int id){
		Collection<Role> roles;
		try {
			roles = roleDao.findByUserId(id);
			return roles;
		} catch (SQLException e) {
			return null;
		}
	};
	
	
}
