package com.netreaders.controller;

import com.netreaders.dto.BookDto;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<BookDto> getBookById(@PathVariable Integer id) {
        return bookService.findBookById(id);
    }

    @GetMapping(value = "range")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Collection<BookDto>> getById(
            @RequestParam(name = "amount", defaultValue = "0") Integer amount,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        return bookService.getById(amount, offset);
    }

    @GetMapping(value = "byauthor")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Collection<BookDto>> getByAuthor(
            @RequestParam(name = "id") Integer authorid,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {
        return bookService.findBooksByAuthor(authorid, amount, offset);
    }

    @GetMapping(value = "byname")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Collection<BookDto>> getByName(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {
        return bookService.getByName(name, amount, offset);
    }

    @GetMapping(value = "bygenre")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<Collection<BookDto>> getByGenre(
            @RequestParam(name = "id") Integer genreid,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {
        return bookService.findBooksByGenre(genreid, amount, offset);
    }

    @GetMapping(value = "count")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Integer> getCount() {
        return bookService.getCount();
    }

    @GetMapping(value = "countByAuthor")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Integer> getCountByAuthor(
            @RequestParam(name = "id") Integer authorid) {
        return bookService.getCountByAuthor(authorid);
    }

    @GetMapping(value = "countByGenre")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Integer> getCountByGenre(
            @RequestParam(name = "id") Integer genreid) {
        return bookService.getCountByGenre(genreid);
    }

    @GetMapping(value = "countByName")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Integer> getCountByName(
            @RequestParam(name = "name") String name) {
        return bookService.getCountByName(name);
    }
}
