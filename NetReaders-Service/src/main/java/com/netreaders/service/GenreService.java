package com.netreaders.service;

import com.netreaders.dao.genres.GenreDao;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Genre;
import com.netreaders.models.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GenreService {

    @Autowired
    private GenreDao genreDao;

    public ResponseMessage<Collection<Genre>> getAll() throws DataBaseSQLException {

        ResponseMessage<Collection<Genre>> message = new ResponseMessage<>();
        message.setObj(genreDao.getAll());

        return message;
    }

    public ResponseMessage<Genre> getById(Integer id) throws DataBaseSQLException {

        ResponseMessage<Genre> message = new ResponseMessage<>();
        message.setObj(genreDao.getById(id));

        return message;
    }
}
