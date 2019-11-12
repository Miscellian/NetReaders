package com.netreaders.books.dao.interfaces;

import java.util.Collection;
import com.netreaders.models.*;

public interface BookDataStore {
	Book findBookById(int id);
	Collection<Book> findBooksByGenre(int genre_id, int amount, int offset);
	Collection<Book> findBooksByAuthor(int author_id, int amount, int offset);
	Collection<Book> getById(int amount, int offset);
}