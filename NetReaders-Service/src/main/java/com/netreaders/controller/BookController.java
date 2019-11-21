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

    @GetMapping(value = "bygenre")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Collection<BookDto>> getByGenre(
            @RequestParam(name = "id") Integer genreid,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {
        return bookService.findBooksByGenre(genreid, amount, offset);
    }

    @GetMapping(value = "byauthor")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Collection<BookDto>> GetByAuthor(
            @RequestParam(name = "id") Integer authorid,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {
        return bookService.findBooksByAuthor(authorid, amount, offset);
    }

    @GetMapping(value = "byname")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseMessage<Collection<BookDto>> GetByName(
            @RequestParam(name = "name") String name) {
        return bookService.getByName(name);
    }

		public BookController(BookService bookService) {
			this.bookService = bookService;
		}

		@GetMapping(value = "{id}")
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseMessage<BookDto> GetBookById(@PathVariable String id) {
			ResponseMessage<BookDto> response = bookService.findBookById(id);
			return response;
		}
		@GetMapping(value = "range")
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseMessage<Collection<BookDto>> GetById(
				@RequestParam(name = "amount",defaultValue = "0") String amount,
				@RequestParam(name = "offset",defaultValue = "0") String offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.getById(amount, offset);
			return response;
		}

		@GetMapping(value = "bygenre")
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseMessage<Collection<BookDto>> GetByGenre(
				@RequestParam(name = "id") String genreid,
				@RequestParam(name = "amount") String amount,
				@RequestParam(name = "offset") String offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.findBooksByGenre(genreid, amount, offset);
			return response;
		}

		@GetMapping(value = "byauthor")
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseMessage<Collection<BookDto>> GetByAuthor(
				@RequestParam(name = "id") String authorid,
				@RequestParam(name = "amount") String amount,
				@RequestParam(name = "offset") String offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.findBooksByAuthor(authorid, amount, offset);
			return response;
		}

		@GetMapping(value = "byname")
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseMessage<Collection<BookDto>> GetByName(
				@RequestParam(name = "name") String name,
				@RequestParam(name = "amount") String amount,
				@RequestParam(name = "offset") String offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.getByName(name, amount, offset);
			return response;
		}

		@GetMapping(value = "count")
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseMessage<Integer> GetCount() {
			ResponseMessage<Integer> response = bookService.getCount();
			return response;
		}

		@GetMapping(value = "countByAuthor")
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseMessage<Integer> GetCountByAuthor(
				@RequestParam(name = "id") String authorid) {
			ResponseMessage<Integer> response = bookService.getCountByAuthor(authorid);
			return response;
		}

		@GetMapping(value = "countByGenre")
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseMessage<Integer> GetCountByGenre(
				@RequestParam(name = "id") String genreid) {
			ResponseMessage<Integer> response = bookService.getCountByGenre(genreid);
			return response;
		}

		@GetMapping(value = "countByName")
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseMessage<Integer> GetCountByName(
				@RequestParam(name = "name") String name) {
			ResponseMessage<Integer> response = bookService.getCountByName(name);
			return response;
		}
}
