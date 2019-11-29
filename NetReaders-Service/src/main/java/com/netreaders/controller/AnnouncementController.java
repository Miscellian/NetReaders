package com.netreaders.controller;

import com.netreaders.models.Announcement;
import com.netreaders.service.AnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping(value = "{id}")
    public Announcement getById(@PathVariable() Integer id) {

        return announcementService.findById(id);
    }

    @GetMapping(value = "bygenre")
    public Collection<Announcement> getByGenre(
            @RequestParam(name = "id") Integer genreId,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {

        return announcementService.findByGenre(genreId, amount, offset);
    }

    @GetMapping(value = "byauthor")
    public Collection<Announcement> getByAuthor(
            @RequestParam(name = "id") Integer authorId,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {

        return announcementService.findByAuthor(authorId, amount, offset);
    }

    @GetMapping("all")
    public Collection<Announcement> getAllAnnouncements(
            @RequestParam(name = "amount", defaultValue = "0") Integer amount,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset) {

        return announcementService.findAll(amount, offset);
    }
}
