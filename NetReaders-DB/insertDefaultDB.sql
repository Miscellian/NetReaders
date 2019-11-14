INSERT INTO "roles"(role_name) VALUES
	('SUPER_ADMIN'),
	('ADMIN'),
	('GENERAL_MODERATOR'),
	('ANNOUNCEMENT_MODERATOR'),
	('OVERVIEW_MODERATOR'),
	('REVIEW_MODERATOR'),
	('USER');
	
INSERT INTO "photos"("link") VALUES
	('<user_default_photo'),
	('<book_default_photo>'),
	('achievment_default_photo'),
	('chat_default_photo');
	
INSERT INTO "users"(user_name, user_password, email, first_name, last_name) VALUES
	('root','topsecretpassword2019','super.netreaders@mail.com','Ilon','Mask'),
	('ivan','qwerty12345','ivan@gmail.com','Ivan','Ivanov'),
	('kingOfNation', '1111', 'prohovnyk.petro@mail.ru','Petro', NULL),
	('pikasso', '45678', 'alan.turing@gmail.ua', NULL, NULL),
	('hotdog007', 'password1010', 'hotdog007@mail.ua', NULL, NULL),
	('newuser', '102102passowrdf','john.ronald@you.com', 'John', 'Leskov');
	
UPDATE "users" SET profile_photo = 1;

INSERT INTO "user_role"(user_id, role_id) VALUES
	(1, 1),
	(2, 2),
	(3, 3),
	(4, 4),
	(5, 5),
	(5, 6),
	(6, 7);
	
INSERT INTO "books"(title, photo, description, release_date, book_language) VALUES
	(	'Harry Potter and the Philosopher`s stone', 
	 	NULL,
	 	'Harry Potter and the Philosopher`s Stone is the first novel in the Harry Potter series. The book was first published on 26 June, 1997[1] by Bloomsbury in London, and was later made into a film of the same name.',
	 	TO_DATE('26/06/1997', 'DD/MM/YYYY'),
	 	'en'
	),
	(	'Don Quixote', 
	 	NULL,
	 	'Published in two parts, in 1605 and 1615, Don Quixote is the most influential work of literature from the Spanish Golden Age and the entire Spanish literary canon.',
	 	TO_DATE('14/04/1610', 'DD/MM/YYYY'),
	 	'es'
	),
	(
		'Robinson Crusoe', 
	 	NULL,
	 	'The first edition credited the work`s protagonist Robinson Crusoe as its author, leading many readers to believe he was a real person and the book a travelogue of true incidents.',
	 	TO_DATE('25/04/1719', 'DD/MM/YYYY'),
	 	'en'
	),
	(
		'War and Peace', 
	 	NULL,
	 	'The novel chronicles the French invasion of Russia and the impact of the Napoleonic era on Tsarist society through the stories of five Russian aristocratic families.',
	 	TO_DATE('20/07/1869', 'DD/MM/YYYY'),
	 	'ru'
	),
	(
		'Kobzar', 
	 	NULL,
	 	'From that time on this title has been applied to Shevchenko`s poetry in general and acquired a symbolic meaning of the Ukrainian national and literary revival.',
	 	TO_DATE('2/09/1884', 'DD/MM/YYYY'),
	 	'ua'
	),
	(
		'Odiseea', 
	 	NULL,
	 	'The Odyssey is fundamental to the modern Western canon; it is the second-oldest extant work of Western literature, while the Iliad is the oldest.',
	 	TO_DATE('3/06/10', 'DD/MM/YYYY'),
	 	'gr'
	),
	(	'Testament', 
	 	NULL,
	 	'Shevchenko`s "Testament", (Zapovit, 1845), has been translated into more than 150 languages and set to music in the 1870s by H. Hladky.',
	 	TO_DATE('25/12/1845', 'DD/MM/YYYY'),
	 	'ua'
	);

INSERT INTO "genres"(genre_name) VALUES
	('Heroes and heroism'),
	('Poetry'),
	('Novel'),
	('Historical'),
	('Adventure'),
	('Fiction'),
	('Fantasy');
	
INSERT INTO "book_genre"(book_id, genre_id) VALUES
	(1, 7),
	(2, 3),
	(3, 6),
	(3, 5),
	(4, 3),
	(4, 4),
	(5, 2),
	(6, 1),
	(7, 2);
	
INSERT INTO "authors"(author_name) VALUES
	('Miguel de Cervantes'),
	('J. K. Rowling'),
	('Leo Tolstoy'),
	('Taras Shevchenko'),
	('Homer'),
	('Daniel Defo');
	
INSERT INTO "book_author"(book_id, author_id) VALUES
	(1, 2),
	(2, 1),
	(3, 6),
	(4, 3),
	(5, 4),
	(6, 5),
	(7, 4);

INSERT INTO books(title, book_language)
VALUES ('The Dark Tower I', 'en');

INSERT INTO books(title, book_language)
VALUES ('The Dark Tower II', 'en');

INSERT INTO books(title, book_language)
VALUES ('Misery', 'en');

INSERT INTO books(title, book_language)
VALUES ('The Da Vinci Code', 'ru');

INSERT INTO books(title, book_language)
VALUES ('Angels & Demons', 'ru');

INSERT INTO authors(author_name)
VALUES ('Steven King');

INSERT INTO authors(author_name)
VALUES ('Den Braun');

INSERT INTO book_author(book_id, author_id)
VALUES ((SELECT book_id
	   FROM books
	   WHERE title='The Dark Tower I'),
	   (SELECT author_id
	   FROM authors
	   WHERE author_name='Steven King'));
	   
INSERT INTO book_author(book_id, author_id)
VALUES ((SELECT book_id
	   FROM books
	   WHERE title='The Dark Tower II'),
	   (SELECT author_id
	   FROM authors
	   WHERE author_name='Steven King'));
	   
INSERT INTO book_author(book_id, author_id)
VALUES ((SELECT book_id
	   FROM books
	   WHERE title='Misery'),
	   (SELECT author_id
	   FROM authors
	   WHERE author_name='Steven King'));
	   
INSERT INTO book_author(book_id, author_id)
VALUES ((SELECT book_id
	   FROM books
	   WHERE title='The Da Vinci Code'),
	   (SELECT author_id
	   FROM authors
	   WHERE author_name='Den Braun'));

INSERT INTO book_author(book_id, author_id)
VALUES ((SELECT book_id
	   FROM books
	   WHERE title='Angels & Demons'),
	   (SELECT author_id
	   FROM authors
	   WHERE author_name='Den Braun'));

DELETE FROM genres
WHERE genre_name = 'thriller';

INSERT INTO genres(genre_name)
VALUES ('Mystery');

INSERT INTO genres(genre_name)
VALUES ('Western');

INSERT INTO genres(genre_name)
VALUES ('Horror');

INSERT INTO genres(genre_name)
VALUES ('Science fiction');

INSERT INTO genres(genre_name)
VALUES ('Detective fiction');

INSERT INTO genres(genre_name)
VALUES ('Psychological horror');

INSERT INTO genres(genre_name)
VALUES ('Thriller');

INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='The Dark Tower I'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Fantasy'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='The Dark Tower I'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Western'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='The Dark Tower II'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Fantasy'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='The Dark Tower II'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Horror'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='The Dark Tower II'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Science fiction'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='The Dark Tower II'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Western'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='Misery'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Psychological horror'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='Misery'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Thriller'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='Angels & Demons'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Mystery'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='Angels & Demons'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Thriller'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='The Da Vinci Code'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Mystery'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='The Da Vinci Code'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Thriller'));
	   
INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
		FROM books
		WHERE title='The Da Vinci Code'),
	   (SELECT genre_id
	   FROM genres
	   WHERE genre_name='Detective fiction'));

UPDATE "books" SET photo = 2;
