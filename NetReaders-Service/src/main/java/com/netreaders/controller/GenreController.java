package com.netreaders.controller;

import com.netreaders.models.Genre;
import com.netreaders.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping(value = "{id}")
    public Genre getGenreById(@PathVariable Integer id) {

        return genreService.getGenreById(id);
    }

    @GetMapping("all")
    public Collection<Genre> getAllGenres() {

        return genreService.getAllGenres();
    }
}
