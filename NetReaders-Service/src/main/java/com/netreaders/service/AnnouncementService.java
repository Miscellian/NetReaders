package com.netreaders.service;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Announcement;

import java.util.Collection;

public interface AnnouncementService {

    Announcement findAnnouncementById(Integer id) throws DataBaseSQLException;

    Collection<Announcement> findAnnouncementsByGenre(Integer genreId, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Announcement> findAnnouncementsByAuthor(Integer authorId, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Announcement> getAllAnnouncements() throws DataBaseSQLException;
}
