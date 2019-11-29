package com.netreaders.service;

import com.netreaders.models.Genre;

import java.util.Collection;

public interface GenreService {

    Genre getGenreById(Integer id);

    Collection<Genre> getAllGenres();
}
