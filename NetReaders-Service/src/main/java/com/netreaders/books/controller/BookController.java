package com.netreaders.books.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netreaders.books.dto.BookDto;
import com.netreaders.books.service.BookService;
import com.netreaders.models.ResponseMessage;

@RestController
public class BookController {

		private final BookService bookService;

		public BookController(BookService bookService) {
			this.bookService = bookService;
		}
		
		@GetMapping(value = "api/books/{id}")
		public BookDto GetGenreById(@PathVariable String id) {
			ResponseMessage<BookDto> response = bookService.findBookById(id);
			if(response.isSuccessful()) {
				return response.getObj();
			} else {
				return null;
			}
		}
		@GetMapping(value = "api/books/range")
		public Collection<BookDto> GetById(
				@RequestParam(name = "amount",defaultValue = "0") String amount,
				@RequestParam(name = "offset",defaultValue = "0") String offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.getById(amount, offset);
			if(response.isSuccessful()) {
				return response.getObj();
			} else {
				return null;
			}
		}
		
		@GetMapping(value = "api/books/bygenre")
		public Collection<BookDto> GetByGenre(
				@RequestParam(name = "genreid",defaultValue = "0") String genreid,
				@RequestParam(name = "amount",defaultValue = "0") String amount,
				@RequestParam(name = "offset",defaultValue = "0") String offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.findBooksByGenre(genreid, amount, offset);
			if(response.isSuccessful()) {
				return response.getObj();
			} else {
				return null;
			}
		}
		
		@GetMapping(value = "api/books/byauthor")
		public Collection<BookDto> GetByAuthor(
				@RequestParam(name = "authorid",defaultValue = "0") String authorid,
				@RequestParam(name = "amount",defaultValue = "0") String amount,
				@RequestParam(name = "offset",defaultValue = "0") String offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.findBooksByAuthor(authorid, amount, offset);
			if(response.isSuccessful()) {
				return response.getObj();
			} else {
				return null;
			}
		}
		
}
