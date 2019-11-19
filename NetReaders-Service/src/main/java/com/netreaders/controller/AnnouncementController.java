package com.netreaders.controller;

import com.netreaders.dto.AnnouncementDto;
import com.netreaders.models.Announcement;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("all")
    public ResponseMessage<Collection<Announcement>> getAllAnnouncements() {
        return announcementService.getAllAnnouncements();
    }

    @GetMapping(value = "{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<AnnouncementDto> getById(@PathVariable() Integer id) {
        ResponseMessage<AnnouncementDto> response = announcementService.findAnnouncementById(id);
        return response;
    }

    @GetMapping(value = "bygenre")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Collection<AnnouncementDto>> getByGenre(
            @RequestParam(name = "id") Integer genreid,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {
        ResponseMessage<Collection<AnnouncementDto>> response = announcementService.findAnnouncementsByGenre(genreid, amount, offset);
        return response;
    }

    @GetMapping(value = "byauthor")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Collection<AnnouncementDto>> getByAuthor(
            @RequestParam(name = "id") Integer authorid,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {
        ResponseMessage<Collection<AnnouncementDto>> response = announcementService.findAnnouncementsByAuthor(authorid, amount, offset);
        return response;
    }
}