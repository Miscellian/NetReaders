package com.netreaders.controller;

import com.netreaders.dto.BookDto;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.BookService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
				@RequestParam(name = "name") String name) {
			ResponseMessage<Collection<BookDto>> response = bookService.getByName(name);
			return response;
		}
		
}
