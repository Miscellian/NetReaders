package com.netreaders.controller;

import com.netreaders.models.Genre;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.GenreService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseMessage<Collection<Genre>> GetAllGenres() {
		return genreService.getAll();
	}
	
	@GetMapping(value = "{id}")
	@CrossOrigin(origins = "http://localhost:8080")
	public ResponseMessage<Genre> GetGenreById(@PathVariable String id) {
		ResponseMessage<Genre> response = genreService.getById(id);
		return response;
	}
}
