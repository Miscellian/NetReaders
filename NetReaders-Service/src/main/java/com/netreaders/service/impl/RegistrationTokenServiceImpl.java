package com.netreaders.service.impl;

import com.netreaders.dao.registrationtoken.RegistrationTokenDao;
import com.netreaders.models.RegistrationToken;
import com.netreaders.models.User;
import com.netreaders.service.RegistrationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationTokenServiceImpl implements RegistrationTokenService {

    private final RegistrationTokenDao tokenDao;

    @Override
    public RegistrationToken createToken(User user) {

        RegistrationToken token = new RegistrationToken();
        token.setToken(UUID.nameUUIDFromBytes(user.getUsername().getBytes()).toString());
        token.setUserId(user.getId());
        token.setCreatedDateTime(LocalDateTime.now());

        token = tokenDao.create(token);
        return token;
    }

    @Override
    public RegistrationToken getByToken(String token) {

        return tokenDao.findByToken(token);
    }

    @Override
    public RegistrationToken getTokenById(Integer id) {

        return tokenDao.getById(id);
    }

    @Override
    public RegistrationToken getTokenByUser(User user) {

        return tokenDao.findByUser(user.getId());
    }

    @Override
    public boolean tokenIsValid(String token) {

        RegistrationToken registrationToken = tokenDao.findByToken(token);

        if (registrationToken == null) return false;
        return LocalDateTime.now().isBefore(registrationToken.calculateExpiryDate());
    }

    @Override
    public void removeToken(String token) {

        RegistrationToken registrationToken = getByToken(token);
        tokenDao.delete(registrationToken);

    }
}
