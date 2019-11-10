package com.netreaders.registration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.netreaders.registration.model.Role;
//import com.netreaders.registration.model.User;
import com.netreaders.registration.model.User;

@Repository
public class RoleDAO implements IRoleDAO {
	
	/*@Autowired
	private SessionFactory sessionFactory;*/
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Role findByRoleName(String roleName) {
		/*Session session = sessionFactory.getCurrentSession();
		
		Query<Role> query = session.createQuery("from Role where role=:userRole", Role.class);
		query.setParameter("userRole", roleName);
		
		Role role = null;
		try {
			role =  query.getSingleResult();
		}catch (Exception e) {
			role = null;
		}
		return role;*/
		String query = "SELECT role_name FROM role WHERE role_name = ?";
		RowMapper<Role> rowMapper = new BeanPropertyRowMapper<Role>(Role.class);
	    //Role role = jdbcTemplate.queryForObject(query, rowMapper, roleName);
		
		
		List<Role> roles = jdbcTemplate.query(query, new Object[]{roleName}, new RowMapper<Role>(){
            @Override
            public Role mapRow(ResultSet rs, int i) throws SQLException {
            	Role role = new Role();
            	role.setRolename(rs.getString(1));
                return role;
            }
        });
        
        if(roles.isEmpty()) return null;
        else { return roles.get(0); }
		/*return (Role) jdbcTemplate.query(query, new Object[]{roleName}, (ResultSetExtractor) rs -> {
			Role role = null;
			if( rs.next() )
			{
				role = (Role) rs.getObject(1);

				// Sanity check - ensure only a single result
				if( rs.next() )
				{
					throw new IncorrectResultSizeDataAccessException(1);
				}
			}
			return role;
		});*/
		
	}

}
