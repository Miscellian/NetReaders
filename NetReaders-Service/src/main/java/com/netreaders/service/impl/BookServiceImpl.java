package com.netreaders.service.impl;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.dao.book.BookDao;
import com.netreaders.dao.genres.GenreDao;
import com.netreaders.dto.UserBookLibrary;
import com.netreaders.models.Author;
import com.netreaders.models.Book;
import com.netreaders.models.Genre;
import com.netreaders.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    public Integer getFavouritesCountByUsername(String username) {
        return bookDao.getFavouritesCountByUsername(username);
    }

    @Override
    public Integer getToReadListCountByUsername(String username) {
        return bookDao.getToReadListCountByUsername(username);
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
    public void addBookToUserFavourites(UserBookLibrary userBookLibrary) {
        String username = userBookLibrary.getUsername();
        Integer bookId = userBookLibrary.getBookId();
        if (bookDao.checkIfBookInUserLibrary(username, bookId)
                && !bookDao.checkIfBookInFavourites(username, bookId)) {
            bookDao.addBookToUserFavourites(username, bookId);
        }
    }

    @Override
    public void removeBookFromUserFavourites(UserBookLibrary userBookLibrary) {
        String username = userBookLibrary.getUsername();
        Integer bookId = userBookLibrary.getBookId();
        if (bookDao.checkIfBookInUserLibrary(username, bookId)
                && bookDao.checkIfBookInFavourites(username, bookId)) {
            bookDao.removeBookToUserFavourites(username, bookId);
        }
    }

    @Override
    public boolean checkIfBookInFavourites(UserBookLibrary userBookLibrary) {
        String username = userBookLibrary.getUsername();
        Integer bookId = userBookLibrary.getBookId();
        return bookDao.checkIfBookInFavourites(username, bookId);
    }

    @Override
    public void addBookToUserToReadList(UserBookLibrary userBookLibrary) {
        String username = userBookLibrary.getUsername();
        Integer bookId = userBookLibrary.getBookId();
        if (bookDao.checkIfBookInUserLibrary(username, bookId)
                && !bookDao.checkIfBookInUserToReadList(username, bookId)) {
            bookDao.addBookToUserToReadList(username, bookId);
        }
    }

    @Override
    public void removeBookFromUserToReadList(UserBookLibrary userBookLibrary) {
        String username = userBookLibrary.getUsername();
        Integer bookId = userBookLibrary.getBookId();
        if (bookDao.checkIfBookInUserLibrary(username, bookId)
                && bookDao.checkIfBookInUserToReadList(username, bookId)) {
            bookDao.removeBookFromUserToReadList(username, bookId);
        }
    }

    @Override
    public boolean checkIfBookInUserToReadList(UserBookLibrary userBookLibrary) {
        String username = userBookLibrary.getUsername();
        Integer bookId = userBookLibrary.getBookId();
        return bookDao.checkIfBookInUserToReadList(username, bookId);
    }

    @Override
    public Collection<Book> getFavouritesByUsername(String username, Integer amount, Integer offset) {
        Collection<Book> books = bookDao.findByFavouritesUsername(username.toLowerCase(), amount, offset);
        return createDtoCollection(books);
    }

    @Override
    public Collection<Book> getToReadListByUsername(String username, Integer amount, Integer offset) {
        Collection<Book> books = bookDao.findToReadListByUsername(username.toLowerCase(), amount, offset);
        return createDtoCollection(books);
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
                .sorted((entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue()))
				.limit(3)
				.flatMap(entry -> bookDao.findByGenre(entry.getKey().getId(), 2, 0).stream())
				.collect(Collectors.toList());
		
		return pickedBooks;
	}

	@Override
	public void publishBook(Integer bookId) {
		Book book = bookDao.getById(bookId);
		book.setPublished(true);
		if(book.getPhoto()==0) {
			book.setPhoto(null);
		}
		bookDao.update(book);
	}

	@Override
	@Transactional
	public void addBook(Book book) {
        if (bookDao.checkBookExistsByTitle(book.getTitle()))
            return;
        book.setPublished(false);
        Integer newId = bookDao.create(book).getId();
        book.getGenres()
                .forEach(genre -> handleGenres(genre, newId));
        book.getAuthors()
                .forEach(author -> handleAuthors(author, newId));
    }
	
	private void handleGenres(Genre genre, Integer bookId) {
		genre = genreDao.existsByName(genre.getName()) ? genreDao.getByName(genre.getName()) : genreDao.create(genre);
		bookDao.addGenre(bookId, genre.getId());
	}
	private void handleAuthors(Author author, Integer bookId) {
		author = authorDao.existsByName(author.getName()) ? authorDao.getByName(author.getName()) : authorDao.create(author);
		bookDao.addAuthor(bookId, author.getId());
	}

	@Override
	public Collection<Book> getUnpublished(Integer amount, Integer offset) {
        Collection<Book> books = bookDao.getUnpublished(amount, offset);
        return createDtoCollection(books);
    }

	@Override
	public Integer getUnpublishedCount() {
		return bookDao.getUnpublishedCount();
	}

	@Override
	public boolean checkBookExists(String title) {
		return bookDao.checkBookExistsByTitle(title);
	}
}
