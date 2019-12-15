package com.netreaders.controller;

import com.netreaders.dto.UserBookLibrary;
import com.netreaders.models.Book;
import com.netreaders.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "{id}")
    public Book getBookById(@PathVariable Integer id) {

        return bookService.findBookById(id);
    }

    @GetMapping(value = "range")
    public Collection<Book> getById(
            @RequestParam(name = "amount", defaultValue = "0") Integer amount,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset) {

        return bookService.getBooksById(amount, offset);
    }

    @GetMapping(value = "bygenre")
    public Collection<Book> getByGenre(
            @RequestParam(name = "id") Integer genreId,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {

        return bookService.findBooksByGenre(genreId, amount, offset);
    }

    @GetMapping(value = "byauthor")
    public Collection<Book> getByAuthor(
            @RequestParam(name = "id") Integer authorId,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {

        return bookService.findBooksByAuthor(authorId, amount, offset);
    }

    @GetMapping(value = "byname")
    public Collection<Book> getByName(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {

        return bookService.getBooksByName(name, amount, offset);
    }

    @GetMapping(value = "byusername")
    public Collection<Book> getByUsername(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {

        return bookService.getBooksUsername(username, amount, offset);
    }
    
    @GetMapping(value = "preferences")
    public Collection<Book> getByUserPreferences(
            @RequestParam(name = "username") String username) {
        return bookService.getByUserPreferences(username);
    }

    @GetMapping(value = "byusernameFavourites")
    public Collection<Book> getFavouritesByUsername(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {
        return bookService.getFavouritesByUsername(username, amount, offset);
    }

    @GetMapping(value = "byusernameToReadList")
    public Collection<Book> getToReadListByUsername(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "offset") Integer offset) {
        return bookService.getToReadListByUsername(username, amount, offset);
    }

    @GetMapping(value = "count")
    public Integer getCount() {

        return bookService.getCount();
    }

    @GetMapping(value = "countByAuthor")
    public Integer getCountByAuthor(
            @RequestParam(name = "id") Integer authorId) {

        return bookService.getCountByAuthor(authorId);
    }

    @GetMapping(value = "countByGenre")
    public Integer getCountByGenre(
            @RequestParam(name = "id") Integer genreId) {

        return bookService.getCountByGenre(genreId);
    }

    @GetMapping(value = "countByName")
    public Integer getCountByName(
            @RequestParam(name = "name") String name) {

        return bookService.getCountByName(name);
    }

    @GetMapping(value = "countByUsername")
    public Integer getCountByUsername(
            @RequestParam(name = "username") String username) {

        return bookService.getCountByUsername(username);
    }

    @GetMapping(value = "countFavouritesByUsername")
    public Integer getFavouritesCountByUsername(
            @RequestParam(name = "username") String username) {

        return bookService.getFavouritesCountByUsername(username);
    }

    // not done
    @GetMapping(value = "countToReadListByUsername")
    public Integer getToReadListCountByUsername(
            @RequestParam(name = "username") String username) {

        return bookService.getToReadListCountByUsername(username);
    }

    @PostMapping(value = "addToLibrary")
    public void addBookToUserLibrary(
            @RequestBody UserBookLibrary userBookLibrary) {
        bookService.addBookToUserLibrary(userBookLibrary);
    }

    @PostMapping(value = "removeFromLibrary")
    public void removeBookFromUserLibrary(
            @RequestBody UserBookLibrary userBookLibrary) {
        bookService.removeBookFromUserLibrary(userBookLibrary);
    }

    @PostMapping(value = "checkInLibrary")
    public boolean checkIfBookInLibrary(
            @RequestBody UserBookLibrary userBookLibrary) {
        return bookService.checkIfBookInLibrary(userBookLibrary);
    }

    @PostMapping(value = "addToFavourites")
    public void addBookToFavorites(
            @RequestBody UserBookLibrary userBookLibrary) {
        bookService.addBookToUserFavourites(userBookLibrary);
    }

    @PostMapping(value = "removeFromFavourites")
    public void removeBookFromFavorites(
            @RequestBody UserBookLibrary userBookLibrary) {
        bookService.removeBookFromUserFavourites(userBookLibrary);
    }

    @PostMapping(value = "checkInFavourites")
    public boolean checkIfBookInFavorites(
            @RequestBody UserBookLibrary userBookLibrary) {
        return bookService.checkIfBookInFavourites(userBookLibrary);
    }

    //not done
    @PostMapping(value = "addToToReadList")
    public void addBookToToReadList(
            @RequestBody UserBookLibrary userBookLibrary) {
        bookService.addBookToUserToReadList(userBookLibrary);
    }

    //not done
    @PostMapping(value = "removeFromToReadList")
    public void removeBookToReadList(
            @RequestBody UserBookLibrary userBookLibrary) {
        bookService.removeBookFromUserToReadList(userBookLibrary);
    }

    //not done
    @PostMapping(value = "checkInToReadList")
    public boolean checkIfBookInToReadList(
            @RequestBody UserBookLibrary userBookLibrary) {
        return bookService.checkIfBookInUserToReadList(userBookLibrary);
    }
    
    @PostMapping(value = "add")
    public void addBook(@RequestBody Book book) {
    	bookService.addBook(book);
    }
    
    @PostMapping(value = "publish")
    public void publishBook(@RequestBody Integer bookId) {
    	bookService.publishBook(bookId);
    }
    
    @GetMapping(value = "unpublished")
    public Collection<Book> getUnpublished(
            @RequestParam(name = "amount", defaultValue = "0") Integer amount,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset) {

        return bookService.getUnpublished(amount, offset);
    }
    
    @GetMapping(value = "unpublishedCount")
    public Integer getUnpublishedCount() {
        return bookService.getUnpublishedCount();
    }
    
    
    
}
