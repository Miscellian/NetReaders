package com.netreaders.dao.genres;

import com.netreaders.dao.GenericDao;
import com.netreaders.models.Genre;

import java.sql.SQLException;
import java.util.Collection;

public interface GenreDao extends GenericDao<Genre, Integer> {

    Collection<Genre> getByBookId(int id) throws SQLException;
}
