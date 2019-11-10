package com.netreaders.registration.dao;

import com.netreaders.registration.model.Role;

public interface IRoleDAO {
	public Role findByRoleName(String role);
}
