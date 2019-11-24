package com.netreaders.controller;

import com.netreaders.models.Announcement;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.AnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/announcements")
@AllArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("all")
    public ResponseMessage<Collection<Announcement>> getAllAnnouncements() {
        return announcementService.getAllAnnouncements();
    }

    @GetMapping(value = "{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Announcement> getById(@PathVariable() Integer id) {
        return announcementService.findAnnouncementById(id);
    }

    @GetMapping(value = "bygenre")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Collection<Announcement>> getByGenre(
            @RequestParam(name = "id") Integer genreid,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {
        return announcementService.findAnnouncementsByGenre(genreid, amount, offset);
    }

    @GetMapping(value = "byauthor")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Collection<Announcement>> getByAuthor(
            @RequestParam(name = "id") Integer authorid,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {
        return announcementService.findAnnouncementsByAuthor(authorid, amount, offset);
    }
}
