package com.netreaders.service;

import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Author;
import com.netreaders.models.ResponseMessage;

import java.util.Collection;

public interface AuthorService {

    ResponseMessage<Author> findAuthorById(Integer id) throws DataBaseSQLException;

    ResponseMessage<Collection<Author>> getAllAuthors() throws DataBaseSQLException;
}
