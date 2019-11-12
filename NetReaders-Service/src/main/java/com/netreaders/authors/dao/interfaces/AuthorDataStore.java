package com.netreaders.authors.dao.interfaces;

import java.util.Collection;

import com.netreaders.models.Author;

public interface AuthorDataStore {
	Collection<Author> getAll();
	Author getById(int id);
	Author getByBookId(int id);
}
