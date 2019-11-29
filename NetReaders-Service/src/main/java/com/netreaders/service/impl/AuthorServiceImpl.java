package com.netreaders.service.impl;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.models.Author;
import com.netreaders.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Author getAuthorById(Integer id) {

        return authorDao.getById(id);
    }

    @Override
    public Collection<Author> getAllAuthors() {

        return authorDao.getAll();
    }
}
