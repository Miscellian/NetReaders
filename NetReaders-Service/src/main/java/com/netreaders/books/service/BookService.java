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
import com.netreaders.models.*;
import com.netreaders.services.ResponseMessagePrepearer;

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

	
	private BookDto tryCreateBookDto(Book book) throws RuntimeException{
		try {
		return new BookDto(
					genreRepository.getByBookId(book.getId()),
					authorRepository.getByBookId(book.getId()),
					book);
		}catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public ResponseMessage<Collection<BookDto>> findBooksByGenre(String genre_id, String amount, String offset){
		ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
		try{
			int numericGenreId = Integer.parseInt(genre_id);
			int numericAmount = Integer.parseInt(amount);
			int numericOffset = Integer.parseInt(offset);
			Collection<Book> books = bookRepository.findBooksByGenre(numericGenreId, numericAmount, numericOffset);
			Collection<BookDto> bookDtos = books.stream()
					.map(this::tryCreateBookDto)
					.collect(Collectors.toCollection(ArrayList::new));
			message.setObj(bookDtos);
		}  catch(NumberFormatException e) {
			ResponseMessagePrepearer.prepareMessage(message, "Invalid numeric parameters");
		} catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		} catch(RuntimeException e) {
			ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
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
			Collection<BookDto> bookDtos = books.stream()
					.map(this::tryCreateBookDto)
					.collect(Collectors.toCollection(ArrayList::new));
			message.setObj(bookDtos);
		} catch(NumberFormatException e) {
			ResponseMessagePrepearer.prepareMessage(message, "Invalid numeric parameters");
		} catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		} catch(RuntimeException e) {
			ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
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
					.map(this::tryCreateBookDto)
					.collect(Collectors.toCollection(ArrayList::new));
			message.setObj(bookDtos);
		} catch(NumberFormatException e) {
			ResponseMessagePrepearer.prepareMessage(message, "Invalid numeric parameters");
		} catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		} catch(RuntimeException e) {
			ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
		}
		return message;
	}
	
	public ResponseMessage<BookDto> findBookById(String id){
		ResponseMessage<BookDto> message = new ResponseMessage<>();
		try{
			int numericId = Integer.parseInt(id);
			Book book = bookRepository.findBookById(numericId);
			message.setObj(tryCreateBookDto(book));
		} catch(NumberFormatException e) {
			ResponseMessagePrepearer.prepareMessage(message, "Invalid numeric parameters");
		} catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		} catch(RuntimeException e) {
			ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
		}
		return message;
	}
	
}
