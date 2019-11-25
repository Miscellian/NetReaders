package com.netreaders.controller;

import com.netreaders.models.Author;
import com.netreaders.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("all")
    @CrossOrigin(origins = "http://localhost:8080")
    public Collection<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping(value = "{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Author getAuthorById(@PathVariable Integer id) {
        return authorService.findAuthorById(id);
    }
}
