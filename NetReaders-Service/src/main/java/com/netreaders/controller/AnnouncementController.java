package com.netreaders.controller;

import com.netreaders.models.Announcement;
import com.netreaders.service.AnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;

@RestController
@Validated
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
            @RequestParam(name = "month") @Max(12) @Min(1) Integer month) {

        return announcementService.findAll(year, month);
    }

    @GetMapping(value = "bygenre")
    public Collection<Announcement> getByGenre(
            @RequestParam(name = "id") Integer genreId,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "month") @Max(12) @Min(1) Integer month) {

        return announcementService.findByGenre(genreId, year, month);
    }

    @GetMapping(value = "byauthor")
    public Collection<Announcement> getByAuthor(
            @RequestParam(name = "id") Integer authorId,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "month") @Max(12) @Min(1) Integer month) {

        return announcementService.findByAuthor(authorId, year, month);
    }
}
