package com.netreaders.authors.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netreaders.authors.service.AuthorService;
import com.netreaders.models.Author;
import com.netreaders.models.ResponseMessage;

@RestController
public class AuthorController {
	
	private final AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("api/authors")
	public Collection<Author> GetAllGenres() {
		return authorService.getAll();
	}
	
	@GetMapping(value = "api/authors/{id}")
	@ResponseBody
	public Author GetGenreById(@PathVariable String id) {
		ResponseMessage<Author> response = authorService.getById(id);
		if(response.isSuccessful()) {
			return response.getObj();
		} else {
			return null;
		}
	}
}
