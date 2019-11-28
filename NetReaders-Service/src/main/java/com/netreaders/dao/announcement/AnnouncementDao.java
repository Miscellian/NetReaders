package com.netreaders.dao.announcement;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Announcement;

import java.util.Collection;

public interface AnnouncementDao extends GenericDao<Announcement, Integer> {

    Collection<Announcement> findByGenre(Integer genreId, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Announcement> findByAuthor(Integer authorId, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Announcement> findAll(Integer amount, Integer offset) throws DataBaseSQLException;
}
