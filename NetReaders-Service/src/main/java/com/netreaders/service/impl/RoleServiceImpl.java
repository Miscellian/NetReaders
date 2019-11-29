package com.netreaders.service.impl;

import com.netreaders.dao.role.RoleDao;
import com.netreaders.models.Role;
import com.netreaders.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Override
    public Role findRoleByName(String roleName) {

        return roleDao.findByRoleName(roleName);
    }

    @Override
    public Collection<Role> findRolesByUserId(Integer id) {

        return roleDao.findByUserId(id);
    }
}
