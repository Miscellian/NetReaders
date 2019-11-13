package com.netreaders.genres.controller;
import java.util.Collection;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.netreaders.models.Genre;
import com.netreaders.models.ResponseMessage;
import com.netreaders.genres.service.GenreService;

@RestController
public class GenreController {
	private final GenreService genreService;

	public GenreController(GenreService genreService) {
		this.genreService = genreService;
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("api/genres")
	public ResponseMessage<Collection<Genre>> GetAllGenres() {
		return genreService.getAll();
	}
	
	@GetMapping(value = "api/genres/{id}")
	public ResponseMessage<Genre> GetGenreById(@PathVariable String id) {
		ResponseMessage<Genre> response = genreService.getById(id);
		return response;
	}
}
