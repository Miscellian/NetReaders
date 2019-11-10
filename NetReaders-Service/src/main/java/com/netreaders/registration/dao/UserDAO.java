package com.netreaders.registration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.netreaders.registration.model.User;

@Repository
public class UserDAO implements IUserDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;


	
	@Override
	public User findByUsername(String userName) {
		
		String query = "SELECT user_name FROM \"user\" WHERE user_name = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
	    //jdbcTemplate.queryForObject(query, User.class, userName)/*(query, rowMapper, userName)*/;
		/*return jdbcTemplate.query(query, new Object[]{userName}, new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rs) throws SQLException,
                                                           DataAccessException {
            	User user = new User();
            
                return rs.next() ? {user.setUsername(rs.getString("user_name")); return user;} : null;
            }
        });*/
        
		/*return (User) jdbcTemplate.query(query, new Object[]{userName}, (ResultSetExtractor) rs -> {
			User user = null;
			if( rs.next() )
			{
				user = (User) rs.getObject(1);

				// Sanity check - ensure only a single result
				if( rs.next() )
				{
					throw new IncorrectResultSizeDataAccessException(1);
				}
			}
			return user;
		});*/
		List<User> users = jdbcTemplate.query(query, new Object[]{userName}, new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setUsername(rs.getString(1));
                return user;
            }
        });
        
        if(users.isEmpty()) return null;
        else { return users.get(0); }
        
	}
	

	@Override
	public void save(User user) {
		/*Session session = sessionFactory.getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		session.saveOrUpdate(user);*/
		String query = "INSERT INTO \"user\"(user_id, user_name, user_password, email, first_name, last_name, profile_photo) VALUES(?, ?, ?, ?, ?, ?, NULL)";
		jdbcTemplate.update(query, user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName());
	}

	/*@Override
	public User loginUser(User user) {
		String query = "SELECT email FROM \"user\" WHERE email = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
	    User user = jdbcTemplate.queryForObject(query, rowMapper, User.getEmail());

		return user;
	}*/

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		/*Session session = sessionFactory.getCurrentSession();
		Query<User> query = session.createQuery("from User where username:=userName,password:=userPassword",User.class);
		session.setCacheMode(CacheMode.IGNORE);
		query.setParameter("userName", username);
		query.setParameter("userPassword", password);
		User user = null;
		try {
			user = query.getSingleResult();
		}catch (Exception e) {
			user = null;
		}*/
		String sql = "SELECT user_name, user_password FROM \"user\" WHERE user_name = ? AND user_password = ?";
        
        List<User> users = jdbcTemplate.query(sql, new Object[]{username, password}, new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setUsername(rs.getString("user_name"));
                user.setPassword(rs.getString("user_password"));
                return user;
            }
        });
        
        if(users.isEmpty()) return null;
        return users.get(0);
		//return user;
	}
	
	

}
