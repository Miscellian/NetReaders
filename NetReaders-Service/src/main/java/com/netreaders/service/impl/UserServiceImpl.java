package com.netreaders.service.impl;

import com.netreaders.dao.role.RoleDao;
import com.netreaders.dao.user.UserDao;
import com.netreaders.dto.JwtResponse;
import com.netreaders.dto.LoginForm;
import com.netreaders.dto.SignUpForm;
import com.netreaders.models.Role;
import com.netreaders.models.User;
import com.netreaders.security.JwtAuthTokenFilter;
import com.netreaders.security.JwtProvider;
import com.netreaders.security.UserPrinciple;
import com.netreaders.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtAuthTokenFilter jwtTokenFilter;

    @Transactional
    @Override
    public User registerUser(SignUpForm signUpForm) {

        User user = CreateUser(signUpForm);
        user = userDao.create(user);
        Role role = roleDao.findByRoleName("USER");
        roleDao.addUserToRole(role, user);
        return user;
    }

    @Transactional
    @Override
    public User registerPriviledgedUser(SignUpForm signUpForm, String[] roles) {

        User user = CreateUser(signUpForm);
        user = userDao.create(user);
        for (String roleName : roles) {
            Role role = roleDao.findByRoleName(roleName);
            roleDao.addUserToRole(role, user);
        }
        return user;
    }
    
    private User CreateUser(SignUpForm signUpForm) {
    	User user = new User();
        user.setFirstName(signUpForm.getFirstName());
        user.setLastName(signUpForm.getLastName());
        user.setUsername(signUpForm.getUsername());
        String hashedPassword = passwordEncoder.encode(signUpForm.getUserPassword());
        user.setUserPassword(hashedPassword);
        user.setEmail(signUpForm.getEmail());
        return user;
    }


    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        Collection<Role> roles = roleDao.findByUserId(user.getId());
        return UserPrinciple.build(user, roles);
	}

	@Override
	public boolean userExists(String username) {
		return userDao.userExists(username);
	}

	@Override
	public JwtResponse login(LoginForm loginForm) {
        UserDetails userDetails = jwtTokenFilter.setContextAuthentication(loginForm.getUsername());
        String jwt = jwtProvider.generateJwtToken(SecurityContextHolder.getContext().getAuthentication());
        return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
	}

}