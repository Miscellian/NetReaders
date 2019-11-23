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

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.netreaders.dao.role.RoleDao;
//import com.netreaders.dao.token.TokenDao;
import com.netreaders.dao.user.UserDao;
import com.netreaders.dto.SignUpForm;
import com.netreaders.dto.UserDto;
import com.netreaders.models.Role;
//import com.netreaders.models.Token;
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
	private PasswordEncoder passwordEncoder;

	public User registerUser(UserDto userDto) throws SQLException {

		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setUserNickname(userDto.getUserName());
		String hashedPassword = passwordEncoder.encode(userDto.getPassword());
		user.setUserPassword(hashedPassword);
		user.setEmail(userDto.getEmail());
		userDao.create(user);
		return user;
	}

	public User findByNickname(String username) throws SQLException {
		return userDao.findByNickname(username);
	}

//	public void createTokenName(User user, String token) throws SQLException {
//		Token newUserToken = new Token(token, user);
//		tokenDao.create(newUserToken);
//	}
//
//	
//	public Token getTokenName(String Token) throws SQLException {
//		return tokenDao.findByToken(Token);
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		Collection<Role> roles;
		try {
			user = userDao.findByNickname(username);
			roles= roleDao.findByUserId(user.getUserId());
			return UserPrinciple.build(user, roles);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		 
		
	}

	public User registerUser(SignUpForm signUpForm) {
		User user = new User();
		user.setFirstName(signUpForm.getFirstName());
		user.setLastName(signUpForm.getLastName());
		user.setUserNickname(signUpForm.getUsername());
		String hashedPassword = passwordEncoder.encode(signUpForm.getPassword());
		user.setUserPassword(hashedPassword);
		user.setEmail(signUpForm.getEmail());
		try {
			userDao.create(user);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


}