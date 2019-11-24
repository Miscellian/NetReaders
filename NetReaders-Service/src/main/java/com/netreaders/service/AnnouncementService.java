package com.netreaders.service;

import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Announcement;
import com.netreaders.models.ResponseMessage;

import java.util.Collection;

public interface AnnouncementService {

    ResponseMessage<Announcement> findAnnouncementById(Integer id) throws DataBaseSQLException;

    ResponseMessage<Collection<Announcement>> findAnnouncementsByGenre(Integer genreId, Integer amount, Integer offset) throws DataBaseSQLException;

    ResponseMessage<Collection<Announcement>> findAnnouncementsByAuthor(Integer authorId, Integer amount, Integer offset) throws DataBaseSQLException;

    ResponseMessage<Collection<Announcement>> getAllAnnouncements() throws DataBaseSQLException;
}
