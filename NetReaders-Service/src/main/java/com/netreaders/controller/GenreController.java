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

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("all")
    public Collection<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping(value = "{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Genre getGenreById(@PathVariable Integer id) {
        return genreService.findGenreById(id);
    }
}
