package com.netreaders.service;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netreaders.dao.role.RoleDao;
import com.netreaders.dao.user.UserDao;
import com.netreaders.dto.SignUpForm;
import com.netreaders.models.Role;
import com.netreaders.models.User;

@Service
public class UserService implements UserDetailsService{

	private UserDao userDao;
	private RoleDao roleDao;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder){
		this.userDao=userDao;
		this.roleDao=roleDao;
		this.passwordEncoder=passwordEncoder;
	}


	@Transactional
	public User registerUser(SignUpForm signUpForm) {
		User user = new User();
		user.setFirstName(signUpForm.getFirstName());
		user.setLastName(signUpForm.getLastName());
		user.setUserNickname(signUpForm.getUser_name());
		String hashedPassword = passwordEncoder.encode(signUpForm.getPassword());
		user.setUserPassword(hashedPassword);
		user.setEmail(signUpForm.getEmail());
		Role role;
		try {
			userDao.create(user);
			role = roleDao.findByRoleName("USER");
			roleDao.addUserToRole(role, user);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public User registerPriviledgedUser(SignUpForm signUpForm, String[] roles) {
		User user = new User();
		user.setFirstName(signUpForm.getFirstName());
		user.setLastName(signUpForm.getLastName());
		user.setUserNickname(signUpForm.getUser_name());
		String hashedPassword = passwordEncoder.encode(signUpForm.getPassword());
		user.setUserPassword(hashedPassword);
		user.setEmail(signUpForm.getEmail());
		Role role;
		try {
			userDao.create(user);
			for(String roleName : roles) {
				role = roleDao.findByRoleName(roleName);
				roleDao.addUserToRole(role, user);
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	

	public User findByNickname(String username) throws SQLException {
		return userDao.findByNickname(username);
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		Collection<Role> roles;
		try {
			user = userDao.findByNickname(username);
			roles= roleDao.findByUserId(user.getUserId());
			return UserPrinciple.build(user, roles);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		 
		
	}




}