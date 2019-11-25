package com.netreaders.dao.author;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Author;

import java.util.Collection;

public interface AuthorDao extends GenericDao<Author, Integer> {

    Collection<Author> getByBookId(int id) throws DataBaseSQLException;
}
