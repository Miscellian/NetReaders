package com.netreaders.controller;

import com.netreaders.models.Author;
import com.netreaders.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

	@GetMapping(value = "{id}")
	@ResponseBody
    public Author getAuthorById(@PathVariable Integer id) {

        return authorService.getAuthorById(id);
    }

    @GetMapping("all")
    public Collection<Author> getAllAuthors() {

        return authorService.getAllAuthors();
    }
}
