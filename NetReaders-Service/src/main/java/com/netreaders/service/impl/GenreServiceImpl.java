package com.netreaders.service.impl;

import com.netreaders.dao.genres.GenreDao;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Genre;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;


    public ResponseMessage<Genre> findGenreById(Integer id) throws DataBaseSQLException {

        ResponseMessage<Genre> message = new ResponseMessage<>();
        message.setObj(genreDao.getById(id));

        return message;
    }

    public ResponseMessage<Collection<Genre>> getAllGenres() throws DataBaseSQLException {

        ResponseMessage<Collection<Genre>> message = new ResponseMessage<>();
        message.setObj(genreDao.getAll());

        return message;
    }
}
