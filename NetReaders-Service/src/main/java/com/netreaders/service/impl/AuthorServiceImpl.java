package com.netreaders.service.impl;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Author;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public ResponseMessage<Author> findAuthorById(Integer id) throws DataBaseSQLException {

        ResponseMessage<Author> message = new ResponseMessage<>();
        message.setObj(authorDao.getById(id));

        return message;
    }

    public ResponseMessage<Collection<Author>> getAllAuthors() throws DataBaseSQLException {

        ResponseMessage<Collection<Author>> message = new ResponseMessage<>();
        message.setObj(authorDao.getAll());

        return message;
    }
}
