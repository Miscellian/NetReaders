package com.netreaders.books.dao.interfaces;

import java.sql.SQLException;
import java.util.Collection;

import com.netreaders.models.*;

public interface BookDataStore {
	Book findBookById(int id) throws SQLException;
	Collection<Book> findBooksByGenre(int genre_id, int amount, int offset) throws SQLException;
	Collection<Book> findBooksByAuthor(int author_id, int amount, int offset) throws SQLException;
	Collection<Book> getById(int amount, int offset) throws SQLException;
}