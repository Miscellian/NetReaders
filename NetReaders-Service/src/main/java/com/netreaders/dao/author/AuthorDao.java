package com.netreaders.dao.author;

import com.netreaders.dao.GenericDao;
import com.netreaders.models.Author;

import java.sql.SQLException;
import java.util.Collection;

public interface AuthorDao extends GenericDao<Author, Integer> {

    Collection<Author> getByBookId(int id) throws SQLException;
}
