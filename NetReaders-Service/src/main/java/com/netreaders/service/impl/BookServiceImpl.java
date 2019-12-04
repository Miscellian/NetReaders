package com.netreaders.service.impl;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.dao.book.BookDao;
import com.netreaders.dao.genres.GenreDao;
import com.netreaders.dto.UserBookLibrary;
import com.netreaders.models.Book;
import com.netreaders.models.Genre;
import com.netreaders.service.BookService;
import lombok.AllArgsConstructor;

import org.assertj.core.internal.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    @Override
    public Book findBookById(Integer id) {

        Book book = bookDao.getById(id);
        return modelToDto(book);
    }

    @Override
    public Collection<Book> getBooksById(Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findAll(amount, offset);
        return createDtoCollection(books);
    }

    @Override
    public Collection<Book> getBooksByName(String name, Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findByName(name.toLowerCase(), amount, offset);
        return createDtoCollection(books);
    }

    @Override
    public Collection<Book> getBooksUsername(String username, Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findByUsername(username.toLowerCase(), amount, offset);
        return createDtoCollection(books);
    }

    @Override
    public Collection<Book> findBooksByGenre(Integer genreId, Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findByGenre(genreId, amount, offset);
        return createDtoCollection(books);
    }

    @Override
    public Collection<Book> findBooksByAuthor(Integer authorId, Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findByAuthor(authorId, amount, offset);
        return createDtoCollection(books);
    }

    @Override
    public Integer getCount() {

        return bookDao.getCount();
    }

    @Override
    public Integer getCountByAuthor(Integer id) {

        return bookDao.getCountByAuthor(id);
    }

    @Override
    public Integer getCountByGenre(Integer id) {

        return bookDao.getCountByGenre(id);
    }

    @Override
    public Integer getCountByName(String name) {

        return bookDao.getCountByName(name);
    }

    @Override
    public Integer getCountByUsername(String username) {

        return bookDao.getCountByUsername(username);
    }

    @Override
    public void addBookToUserLibrary(UserBookLibrary userBookLibrary) {
        String username = userBookLibrary.getUsername();
        Integer bookId = userBookLibrary.getBookId();
        if (!bookDao.checkIfBookInUserLibrary(username, bookId)) {
            bookDao.addBookToUserLibrary(username, bookId);
        }
    }

    @Override
    public boolean checkIfBookInLibrary(UserBookLibrary userBookLibrary) {
        String username = userBookLibrary.getUsername();
        Integer bookId = userBookLibrary.getBookId();
        return bookDao.checkIfBookInUserLibrary(username, bookId);
    }

    @Override
    public void removeBookFromUserLibrary(UserBookLibrary userBookLibrary) {
        String username = userBookLibrary.getUsername();
        Integer bookId = userBookLibrary.getBookId();
        if (bookDao.checkIfBookInUserLibrary(username, bookId)) {
            bookDao.removeBookFromUserLibrary(username, bookId);
        }
    }

    @Override
    public Collection<Book> findByAnnouncementId(Integer announcementId) {

        return bookDao.findByAnnouncementId(announcementId);
    }

    @Override
    public Collection<Book> findByAnnouncementWithGenre(Integer announcementId, Integer genreId) {

        return bookDao.findByAnnouncementWithGenre(announcementId, genreId);
    }

    @Override
    public Collection<Book> findByAnnouncementWithAuthor(Integer announcementId, Integer authorId) {

        return bookDao.findByAnnouncementWithAuthor(announcementId, authorId);
    }

    private Collection<Book> createDtoCollection(Collection<Book> books) {

        return books.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    private Book modelToDto(Book book) {

        book.setGenres(genreDao.getByBookId(book.getId()));
        book.setAuthors(authorDao.getByBookId(book.getId()));

        return book;
    }

	@Override
	public Book findBookByReviewId(Integer id) {
		Book book = bookDao.getByReviewId(id);
        return modelToDto(book);
	}


	

    
	public Collection<Book> getByUserPreferences(String username){
		Collection<Book> preparedBooks = bookDao.findByUsername(username, getCountByUsername(username), 0)
				.stream()
				.map(this::modelToDto)
				.collect(Collectors.toList());
		Map<Genre, Long> userGenres = preparedBooks
				.stream()
				.flatMap(book -> book.getGenres().stream())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
		List<Book> pickedBooks = userGenres.entrySet().stream()
				.sorted((entry1, entry2) -> {return (int) (entry1.getValue() - entry2.getValue());})
				.limit(3)
				.flatMap(entry -> bookDao.findByGenre(entry.getKey().getId(), 5, 0).stream())
				.collect(Collectors.toList());
		
		return bookDao.findBooksMinusSelected(pickedBooks,6,0)
				.stream()
				.map(this::modelToDto)
				.collect(Collectors.toList());
	}
}
