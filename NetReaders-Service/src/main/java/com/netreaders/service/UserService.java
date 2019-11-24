package com.netreaders.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

//	@Autowired
//	private TokenDao tokenDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public User registerUser(SignUpForm signUpForm) {
		User user = new User();
		user.setFirstName(signUpForm.getFirstName());
		user.setLastName(signUpForm.getLastName());
		user.setUserNickname(signUpForm.getUsername());
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
		user.setUserNickname(signUpForm.getUsername());
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