package com.netreaders.service;

import com.netreaders.dao.annoucement.AnnouncementDao;
import com.netreaders.dao.book.BookDao;
import com.netreaders.dto.AnnouncementDto;
import com.netreaders.models.Announcement;
import com.netreaders.models.ResponseMessage;
import com.netreaders.utils.ResponseMessagePrepearer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementDao announcementDao;

    @Autowired
    private BookDao bookDao;

    public ResponseMessage<Collection<Announcement>> getAllAnnouncements() {
        ResponseMessage<Collection<Announcement>> message = new ResponseMessage<>();
        try {
            message.setObj(announcementDao.getAll());
        } catch (Exception e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<AnnouncementDto> findAnnouncementById(String id) {
        ResponseMessage<AnnouncementDto> message = new ResponseMessage<>();
        try {
            int numericId = Integer.parseInt(id);
            Announcement announcement = announcementDao.getById(numericId);
            message.setObj(tryCreateAnnouncementDto(announcement));
        } catch (NumberFormatException e) {
            ResponseMessagePrepearer.prepareMessage(message, "Invalid numeric parameters");
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Collection<AnnouncementDto>> findAnnouncementsByGenre(String genre_id, String amount, String offset) {
        ResponseMessage<Collection<AnnouncementDto>> message = new ResponseMessage<>();
        try {
            int numericGenreId = Integer.parseInt(genre_id);
            int numericAmount = Integer.parseInt(amount);
            int numericOffset = Integer.parseInt(offset);
            Collection<Announcement> announcements = announcementDao.findAnnouncementsByGenre(numericGenreId, numericAmount, numericOffset);
            Collection<AnnouncementDto> announcementDtos = announcements.stream()
                    .map(this::tryCreateAnnouncementDto)
                    .collect(Collectors.toCollection(ArrayList::new));
            message.setObj(announcementDtos);
        } catch (NumberFormatException e) {
            ResponseMessagePrepearer.prepareMessage(message, "Invalid numeric parameters");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Collection<AnnouncementDto>> findAnnouncementsByAuthor(String author_id, String amount, String offset) {
        ResponseMessage<Collection<AnnouncementDto>> message = new ResponseMessage<>();
        try {
            int numericAuthorId = Integer.parseInt(author_id);
            int numericAmount = Integer.parseInt(amount);
            int numericOffset = Integer.parseInt(offset);
            Collection<Announcement> announcements = announcementDao.findAnnouncementsByAuthor(numericAuthorId, numericAmount, numericOffset);
            Collection<AnnouncementDto> announcementDtos = announcements.stream()
                    .map(this::tryCreateAnnouncementDto)
                    .collect(Collectors.toCollection(ArrayList::new));
            message.setObj(announcementDtos);
        } catch (NumberFormatException e) {
            ResponseMessagePrepearer.prepareMessage(message, "Invalid numeric parameters");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    private AnnouncementDto tryCreateAnnouncementDto(Announcement announcement) throws RuntimeException {
        try {
            return new AnnouncementDto(
                    bookDao.getByAnnouncementId(announcement.getId()),
                    announcement
            );
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
