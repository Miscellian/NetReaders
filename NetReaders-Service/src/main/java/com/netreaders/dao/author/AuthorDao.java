package com.netreaders.dao.author;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Author;
import com.netreaders.models.Genre;

import java.util.Collection;

public interface AuthorDao extends GenericDao<Author, Integer> {

    Collection<Author> getByBookId(Integer id) throws DataBaseSQLException;
    
    Author getByName(String name) throws DataBaseSQLException;
    
    boolean existsByName(String name) throws DataBaseSQLException;
}
