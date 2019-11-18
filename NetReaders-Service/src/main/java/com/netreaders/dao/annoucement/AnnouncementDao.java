package com.netreaders.dao.annoucement;

import com.netreaders.dao.GenericDao;
import com.netreaders.models.Announcement;

import java.sql.SQLException;
import java.util.Collection;

public interface AnnouncementDao extends GenericDao<Announcement, Integer> {

    Collection<Announcement> findAnnouncementsByGenre(int genre_id, int amount, int offset) throws SQLException;

    Collection<Announcement> findAnnouncementsByAuthor(int author_id, int amount, int offset) throws SQLException;

    Collection<Announcement> getById(int amount, int offset) throws SQLException;
}
