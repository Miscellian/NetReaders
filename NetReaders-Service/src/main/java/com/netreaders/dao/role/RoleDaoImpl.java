package com.netreaders.dao.role;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.netreaders.models.Role;

import lombok.extern.log4j.Log4j;

@Log4j
@PropertySource("classpath:query.properties")
@Repository
public class RoleDaoImpl implements RoleDao {
	
    @Autowired
    private RoleMapper roleMapper;
	
	@Autowired
    private Environment env;

    @Autowired
    private JdbcTemplate template;
    
    @Override
	public Role findByRoleName(String roleName) throws SQLException {

    	String sql_query = env.getProperty("role.findByRoleName");
		
		
    	List<Role> roles = template.query(sql_query, roleMapper, roleName);
        if (roles.isEmpty()) {
            log.debug(String.format("Dont find any role by roleName '%s'", roleName));
            return null;
        } else if (roles.size() == 1) {
            log.debug(String.format("Find a role by roleName '%s'", roleName));
            return roles.get(0);
        } else {
            log.error(String.format("Find more than one role by roleName '%s'", roleName));
            throw new SQLException("Internal sql exception");
        }
		
	}

	@Override
	public Role create(Role newInstance) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role getById(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Role persistentObject) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Role persistentObject) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Role> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
    

}
