package com.netreaders.service;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Genre;

import java.util.Collection;

public interface GenreService {

    Genre findById(Integer genreId) throws DataBaseSQLException;

    Collection<Genre> getAll() throws DataBaseSQLException;
}
