package com.netreaders.dao.genres;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Genre;

import java.util.Collection;

public interface GenreDao extends GenericDao<Genre, Integer> {

    Collection<Genre> getByBookId(int id) throws DataBaseSQLException;
}
