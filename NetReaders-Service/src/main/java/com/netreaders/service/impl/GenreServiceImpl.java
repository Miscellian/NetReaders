package com.netreaders.service.impl;

import com.netreaders.dao.genres.GenreDao;
import com.netreaders.models.Genre;
import com.netreaders.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public Genre getGenreById(Integer id) {

        return genreDao.getById(id);
    }

    @Override
    public Collection<Genre> getAllGenres() {

        return genreDao.getAll();
    }
}
