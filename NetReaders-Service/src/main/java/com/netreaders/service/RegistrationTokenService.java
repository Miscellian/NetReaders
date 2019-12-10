package com.netreaders.service;

import com.netreaders.models.RegistrationToken;
import com.netreaders.models.User;

import java.util.Collection;

public interface RegistrationTokenService {
    RegistrationToken createToken(User user);

    RegistrationToken getByToken(String token);

    RegistrationToken getTokenById(Integer id);

    RegistrationToken getTokenByUser(User user);

    boolean tokenIsValid(String token);
    
    boolean tokenExistsByUser(String username);

    void removeToken(String token);

    Collection<RegistrationToken> getAllTokens();
}
