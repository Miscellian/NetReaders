package com.netreaders.registration.dao;

import com.netreaders.registration.dto.UserDTO;
import com.netreaders.registration.model.User;

public interface IUserDAO {
	public User findByUsername(String username);

	public void save(User user);
	
	//public User loginUser(UserDTO userDTO);
	//public User loginUser(User user);

	public User findByUsernameAndPassword(String username, String password);
}
