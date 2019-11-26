package com.netreaders.controller;

import com.netreaders.models.Genre;
import com.netreaders.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/genres")
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("all")
    public Collection<Genre> getAllGenres() {

        return genreService.getAll();
    }

    @GetMapping(value = "{id}")
    public Genre getGenreById(@PathVariable Integer id) {

        return genreService.findById(id);
    }
}
