package com.netreaders.registration.dao;

//import org.hibernate.CacheMode;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.netreaders.registration.model.User;
import com.netreaders.registration.model.VerificationToken;

@Repository
public class TokenDAO implements ITokenDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*@Autowired
	private SessionFactory sessionFactory;*/

	@Override
	public VerificationToken findByToken(String token) {
		/*Session session = sessionFactory.getCurrentSession();
		Query<VerificationToken> query = session.createQuery("from VerificationToken where token=:token",
				VerificationToken.class);
		session.setCacheMode(CacheMode.IGNORE);
		query.setParameter("token", token);

		VerificationToken userToken = null;
		try {
			userToken = query.getSingleResult();
		} catch (Exception e) {
			userToken = null;
			e.printStackTrace();
		}*/
		
		String query = "SELECT url FROM registration_link WHERE url = ?";
		RowMapper<VerificationToken> rowMapper = new BeanPropertyRowMapper<VerificationToken>(VerificationToken.class);
		VerificationToken userToken = jdbcTemplate.queryForObject(query, rowMapper, token);
		return userToken;
	}

	@Override
	public VerificationToken findByUser(User user) {
		/*Session session = sessionFactory.getCurrentSession();
		Query<VerificationToken> query = session.createQuery("from VerificationToken where user=:user",
				VerificationToken.class);
		session.setCacheMode(CacheMode.IGNORE);
		query.setParameter("user", user);

		VerificationToken userToken = null;
		try {
			userToken = query.getSingleResult();
		} catch (Exception e) {
			userToken = null;
			e.printStackTrace();
		}*/
		
		String query = "SELECT user_id FROM registration_link WHERE user_id = ?";
		RowMapper<VerificationToken> rowMapper = new BeanPropertyRowMapper<VerificationToken>(VerificationToken.class);
		VerificationToken userToken = jdbcTemplate.queryForObject(query, rowMapper, user);
		return userToken;
	}

	@Override
	public void save(VerificationToken newUserToken) {
		/*Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
		session.setCacheMode(CacheMode.IGNORE);
		session.saveOrUpdate(newUserToken);*/
		
		String query = "INSERT INTO registration_link(registration_link_id, user_id, url, created_time) VALUES(?, ?, ?, ?)";
		jdbcTemplate.update(query, newUserToken.getId(), newUserToken.getUser().getId(), newUserToken.getToken(), newUserToken.getCreatedDate());
	}

}
