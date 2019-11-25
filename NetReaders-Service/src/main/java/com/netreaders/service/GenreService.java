package com.netreaders.service;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Genre;

import java.util.Collection;

public interface GenreService {

    Genre findGenreById(Integer genreId) throws DataBaseSQLException;

    Collection<Genre> getAllGenres() throws DataBaseSQLException;
}
