package com.netreaders.service;

import com.netreaders.dao.user.UserDao;
import com.netreaders.models.ResponseMessage;
import com.netreaders.models.User;
import com.netreaders.utils.ResponseMessagePrepearer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public ResponseMessage<User> findUserByUsername(String username) {
        ResponseMessage<User> message = new ResponseMessage<>();
        try {
            User user = userDao.findByUsername(username);
            message.setObj(user);
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }
}
