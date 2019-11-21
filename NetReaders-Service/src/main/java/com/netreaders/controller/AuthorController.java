package com.netreaders.controller;

import com.netreaders.models.Author;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorController {
	
	private final AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping("all")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseMessage<Collection<Author>> GetAllAuthors() {
		return authorService.getAll();
	}
	
	@GetMapping(value = "{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseMessage<Author> GetAuthorById(@PathVariable String id) {
		ResponseMessage<Author> response = authorService.getById(id);
		return response;
	}
}
