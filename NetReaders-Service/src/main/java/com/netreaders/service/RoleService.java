package com.netreaders.service;

import com.netreaders.models.Role;

import java.util.Collection;

public interface RoleService {

    Role findRoleByName(String roleName);

    Collection<Role> findRolesByUserId(Integer id);
}
