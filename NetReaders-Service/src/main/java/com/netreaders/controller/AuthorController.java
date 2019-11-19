package com.netreaders.controller;

import com.netreaders.models.Author;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("all")
    public ResponseMessage<Collection<Author>> getAllAuthors() {
        return authorService.getAll();
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public ResponseMessage<Author> getAuthorById(@PathVariable Integer id) {
        ResponseMessage<Author> response = authorService.getById(id);
        return response;
    }
}
