package com.netreaders.registration.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.netreaders.registration.dao.IRoleDAO;
import com.netreaders.registration.dao.IUserDAO;
import com.netreaders.registration.dao.TokenDAO;
import com.netreaders.registration.dto.UserDTO;
import com.netreaders.registration.model.Role;
import com.netreaders.registration.model.User;
import com.netreaders.registration.model.VerificationToken;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IRoleDAO roleDAO;

	@Autowired
	private TokenDAO tokenDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	//@Transactional
	public User registerUser(UserDTO userDto) {

		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setUsername(userDto.getUserName());
		String hashedPassword = passwordEncoder.encode(userDto.getPassword());
		user.setPassword(hashedPassword);
		user.setEnabled(userDto.isEnabled());
		user.setEmail(userDto.getEmail());

		user.setRoles(Arrays.asList(roleDAO.findByRoleName("ROLE_USER")));
		userDAO.save(user);
		return user;
	}

	@Override
	//@Transactional
	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {

		return userDAO.findByUsernameAndPassword(username, password);
	}

	//@Transactional
	/*@Override
	public User loginUser(UserDTO userDTO) {
		return userDAO.loginUser(userDTO);
	}*/

	//@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userDAO.findByUsername(username);
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
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toList());
	}

	@Override
	public void createVerificationToken(User user, String token) {
		VerificationToken newUserToken = new VerificationToken(token, user);
		tokenDAO.save(newUserToken);
	}

	@Override
	//@Transactional
	public VerificationToken getVerificationToken(String verificationToken) {
		return tokenDAO.findByToken(verificationToken);
	}

	@Override
	//@Transactional
	public void enableRegisteredUser(User user) {
		userDAO.save(user);
	}

}
