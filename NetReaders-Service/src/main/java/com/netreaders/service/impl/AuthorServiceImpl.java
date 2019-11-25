package com.netreaders.service.impl;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Author;
import com.netreaders.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public Author findAuthorById(Integer id) throws DataBaseSQLException {

        return authorDao.getById(id);
    }

    public Collection<Author> getAllAuthors() throws DataBaseSQLException {

        return authorDao.getAll();
    }
}
