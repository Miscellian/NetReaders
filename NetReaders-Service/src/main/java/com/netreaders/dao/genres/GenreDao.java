package com.netreaders.dao.genres;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Genre;

import java.util.Collection;

public interface GenreDao extends GenericDao<Genre, Integer> {

    Collection<Genre> getByBookId(Integer id) throws DataBaseSQLException;
}
