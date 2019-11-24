package com.netreaders.service;

import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Genre;
import com.netreaders.models.ResponseMessage;

import java.util.Collection;

public interface GenreService {

    ResponseMessage<Genre> findGenreById(Integer genreId) throws DataBaseSQLException;

    ResponseMessage<Collection<Genre>> getAllGenres() throws DataBaseSQLException;
}
