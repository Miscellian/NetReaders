package com.netreaders.authors.dao.interfaces;

import java.sql.SQLException;
import java.util.Collection;

import com.netreaders.models.Author;

public interface AuthorDataStore {
	Collection<Author> getAll() throws SQLException;
	Author getById(int id) throws SQLException;
	Collection<Author> getByBookId(int id) throws SQLException;
}
