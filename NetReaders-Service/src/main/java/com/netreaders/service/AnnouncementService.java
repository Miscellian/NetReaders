package com.netreaders.service;

import com.netreaders.dao.annoucement.AnnouncementDao;
import com.netreaders.dao.book.BookDao;
import com.netreaders.dto.AnnouncementDto;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Announcement;
import com.netreaders.models.ResponseMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final AnnouncementDao announcementDao;

    private final BookDao bookDao;

    public AnnouncementService(AnnouncementDao announcementDao, BookDao bookDao) {
        this.announcementDao = announcementDao;
        this.bookDao = bookDao;
    }

    public ResponseMessage<Collection<AnnouncementDto>> getAllAnnouncements() throws DataBaseSQLException {

        ResponseMessage<Collection<AnnouncementDto>> message = new ResponseMessage<>();
        Collection<Announcement> announcements = announcementDao.getAll();
        Collection<AnnouncementDto> announcementDtos = announcements.stream()
                .map(this::tryCreateAnnouncementDto)
                .collect(Collectors.toCollection(ArrayList::new));
        message.setObj(announcementDtos);

        return message;
    }

    public ResponseMessage<AnnouncementDto> findAnnouncementById(Integer id) throws DataBaseSQLException {

        ResponseMessage<AnnouncementDto> message = new ResponseMessage<>();
        Announcement announcement = announcementDao.getById(id);
        message.setObj(tryCreateAnnouncementDto(announcement));

        return message;
    }

    public ResponseMessage<Collection<AnnouncementDto>> findAnnouncementsByGenre(Integer genre_id, Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<AnnouncementDto>> message = new ResponseMessage<>();
        Collection<Announcement> announcements = announcementDao.findAnnouncementsByGenre(genre_id, amount, offset);
        Collection<AnnouncementDto> announcementDtos = announcements.stream()
                .map(this::tryCreateAnnouncementDto)
                .collect(Collectors.toCollection(ArrayList::new));
        message.setObj(announcementDtos);

        return message;
    }

    public ResponseMessage<Collection<AnnouncementDto>> findAnnouncementsByAuthor(Integer author_id, Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<AnnouncementDto>> message = new ResponseMessage<>();
        Collection<Announcement> announcements = announcementDao.findAnnouncementsByAuthor(author_id, amount, offset);
        Collection<AnnouncementDto> announcementDtos = announcements.stream()
                .map(this::tryCreateAnnouncementDto)
                .collect(Collectors.toCollection(ArrayList::new));
        message.setObj(announcementDtos);

        return message;
    }

    private AnnouncementDto tryCreateAnnouncementDto(Announcement announcement) throws DataBaseSQLException {

        return new AnnouncementDto(
                bookDao.getByAnnouncementId(announcement.getId()),
                announcement
        );
    }
}
