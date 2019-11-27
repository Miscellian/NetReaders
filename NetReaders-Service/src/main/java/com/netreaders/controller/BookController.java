package com.netreaders.controller;

import com.netreaders.dto.BookDto;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.BookService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/books")
public class BookController {

		private final BookService bookService;

		public BookController(BookService bookService) {
			this.bookService = bookService;
		}
		
		@GetMapping(value = "{id}")
		public ResponseMessage<BookDto> GetBookById(@PathVariable String id) {
			ResponseMessage<BookDto> response = bookService.findBookById(id);
			return response;
		}
		@GetMapping(value = "range")
		public ResponseMessage<Collection<BookDto>> GetById(
				@RequestParam(name = "amount",defaultValue = "0") String amount,
				@RequestParam(name = "offset",defaultValue = "0") String offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.getById(amount, offset);
			return response;
		}
		
		@GetMapping(value = "bygenre")
		public ResponseMessage<Collection<BookDto>> GetByGenre(
				@RequestParam(name = "id") String genreid,
				@RequestParam(name = "amount") String amount,
				@RequestParam(name = "offset") String offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.findBooksByGenre(genreid, amount, offset);
			return response;
		}
		
		@GetMapping(value = "byauthor")
		public ResponseMessage<Collection<BookDto>> GetByAuthor(
				@RequestParam(name = "id") String authorid,
				@RequestParam(name = "amount") String amount,
				@RequestParam(name = "offset") String offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.findBooksByAuthor(authorid, amount, offset);
			return response;
		}
		
		@GetMapping(value = "byname")
		public ResponseMessage<Collection<BookDto>> GetByName(
				@RequestParam(name = "name") String name,
				@RequestParam(name = "amount") String amount,
				@RequestParam(name = "offset") String offset) {
			ResponseMessage<Collection<BookDto>> response = bookService.getByName(name, amount, offset);
			return response;
		}
		
		@GetMapping(value = "count")
		public ResponseMessage<Integer> GetCount() {
			ResponseMessage<Integer> response = bookService.getCount();
			return response;
		}
		
		@GetMapping(value = "countByAuthor")
		public ResponseMessage<Integer> GetCountByAuthor(
				@RequestParam(name = "id") String authorid) {
			ResponseMessage<Integer> response = bookService.getCountByAuthor(authorid);
			return response;
		}
		
		@GetMapping(value = "countByGenre")
		public ResponseMessage<Integer> GetCountByGenre(
				@RequestParam(name = "id") String genreid) {
			ResponseMessage<Integer> response = bookService.getCountByGenre(genreid);
			return response;
		}
		
		@GetMapping(value = "countByName")
		public ResponseMessage<Integer> GetCountByName(
				@RequestParam(name = "name") String name) {
			ResponseMessage<Integer> response = bookService.getCountByName(name);
			return response;
		}
}
