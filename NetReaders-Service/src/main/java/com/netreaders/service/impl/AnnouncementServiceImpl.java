package com.netreaders.service.impl;

import com.netreaders.dao.annoucement.AnnouncementDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Announcement;
import com.netreaders.service.AnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementDao announcementDao;
    private final BookServiceImpl bookServiceImpl;

    public Collection<Announcement> getAllAnnouncements() throws DataBaseSQLException {

        Collection<Announcement> announcements = announcementDao.getAll();
        return createDtoCollection(announcements);
    }

    public Announcement findAnnouncementById(Integer id) throws DataBaseSQLException {

        Announcement announcement = announcementDao.getById(id);
        return modelToDto(announcement);
    }

    public Collection<Announcement> findAnnouncementsByGenre(Integer genre_id, Integer amount, Integer offset) throws DataBaseSQLException {

        Collection<Announcement> announcements = announcementDao.findAnnouncementsByGenre(genre_id, amount, offset);
        //TODO
        return createDtoCollection(announcements);
    }

    public Collection<Announcement> findAnnouncementsByAuthor(Integer author_id, Integer amount, Integer offset) throws DataBaseSQLException {

        Collection<Announcement> announcements = announcementDao.findAnnouncementsByAuthor(author_id, amount, offset);
        //TODO
        return createDtoCollection(announcements);
    }

    private Collection<Announcement> createDtoCollection(Collection<Announcement> announcements) {

        return announcements.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    private Announcement modelToDto(Announcement announcement) throws DataBaseSQLException {

        announcement.setBooks(bookServiceImpl.getByAnnouncementId(announcement.getId()));
        return announcement;
    }
}
