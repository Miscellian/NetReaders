package com.netreaders.dao.role;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.netreaders.models.Role;

@Component
public class RoleMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int i) throws SQLException {

    	Role role = new Role();

    	role.setRoleId(rs.getInt("role_id"));
    	role.setRoleName(rs.getString("role_name"));

        return role;
    }

    @Bean
    public RoleMapper getRoleMapper() {
        return new RoleMapper();
    }
}
