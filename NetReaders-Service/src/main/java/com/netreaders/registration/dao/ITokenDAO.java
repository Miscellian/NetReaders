package com.netreaders.registration.dao;

import com.netreaders.registration.model.User;
import com.netreaders.registration.model.VerificationToken;

public interface ITokenDAO {
	public VerificationToken findByToken(String token);

	public VerificationToken findByUser(User user);

	public void save(VerificationToken token);

}
