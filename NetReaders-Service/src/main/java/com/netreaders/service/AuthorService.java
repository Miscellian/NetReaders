package com.netreaders.service;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Author;

import java.util.Collection;

public interface AuthorService {

    Author findById(Integer id) throws DataBaseSQLException;

    Collection<Author> getAll() throws DataBaseSQLException;
}
