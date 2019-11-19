package com.netreaders.controller;

import com.netreaders.dto.BookDto;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/books")
public class BookController {

		@Autowired
		private BookService bookService;

		@GetMapping(value = "{id}")
		@CrossOrigin(origins = "http://localhost:8080")
		public ResponseMessage<BookDto> getBookById(@PathVariable Integer id) {
			ResponseMessage<BookDto> response = bookService.findBookById(id);
			return response;
		}
		@GetMapping(value = "range")
		@CrossOrigin(origins = "http://localhost:8080")
		public ResponseMessage<Collection<BookDto>> getById(
				@RequestParam(name = "amount",defaultValue = "0") Integer amount,
				@RequestParam(name = "offset",defaultValue = "0") Integer offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.getById(amount, offset);
			return response;
		}
		
		@GetMapping(value = "bygenre")
		@CrossOrigin(origins = "http://localhost:8080")
		public ResponseMessage<Collection<BookDto>> getByGenre(
				@RequestParam(name = "id") Integer genreid,
				@RequestParam(name = "amount") Integer amount,
				@RequestParam(name = "offset") Integer offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.findBooksByGenre(genreid, amount, offset);
			return response;
		}
		
		@GetMapping(value = "byauthor")
		@CrossOrigin(origins = "http://localhost:8080")
		public ResponseMessage<Collection<BookDto>> GetByAuthor(
				@RequestParam(name = "id") Integer authorid,
				@RequestParam(name = "amount") Integer amount,
				@RequestParam(name = "offset") Integer offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.findBooksByAuthor(authorid, amount, offset);
			return response;
		}
		
		@GetMapping(value = "byname")
		@CrossOrigin(origins = "http://localhost:8080")
		public ResponseMessage<Collection<BookDto>> GetByName(
				@RequestParam(name = "name") String name) {
			ResponseMessage<Collection<BookDto>> response = bookService.getByName(name);
			return response;
		}
		
}
