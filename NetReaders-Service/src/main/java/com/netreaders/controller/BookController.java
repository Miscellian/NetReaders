package com.netreaders.controller;

import com.netreaders.dto.BookDto;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<BookDto> GetBookById(@PathVariable String id) {
        return bookService.findBookById(id);
    }

    @GetMapping(value = "range")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<Collection<BookDto>> GetById(
            @RequestParam(name = "amount", defaultValue = "0") String amount,
            @RequestParam(name = "offset", defaultValue = "0") String offset) {
        return bookService.getById(amount, offset);
    }

    @GetMapping(value = "bygenre")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<Collection<BookDto>> GetByGenre(
            @RequestParam(name = "id") String genreid,
            @RequestParam(name = "amount") String amount,
            @RequestParam(name = "offset") String offset) {
        return bookService.findBooksByGenre(genreid, amount, offset);
    }

    @GetMapping(value = "byauthor")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<Collection<BookDto>> GetByAuthor(
            @RequestParam(name = "id") String authorid,
            @RequestParam(name = "amount") String amount,
            @RequestParam(name = "offset") String offset) {
        return bookService.findBooksByAuthor(authorid, amount, offset);
    }

    @GetMapping(value = "byname")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<Collection<BookDto>> GetByName(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "amount") String amount,
            @RequestParam(name = "offset") String offset) {
        return bookService.getByName(name, amount, offset);
    }

    @GetMapping(value = "byusername")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<Collection<BookDto>> GetByUserName(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "amount") String amount,
            @RequestParam(name = "offset") String offset) {
        return bookService.getByUsername(username, amount, offset);
    }

    @GetMapping(value = "count")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<Integer> GetCount() {
        return bookService.getCount();
    }

    @GetMapping(value = "countByAuthor")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<Integer> GetCountByAuthor(
            @RequestParam(name = "id") String authorid) {
        return bookService.getCountByAuthor(authorid);
    }

    @GetMapping(value = "countByGenre")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<Integer> GetCountByGenre(
            @RequestParam(name = "id") String genreid) {
        return bookService.getCountByGenre(genreid);
    }

    @GetMapping(value = "countByName")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<Integer> GetCountByName(
            @RequestParam(name = "name") String name) {
        return bookService.getCountByName(name);
    }

    @GetMapping(value = "countByUsername")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<Integer> GetCountByUsername(
            @RequestParam(name = "username") String username) {
        return bookService.getCountByUsername(username);
    }
}
