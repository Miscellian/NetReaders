package com.netreaders.service;

import com.netreaders.models.Announcement;

import java.util.Collection;

public interface AnnouncementService {

    Announcement findById(Integer id);

    Collection<Announcement> findByGenre(Integer genreId, Integer amount, Integer offset);

    Collection<Announcement> findByAuthor(Integer authorId, Integer amount, Integer offset);

    Collection<Announcement> findAll(Integer amount, Integer offset);
}
