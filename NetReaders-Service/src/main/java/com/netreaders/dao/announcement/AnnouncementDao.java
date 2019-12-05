package com.netreaders.dao.announcement;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Announcement;

import java.util.Collection;

public interface AnnouncementDao extends GenericDao<Announcement, Integer> {

    Collection<Announcement> findByGenre(Integer genreId, Integer year, Integer month) throws DataBaseSQLException;

    Collection<Announcement> findByAuthor(Integer authorId, Integer year, Integer month) throws DataBaseSQLException;

    Collection<Announcement> findAll(Integer year, Integer month) throws DataBaseSQLException;
}
