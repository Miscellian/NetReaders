package com.netreaders.genres.dao.interfaces;

import java.sql.SQLException;
import java.util.Collection;
import com.netreaders.models.Genre;

public interface GenreDataStore {
	Collection<Genre> getAll() throws SQLException;
	Genre getById(int id) throws SQLException;
	Collection<Genre> getByBookId(int id) throws SQLException;
}
