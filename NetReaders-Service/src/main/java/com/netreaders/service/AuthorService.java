package com.netreaders.service;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.models.Author;
import com.netreaders.models.ResponseMessage;
import com.netreaders.utils.ResponseMessagePrepearer;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class AuthorService {

    private final AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public ResponseMessage<Collection<Author>> getAll() {
        ResponseMessage<Collection<Author>> message = new ResponseMessage<>();
        try {
            message.setObj(authorDao.getAll());
        } catch (SQLException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Author> getById(String id) {
        ResponseMessage<Author> message = new ResponseMessage<>();

        try {
            int numericId = Integer.parseInt(id);
            message.setObj(authorDao.getById(numericId));
        } catch (NumberFormatException e) {
            ResponseMessagePrepearer.prepareMessage(message, "Invalid author id");
        } catch (SQLException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

}
