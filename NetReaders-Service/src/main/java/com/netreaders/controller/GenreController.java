package com.netreaders.controller;

import com.netreaders.models.Genre;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/genres")
@CrossOrigin(origins = "http://localhost:8080")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("all")
    public ResponseMessage<Collection<Genre>> getAllGenres() {
        return genreService.getAll();
    }

    @GetMapping(value = "{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Genre> getGenreById(@PathVariable Integer id) {
        return genreService.getById(id);
    }
}
