Achievments - title and description for user achievements, like "read 10 books".
Activities - contains information about user activity, such as adding a book to the library, adding a user to friends, and so on.
Announcements - date, description. Must be approved by moderator before publishing.
Authors - names of authors.
Book_Announcement - books belonging to the announcement.
Book_Author - authors belonging to the book.
Book_File - files belonging to the book.
Book_Genre - genres belonging to the book
Books - info about all books , such as title, photo, short description, language and release date.
Chats - creation date, photo(foreign key on Photos) and title of the chat.
Files - files of books, links for downloading and files size.
Friendships - contains which users are friens.
Genres - names of genres.
Photos - links to the photos.
Registration_Links - link for registration, created time of it.
Reviews - rating and description of review for concrete book. Can be write only by registered user. Must be approved with moderator before publishing.
Roles - user rights on the site and their meanings, such as admin, moderator.
Users - username, hashed password, email, fullname and profile photo(foreign key on Photos) of user.
User_Activity - friends activity display and notification information.
User_Achievment - what achievements did the user reach.
User_Book - illustrates which books are in the user library, which ones are favorite, read, the user wants to read, and which ones he is waiting for.
User_Message_Chat - what, when and who wrote in which chat.
User_Role - which user has what roles and rights.