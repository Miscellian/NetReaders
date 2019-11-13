package com.netreaders.service;

import com.netreaders.dao.genres.GenreDao;
import com.netreaders.models.Genre;
import com.netreaders.models.ResponseMessage;
import com.netreaders.utils.ResponseMessagePrepearer;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GenreService {
    private final GenreDao genreDao;

    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public ResponseMessage<Collection<Genre>> getAll() {
        ResponseMessage<Collection<Genre>> message = new ResponseMessage<>();
        try {
            message.setObj(genreDao.getAll());
        } catch (Exception e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Genre> getById(String id) {
        ResponseMessage<Genre> message = new ResponseMessage<>();

        try {
            int numericId = Integer.parseInt(id);
            message.setObj(genreDao.getById(numericId));
        } catch (NumberFormatException e) {
            ResponseMessagePrepearer.prepareMessage(message, "Invalid Genre id");
        } catch (Exception e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }
}
