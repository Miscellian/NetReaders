package com.netreaders.service;

import com.netreaders.models.Author;

import java.util.Collection;

public interface AuthorService {

    Author getAuthorById(Integer id);

    Collection<Author> getAllAuthors();
}
