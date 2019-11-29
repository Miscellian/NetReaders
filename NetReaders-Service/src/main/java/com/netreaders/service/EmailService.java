package com.netreaders.service;

import com.netreaders.models.RegistrationToken;
import com.netreaders.models.User;

public interface EmailService {

    void sendEmail(User user, RegistrationToken token);
}
