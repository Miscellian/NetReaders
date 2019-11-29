package com.netreaders.service.impl;

import com.netreaders.dao.role.RoleDao;
import com.netreaders.dao.user.UserDao;
import com.netreaders.dto.SignUpForm;
import com.netreaders.models.Role;
import com.netreaders.models.User;
import com.netreaders.security.UserPrinciple;
import com.netreaders.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public User registerUser(SignUpForm signUpForm) {

        User user = new User();

        user.setFirstName(signUpForm.getFirstName());
        user.setLastName(signUpForm.getLastName());
        user.setUsername(signUpForm.getUser_name());
        String hashedPassword = passwordEncoder.encode(signUpForm.getPassword());
        user.setUserPassword(hashedPassword);
        user.setEmail(signUpForm.getEmail());

        user = userDao.create(user);
        Role role = roleDao.findByRoleName("USER");
        roleDao.addUserToRole(role, user);
        return user;
    }

    @Transactional
    @Override
    public User registerPriviledgedUser(SignUpForm signUpForm, String[] roles) {

        User user = new User();

        // maybe create separate method for this stuff
        user.setFirstName(signUpForm.getFirstName());
        user.setLastName(signUpForm.getLastName());
        user.setUsername(signUpForm.getUser_name());
        String hashedPassword = passwordEncoder.encode(signUpForm.getPassword());
        user.setUserPassword(hashedPassword);
        user.setEmail(signUpForm.getEmail());


        user = userDao.create(user);
        for (String roleName : roles) {
            Role role = roleDao.findByRoleName(roleName);
            roleDao.addUserToRole(role, user);
        }
        return user;
    }


    @Override
    public User findByNickname(String username) {

        return userDao.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);
        Collection<Role> roles = roleDao.findByUserId(user.getId());
        return UserPrinciple.build(user, roles);
    }
}