package com.netreaders.controller;

import com.netreaders.models.Author;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("all")
    public ResponseMessage<Collection<Author>> getAllAuthors() {
        return authorService.getAll();
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public ResponseMessage<Author> getAuthorById(@PathVariable Integer id) {
        return authorService.getById(id);
    }
}
