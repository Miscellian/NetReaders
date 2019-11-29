package com.netreaders.service.impl;

import com.netreaders.dao.announcement.AnnouncementDao;
import com.netreaders.models.Announcement;
import com.netreaders.service.AnnouncementService;
import com.netreaders.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AnnouncementImpl implements AnnouncementService {

    private final AnnouncementDao announcementDao;
    private final BookService bookService;

    @Override
    public Announcement findById(Integer id) {

        Announcement announcement = announcementDao.getById(id);
        announcement.setBooks(bookService.findByAnnouncementId(announcement.getId()));
        return announcement;
    }

    @Override
    public Collection<Announcement> findByGenre(Integer genreId, Integer amount, Integer offset) {

        Collection<Announcement> announcements = announcementDao.findByGenre(genreId, amount, offset);

        announcements.forEach(announcement ->
                announcement.setBooks(bookService.findByAnnouncementWithGenre(announcement.getId(), genreId))
        );

        return announcements;
    }

    @Override
    public Collection<Announcement> findByAuthor(Integer authorId, Integer amount, Integer offset) {

        Collection<Announcement> announcements = announcementDao.findByAuthor(authorId, amount, offset);

        announcements.forEach(announcement ->
                announcement.setBooks(bookService.findByAnnouncementWithAuthor(announcement.getId(), authorId))
        );

        return announcements;
    }

    @Override
    public Collection<Announcement> findAll(Integer amount, Integer offset) {

        return announcementDao.findAll(amount, offset);
    }
}