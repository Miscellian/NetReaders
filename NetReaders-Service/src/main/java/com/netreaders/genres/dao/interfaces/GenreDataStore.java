package com.netreaders.genres.dao.interfaces;

import java.util.Collection;
import com.netreaders.models.Genre;

public interface GenreDataStore {
	Collection<Genre> getAll();
	Genre getById(int id);
	Genre getByBookId(int id);
}
