package com.netreaders.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.netreaders.dao.role.RoleDao;
import com.netreaders.dao.token.TokenDao;
import com.netreaders.dao.user.UserDao;
import com.netreaders.dto.UserDto;
import com.netreaders.models.Role;
import com.netreaders.models.Token;
import com.netreaders.models.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private TokenDao tokenDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User registerUser(UserDto userDto) throws SQLException {

		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setUserNickname(userDto.getUserName());
		String hashedPassword = passwordEncoder.encode(userDto.getPassword());
		user.setUserPassword(hashedPassword);
		user.setEmail(userDto.getEmail());

		user.setRoles(Arrays.asList(roleDao.findByRoleName("ROLE_USER")));
		userDao.create(user);
		return user;
	}

	public User findByNickname(String username) throws SQLException {
		return userDao.findByNickname(username);
	}

	/*@Override
	public User findByUsernameAndPassword(String username, String password) {

		return userDAO.findByUsernameAndPassword(username, password);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		User user = userDAO.findByUsername(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		try {
			if (user.isEnabled() != true) {
				throw new UsernameNotFoundException("Please enable your account.");
			}
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.isEnabled(), true, true, true, mapRolesToAuthorities(user.getRoles()));
	}*/

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
	}

	public void createTokenName(User user, String token) throws SQLException {
		Token newUserToken = new Token(token, user);
		tokenDao.create(newUserToken);
	}

	
	public Token getTokenName(String Token) throws SQLException {
		return tokenDao.findByToken(Token);
	}


}