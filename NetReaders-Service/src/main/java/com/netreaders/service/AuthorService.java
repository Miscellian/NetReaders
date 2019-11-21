package com.netreaders.service;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Author;
import com.netreaders.models.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthorService {

    private final AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public ResponseMessage<Collection<Author>> getAll() throws DataBaseSQLException {

        ResponseMessage<Collection<Author>> message = new ResponseMessage<>();
        message.setObj(authorDao.getAll());

        return message;
    }

    public ResponseMessage<Author> getById(Integer id) throws DataBaseSQLException {

        ResponseMessage<Author> message = new ResponseMessage<>();
        message.setObj(authorDao.getById(id));

        return message;
    }
}
