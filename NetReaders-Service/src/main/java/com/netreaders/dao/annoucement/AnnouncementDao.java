package com.netreaders.dao.annoucement;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Announcement;

import java.util.Collection;

public interface AnnouncementDao extends GenericDao<Announcement, Integer> {

    Collection<Announcement> findAnnouncementsByGenre(int genre_id, int amount, int offset) throws DataBaseSQLException;

    Collection<Announcement> findAnnouncementsByAuthor(int author_id, int amount, int offset) throws DataBaseSQLException;

    Collection<Announcement> getById(int amount, int offset) throws DataBaseSQLException;
}
