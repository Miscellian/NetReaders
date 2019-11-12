package com.netreaders.books.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.netreaders.books.dao.interfaces.*;
import com.netreaders.books.dto.BookDto;
import com.netreaders.genres.dao.interfaces.GenreDataStore;
import com.netreaders.authors.dao.interfaces.AuthorDataStore;
import com.netreaders.books.dao.*;
import com.netreaders.models.*;

@Service
public class BookService {

	private final BookDataStore bookRepository;
	private final GenreDataStore genreRepository;
	private final AuthorDataStore authorRepository;

	public BookService(BookDataStore bookDataStore,
					   GenreDataStore genreDataStore,
					   AuthorDataStore authorDataStore) {
		this.bookRepository = bookDataStore;
		this.genreRepository = genreDataStore;
		this.authorRepository = authorDataStore;
	}

	
	public ResponseMessage<Collection<BookDto>> findBooksByGenre(String genre_id, String amount, String offset){
		ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
		try{
			int numericGenreId = Integer.parseInt(genre_id);
			int numericAmount = Integer.parseInt(amount);
			int numericOffset = Integer.parseInt(offset);
			Collection<Book> books = bookRepository.findBooksByGenre(numericGenreId, numericAmount, numericOffset);
			Genre genre = genreRepository.getById(numericGenreId);
			Collection<BookDto> bookDtos = books.stream()
					.map(book -> new BookDto(
									genre,
									authorRepository.getByBookId(book.getId()),
									book)
					).collect(Collectors.toCollection(ArrayList::new));
			message.setObj(bookDtos);
		} catch(NumberFormatException e) {
			message.setSuccessful(false);
			message.setErrorMessage(e.getMessage());
		} 
		return message;
	}
	
	public ResponseMessage<Collection<BookDto>> findBooksByAuthor(String author_id, String amount, String offset){
		ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
		try{
			int numericAuthorId = Integer.parseInt(author_id);
			int numericAmount = Integer.parseInt(amount);
			int numericOffset = Integer.parseInt(offset);
			Collection<Book> books = bookRepository.findBooksByAuthor(numericAuthorId, numericAmount, numericOffset);
			Author author = authorRepository.getById(numericAuthorId);
			Collection<BookDto> bookDtos = books.stream()
					.map(book -> new BookDto(
									genreRepository.getByBookId(book.getId()),
									author,
									book)
					).collect(Collectors.toCollection(ArrayList::new));
			message.setObj(bookDtos);
		} catch(NumberFormatException e) {
			message.setSuccessful(false);
			message.setErrorMessage(e.getMessage());
		}
		return message;
	}
	
	public ResponseMessage<Collection<BookDto>> getById(String amount, String offset){
		ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
		try{
			int numericAmount = Integer.parseInt(amount);
			int numericOffset = Integer.parseInt(offset);
			Collection<Book> books = bookRepository.getById(numericAmount, numericOffset);
			Collection<BookDto> bookDtos = books.stream()
					.map(book -> new BookDto(
										genreRepository.getByBookId(book.getId()),
										authorRepository.getByBookId(book.getId()),
										book)
					).collect(Collectors.toCollection(ArrayList::new));
			message.setObj(bookDtos);
		} catch(NumberFormatException e) {
			message.setSuccessful(false);
			message.setErrorMessage(e.getMessage());
		}
		return message;
	}
	
	public ResponseMessage<BookDto> findBookById(String id){
		ResponseMessage<BookDto> message = new ResponseMessage<>();
		try{
			int numericId = Integer.parseInt(id);
			Book book = bookRepository.findBookById(numericId);
			Genre genre = genreRepository.getByBookId(book.getId());
			Author author = authorRepository.getByBookId(book.getId());
			message.setObj(new BookDto(genre, author, book));
		} catch(NumberFormatException e) {
			message.setSuccessful(false);
			message.setErrorMessage(e.getMessage());
		}
		return message;
	}
	
}
