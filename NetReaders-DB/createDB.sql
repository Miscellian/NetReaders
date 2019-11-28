/*
*	BE VERY CAREFUL, WHEN USING THIS
*
*	Just for quick drop, when you test your local DB only
*/
DROP TABLE IF EXISTS user_message_chat CASCADE;
DROP TABLE IF EXISTS chats CASCADE;
DROP TABLE IF EXISTS user_achievment CASCADE;
DROP TABLE IF EXISTS user_activity CASCADE;
DROP TABLE IF EXISTS activities CASCADE;
DROP TABLE IF EXISTS friendships CASCADE;
DROP TABLE IF EXISTS achievments CASCADE;
DROP TABLE IF EXISTS user_book CASCADE;
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS book_announcement CASCADE;
DROP TABLE IF EXISTS announcements CASCADE;
DROP TABLE IF EXISTS book_file CASCADE;
DROP TABLE IF EXISTS files CASCADE;
DROP TABLE IF EXISTS book_genre CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS book_author CASCADE;
DROP TABLE IF EXISTS authors CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS registration_tokens CASCADE;
DROP TABLE IF EXISTS user_role CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS photos CASCADE;

CREATE TABLE photos
(
    photo_id SERIAL PRIMARY KEY,
    link     TEXT
);

CREATE TABLE users
(
    user_id       SERIAL PRIMARY KEY,
    user_name     VARCHAR(16) UNIQUE NOT NULL,
    user_password CHAR(64)           NOT NULL,
    email         VARCHAR(32) UNIQUE NOT NULL,
    first_name    VARCHAR(32),
    last_name     VARCHAR(32),
    profile_photo INTEGER,
    CONSTRAINT user_photo_fk FOREIGN KEY (profile_photo) REFERENCES photos (photo_id)
);

CREATE TABLE roles
(
    role_id   SERIAL PRIMARY KEY,
    role_name VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE user_role
(
    user_role_id SERIAL PRIMARY KEY,
    user_id      INTEGER NOT NULL,
    role_id      INTEGER NOT NULL,
    CONSTRAINT ur_user_fk FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT ur_role_fk FOREIGN KEY (role_id) REFERENCES roles (role_id)
);

CREATE TABLE registration_tokens (
	registration_token_id SERIAL PRIMARY KEY,
    user_id              INTEGER NOT NULL,
	token_value VARCHAR(128),
    created_time         TIMESTAMP,
	CONSTRAINT rt_user_fk FOREIGN KEY(user_id) REFERENCES users(user_id)
);

CREATE TABLE books
(
    book_id       SERIAL PRIMARY KEY,
    title         VARCHAR(64) NOT NULL,
    photo         INTEGER,
    description   TEXT,
    release_date  DATE,
    book_language CHAR(2),
    CONSTRAINT book_photo_fk FOREIGN KEY (photo) REFERENCES photos (photo_id)
);

CREATE TABLE authors
(
    author_id   SERIAL PRIMARY KEY,
    author_name VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE book_author
(
    book_author_id SERIAL PRIMARY KEY,
    book_id        INTEGER NOT NULL,
    author_id      INTEGER NOT NULL,
    CONSTRAINT ba_book_fk FOREIGN KEY (book_id) REFERENCES books (book_id),
    CONSTRAINT ba_author_fk FOREIGN KEY (author_id) REFERENCES authors (author_id)
);

CREATE TABLE genres
(
    genre_id   SERIAL PRIMARY KEY,
    genre_name VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE book_genre
(
    book_genre_id SERIAL PRIMARY KEY,
    book_id       INTEGER NOT NULL,
    genre_id      INTEGER NOT NULL,
    CONSTRAINT ba_book_fk FOREIGN KEY (book_id) REFERENCES books (book_id),
    CONSTRAINT ba_genre_fk FOREIGN KEY (genre_id) REFERENCES genres (genre_id)
);

CREATE TABLE files
(
    file_id       SERIAL PRIMARY KEY,
    download_link TEXT NOT NULL,
    file_size     FLOAT
);

CREATE TABLE book_file
(
    book_file_id SERIAL PRIMARY KEY,
    book_id      INTEGER NOT NULL,
    file_id      INTEGER NOT NULL,
    CONSTRAINT ba_book_fk FOREIGN KEY (book_id) REFERENCES books (book_id),
    CONSTRAINT ba_file_fk FOREIGN KEY (file_id) REFERENCES files (file_id)
);

CREATE TABLE announcements
(
    announcement_id   SERIAL PRIMARY KEY,
    announcement_date DATE NOT NULL,
    description       TEXT,
    published         BOOLEAN DEFAULT FALSE
);

CREATE TABLE book_announcement
(
    book_announcement_id SERIAL PRIMARY KEY,
    book_id              INTEGER NOT NULL,
    announcement_id      INTEGER NOT NULL,
    CONSTRAINT ba_book_fk FOREIGN KEY (book_id) REFERENCES books (book_id),
    CONSTRAINT ba_announcement_fk FOREIGN KEY (announcement_id) REFERENCES announcements (announcement_id)
);

CREATE TABLE reviews
(
    review_id   SERIAL PRIMARY KEY,
    book_id     INTEGER NOT NULL,
    rating      INTEGER,
    description TEXT,
    published   BOOLEAN DEFAULT FALSE,
    CONSTRAINT review_book_fk FOREIGN KEY (book_id) REFERENCES books (book_id)
);


CREATE TABLE user_book
(
    user_book_id      SERIAL PRIMARY KEY,
    user_id           INTEGER NOT NULL,
    book_id           INTEGER NOT NULL,
    read_mark         BOOLEAN DEFAULT FALSE,
    favourite_mark    BOOLEAN DEFAULT FALSE,
    announcement_mark BOOLEAN DEFAULT FALSE,
    to_read_list_mark BOOLEAN DEFAULT FALSE,
    CONSTRAINT ub_user_fk FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT ub_book_fk FOREIGN KEY (book_id) REFERENCES books (book_id)
);

CREATE TABLE achievments
(
    achievment_id SERIAL PRIMARY KEY,
    title         VARCHAR(32) UNIQUE NOT NULL,
    description   TEXT,
    photo         INTEGER,
    CONSTRAINT achievment_photo_fk FOREIGN KEY (photo) REFERENCES photos (photo_id)
);

CREATE TABLE friendships
(
    friendship_id SERIAL PRIMARY KEY,
    user_id_1     INTEGER NOT NULL,
    user_id_2     INTEGER NOT NULL,
    CONSTRAINT friendship_user1_fk FOREIGN KEY (user_id_1) REFERENCES users (user_id),
    CONSTRAINT friendship_user2_fk FOREIGN KEY (user_id_2) REFERENCES users (user_id)
);

CREATE TABLE activities
(
    activity_id SERIAL PRIMARY KEY,
    description TEXT NOT NULL
);

CREATE TABLE user_activity
(
    user_activity_id SERIAL PRIMARY KEY,
    user_id          INTEGER NOT NULL,
    activity_id      INTEGER NOT NULL,
    CONSTRAINT uac_user_fk FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT uac_activity_fk FOREIGN KEY (activity_id) REFERENCES activities (activity_id)
);

CREATE TABLE user_achievment
(
    user_achievment_id SERIAL PRIMARY KEY,
    user_id            INTEGER NOT NULL,
    achievment_id      INTEGER NOT NULL,
    CONSTRAINT ua_user_fk FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT ua_achievment_fk FOREIGN KEY (achievment_id) REFERENCES achievments (achievment_id)
);

CREATE TABLE chats
(
    chat_id       SERIAL PRIMARY KEY,
    title         VARCHAR(32) NOT NULL,
    photo         INTEGER,
    creation_date DATE        NOT NULL,
    CONSTRAINT chat_photo_fk FOREIGN KEY (photo) REFERENCES photos (photo_id)
);

CREATE TABLE user_message_chat
(
    user_message_chat_id SERIAL PRIMARY KEY,
    message_content      TEXT    NOT NULL,
    message_date         DATE    NOT NULL,
    chat_id              INTEGER NOT NULL,
    user_id              INTEGER NOT NULL,
    CONSTRAINT umc_chat_fk FOREIGN KEY (chat_id) REFERENCES chats (chat_id),
    CONSTRAINT umc_user_fk FOREIGN KEY (user_id) REFERENCES users (user_id)
);
