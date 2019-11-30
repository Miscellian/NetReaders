package com.netreaders.controller;

import com.netreaders.models.Book;
import com.netreaders.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "{id}")
    public Book getBookById(@PathVariable Integer id) {

        return bookService.findBookById(id);
    }

    @GetMapping(value = "range")
    public Collection<Book> getById(
            @RequestParam(name = "amount", defaultValue = "0") Integer amount,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset) {

        return bookService.getBooksById(amount, offset);
    }

    @GetMapping(value = "bygenre")
    public Collection<Book> getByGenre(
            @RequestParam(name = "id") Integer genreId,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {

        return bookService.findBooksByGenre(genreId, amount, offset);
    }

    @GetMapping(value = "byauthor")
    public Collection<Book> getByAuthor(
            @RequestParam(name = "id") Integer authorId,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {

        return bookService.findBooksByAuthor(authorId, amount, offset);
    }

    @GetMapping(value = "byname")
    public Collection<Book> getByName(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {

        return bookService.getBooksByName(name, amount, offset);
    }

    @GetMapping(value = "byusername")
    public Collection<Book> getByUserName(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {

        return bookService.getBooksUsername(username, amount, offset);
    }

    @GetMapping(value = "count")
    public Integer getCount() {

        return bookService.getCount();
    }

    @GetMapping(value = "countByAuthor")
    public Integer getCountByAuthor(
            @RequestParam(name = "id") Integer authorId) {

        return bookService.getCountByAuthor(authorId);
    }

    @GetMapping(value = "countByGenre")
    public Integer getCountByGenre(
            @RequestParam(name = "id") Integer genreId) {

        return bookService.getCountByGenre(genreId);
    }

    @GetMapping(value = "countByName")
    public Integer getCountByName(
            @RequestParam(name = "name") String name) {

        return bookService.getCountByName(name);
    }

    @GetMapping(value = "countByUsername")
    public Integer getCountByUsername(
            @RequestParam(name = "username") String username) {

        return bookService.getCountByUsername(username);
    }
}
