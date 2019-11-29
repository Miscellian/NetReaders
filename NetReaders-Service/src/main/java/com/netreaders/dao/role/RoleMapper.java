package com.netreaders.dao.role;

import com.netreaders.models.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int i) throws SQLException {

    	Role role = new Role();

        role.setId(rs.getInt("role_id"));
    	role.setRoleName(rs.getString("role_name"));

        return role;
    }

    @Bean
    public RoleMapper getRoleMapper() {
        return new RoleMapper();
    }
}
