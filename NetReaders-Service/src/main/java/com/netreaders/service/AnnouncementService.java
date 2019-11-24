package com.netreaders.service;

import com.netreaders.dao.annoucement.AnnouncementDao;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Announcement;
import com.netreaders.models.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnnouncementService {

    private final AnnouncementDao announcementDao;
    private final BookService bookService;

    public ResponseMessage<Collection<Announcement>> getAllAnnouncements() throws DataBaseSQLException {

        ResponseMessage<Collection<Announcement>> message = new ResponseMessage<>();
        Collection<Announcement> announcements = announcementDao.getAll();
        message.setObj(createDtoCollection(announcements));

        return message;
    }

    public ResponseMessage<Announcement> findAnnouncementById(Integer id) throws DataBaseSQLException {

        ResponseMessage<Announcement> message = new ResponseMessage<>();
        Announcement announcement = announcementDao.getById(id);
        message.setObj(modelToDto(announcement));

        return message;
    }

    public ResponseMessage<Collection<Announcement>> findAnnouncementsByGenre(Integer genre_id, Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<Announcement>> message = new ResponseMessage<>();
        Collection<Announcement> announcements = announcementDao.findAnnouncementsByGenre(genre_id, amount, offset);
        Collection<Announcement> announcementDtos = createDtoCollection(announcements);

        //TODO

        message.setObj(createDtoCollection(announcements));

        return message;
    }

    public ResponseMessage<Collection<Announcement>> findAnnouncementsByAuthor(Integer author_id, Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<Announcement>> message = new ResponseMessage<>();
        Collection<Announcement> announcements = announcementDao.findAnnouncementsByAuthor(author_id, amount, offset);

        //TODO

        message.setObj(createDtoCollection(announcements));

        return message;
    }

    private Collection<Announcement> createDtoCollection(Collection<Announcement> announcements) {

        return announcements.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    private Announcement modelToDto(Announcement announcement) throws DataBaseSQLException {

        announcement.setBooks(bookService.getByAnnouncementId(announcement.getId()));

        return announcement;
    }

}
