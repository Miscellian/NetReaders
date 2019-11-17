## Description of tables in database
**Achievments** - title and description for user achievements, like "<i>read 10 books</i>". <br/>
**Activities** - contains information about user activity, such as adding a book to the library, adding a user to friends, and so on. <br/>
**Announcements** - date, description. Must be approved by moderator before publishing. <br/>
**Authors** - names of authors. <br/>
**Book_Announcement** - books belonging to the announcement. <br/>
**Book_Author** - authors belonging to the book. <br/>
**Book_File** - files belonging to the book. <br/>
**Book_Genre** - genres belonging to the book. <br/>
**Books** - info about all books , such as title, photo, short description, language and release date. <br/>
**Chats** - creation date, photo(foreign key on Photos) and title of the chat. <br/>
**Files** - files of books, links for downloading and files size. <br/>
**Friendships** - contains which users are friens. <br/>
**Genres** - names of genres. <br/>
**Photos** - links to the photos. <br/>
**Registration_Links** - link for registration, created time of it. <br/>
**Reviews** - rating and description of review for concrete book. Can be write only by registered user. Must be approved with moderator before publishing. <br/>
**Roles** - user rights on the site and their meanings, such as admin, moderator. <br/>
**Users** - username, hashed password, email, fullname and profile photo(foreign key on Photos) of user. <br/>
**User_Activity** - friends activity display and notification information. <br/>
**User_Achievment** - what achievements did the user reach. <br/>
**User_Book** - illustrates which books are in the user library, which ones are favorite, read, the user wants to read, and which ones he is waiting for. <br/>
**User_Message_Chat** - what, when and who wrote in which chat. <br/>
**User_Role** - which user has what roles and rights.
