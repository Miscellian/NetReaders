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

    @GetMapping(value = "all")
    public Collection<Announcement> getById(
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "month") Integer month) {

        return announcementService.findAll(year, month);
    }

    @GetMapping(value = "bygenre")
    public Collection<Announcement> getByGenre(
            @RequestParam(name = "id") Integer genreId,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "month") Integer month) {

        return announcementService.findByGenre(genreId, year, month);
    }

    @GetMapping(value = "byauthor")
    public Collection<Announcement> getByAuthor(
            @RequestParam(name = "id") Integer authorId,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "month") Integer month) {

        return announcementService.findByAuthor(authorId, year, month);
    }
}
