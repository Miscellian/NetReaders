/*
 * 	Use only after full drop of all tables to prevent mistakes
 *  Insert own set of data on the end of specific block
 *  Dont forget to share with us if you add something important (:
*/
/*----------------------------DEFAULT COMMON TABLES---------------------------------------*/

INSERT INTO "photos"("link")
VALUES ('<user_default_photo'),
       ('<book_default_photo>'),
       ('<achievement_default_photo>'),
       ('<chat_default_photo>');
/*----------------------------------------------------------------------------------------*/


/*----------------------------DEFAULT USERS-----------------------------------------------*/
INSERT INTO "roles"(role_name)
VALUES ('SUPER_ADMIN'),
       ('ADMIN'),
       ('GENERAL_MODERATOR'),
       ('ANNOUNCEMENT_MODERATOR'),
       ('OVERVIEW_MODERATOR'),
       ('REVIEW_MODERATOR'),
       ('USER');

INSERT INTO "users"(user_name, user_password, email, first_name, last_name)
VALUES ('root', 'topsecretpassword2019', 'super.netreaders@mail.com', 'Ilon', 'Mask'),
       ('ivan', 'qwerty12345', 'ivan@gmail.com', 'Ivan', 'Ivanov'),
       ('kingOfNation', '1111', 'prohovnyk.petro@mail.ru', 'Petro', NULL),
       ('pikasso', '45678', 'alan.turing@gmail.ua', NULL, NULL),
       ('hotdog007', 'password1010', 'hotdog007@mail.ua', NULL, NULL),
       ('newuser', '102102passowrdf', 'john.ronald@you.com', 'John', 'Leskov');

INSERT INTO user_role(user_id, role_id)
VALUES ((SELECT user_id
         FROM users
         WHERE user_name = 'hotdog007'),
        (SELECT role_id
         FROM roles
         WHERE role_name = 'USER')),
       ((SELECT user_id
         FROM users
         WHERE user_name = 'newuser'),
        (SELECT role_id
         FROM roles
         WHERE role_name = 'REVIEW_MODERATOR')),
       ((SELECT user_id
         FROM users
         WHERE user_name = 'pikasso'),
        (SELECT role_id
         FROM roles
         WHERE role_name = 'ANNOUNCEMENT_MODERATOR')),
       ((SELECT user_id
         FROM users
         WHERE user_name = 'kingOfNation'),
        (SELECT role_id
         FROM roles
         WHERE role_name = 'GENERAL_MODERATOR')),
       ((SELECT user_id
         FROM users
         WHERE user_name = 'ivan'),
        (SELECT role_id
         FROM roles
         WHERE role_name = 'ADMIN')),
       ((SELECT user_id
         FROM users
         WHERE user_name = 'root'),
        (SELECT role_id
         FROM roles
         WHERE role_name = 'SUPER_ADMIN'));

UPDATE "users"
SET profile_photo = (
    SELECT DISTINCT photo_id
    FROM photos
    WHERE link = '<user_default_photo');
/*----------------------------------------------------------------------------------------*/


/*----------------------------DEFAULT BOOKS-----------------------------------------------*/
INSERT INTO "books"(title, photo, description, release_date, book_language)
VALUES ('Harry Potter and the Philosopher`s stone',
        NULL,
        'Harry Potter and the Philosopher`s Stone is the first novel in the Harry Potter series. The book was first published on 26 June, 1997[1] by Bloomsbury in London, and was later made into a film of the same name.',
        TO_DATE('26/06/1997', 'DD/MM/YYYY'),
        'en'),

       ('Don Quixote',
        NULL,
        'Published in two parts, in 1605 and 1615, Don Quixote is the most influential work of literature from the Spanish Golden Age and the entire Spanish literary canon.',
        TO_DATE('14/04/1610', 'DD/MM/YYYY'),
        'es'),

       ('Robinson Crusoe',
        NULL,
        'The first edition credited the work`s protagonist Robinson Crusoe as its author, leading many readers to believe he was a real person and the book a travelogue of true incidents.',
        TO_DATE('25/04/1719', 'DD/MM/YYYY'),
        'en'),

       ('War and Peace',
        NULL,
        'The novel chronicles the French invasion of Russia and the impact of the Napoleonic era on Tsarist society through the stories of five Russian aristocratic families.',
        TO_DATE('20/07/1869', 'DD/MM/YYYY'),
        'ru'),

       ('Kobzar',
        NULL,
        'From that time on this title has been applied to Shevchenko`s poetry in general and acquired a symbolic meaning of the Ukrainian national and literary revival.',
        TO_DATE('2/09/1884', 'DD/MM/YYYY'),
        'ua'),

       ('Odiseea',
        NULL,
        'The Odyssey is fundamental to the modern Western canon; it is the second-oldest extant work of Western literature, while the Iliad is the oldest.',
        TO_DATE('10/10/10', 'DD/MM/YYYY'),
        'gr'),

       ('Testament',
        NULL,
        'Shevchenko`s "Testament", (Zapovit, 1845), has been translated into more than 150 languages and set to music in the 1870s by H. Hladky.',
        TO_DATE('25/12/1845', 'DD/MM/YYYY'),
        'ua'),

       ('The Dark Tower: The Gunslinger',
        NULL,
        'The story centers upon Roland Deschain, the last gunslinger, who has been chasing his adversary, "the man in black," for many years. The novel fuses Western fiction with fantasy, science fiction, and horror, following Roland''s trek through a vast desert and beyond in search of the man in black. Roland meets several people along his journey, including a boy named Jake Chambers, who travels with him part of the way.',
        TO_DATE('10/07/1982', 'DD/MM/YYYY'),
        'en'),

       ('The Dark Tower II: The Drawing of the Three',
        NULL,
        'It is the second book in The Dark Tower series, published by Grant in 1987. The series was inspired by Childe Roland to the Dark Tower Came by Robert Browning. The story is a continuation of The Gunslinger and follows Roland of Gilead and his quest towards the Dark Tower. The subtitle of this novel is RENEWAL',
        TO_DATE('18/03/1987', 'DD/MM/YYYY'),
        'en'),

       ('The Dark Tower III: The Waste Lands',
        NULL,
        'It is the third book of The Dark Tower series. The original limited edition hardcover featuring full-color illustrations by Ned Dameron was published in 1991 by Grant.',
        TO_DATE('26/08/1991', 'DD/MM/YYYY'),
        'en'),

       ('The Dark Tower: The Wind Through the Keyhole',
        NULL,
        'The Dark Tower: The Wind Through the Keyhole is a fantasy novel by American writer Stephen King. As part of The Dark Tower series, it is the eighth novel, but it is set chronologically between volumes four and five. First mentioned by King in 2009, after the controversial ending of the seventh novel in 2004, this new episode was announced on the author`s official site on March 10, 2011.',
        TO_DATE('21/02/2012', 'DD/MM/YYYY'),
        'en'),

       ('The Dark Tower V: Wolves of the Calla',
        NULL,
        'It is the fifth book in his The Dark Tower series. The book continues the story of Roland Deschain, Eddie Dean, Susannah Dean, Jake Chambers, and Oy as they make their way toward the Dark Tower. The subtitle of this novel is Resistance.',
        TO_DATE('04/11/2003', 'DD/MM/YYYY'),
        'en'),

       ('The Dark Tower VI: Song of Susannah',
        NULL,
        'Song of Susannah is a fantasy novel by American writer Stephen King. It is the sixth book in his Dark Tower series. Its subtitle is Reproduction.',
        TO_DATE('08/07/2004', 'DD/MM/YYYY'),
        'en'),

       ('The Dark Tower VII: The Dark Tower',
        NULL,
        'It is the seventh and final book in his Dark Tower series. It was published by Grant on September 21, 2004 (King`s birthday), and illustrated by Michael Whelan.[1] It has four subtitles: REPRODUCTION, REVELATION, REDEMPTION, and RESUMPTION – all but the second of these having been used as subtitles for previous novels in the series.',
        TO_DATE('21/09/2004', 'DD/MM/YYYY'),
        'en'),

       ('The Da Vinci Code',
        NULL,
        'It is Brown`s second novel to include the character Robert Langdon: the first was his 2000 novel Angels & Demons. The Da Vinci Code follows "symbologist" Robert Langdon and cryptologist Sophie Neveu after a murder in the Louvre Museum in Paris causes them to become involved in a battle between the Priory of Sion and Opus Dei over the possibility of Jesus Christ having been a companion to Mary Magdalene.',
        TO_DATE('07/04/2003', 'DD/MM/YYYY'),
        'en'),

       ('Angels & Demons',
        NULL,
        'The novel introduces the character Robert Langdon, who recurs as the protagonist of Brown`s subsequent novels. Angels & Demons shares many stylistic literary elements with its sequels, such as conspiracies of secret societies, a single-day time frame, and the Catholic Church.',
        TO_DATE('04/05/2000', 'DD/MM/YYYY'),
        'en'),

       ('Harry Potter and the Prisoner of Azkaban',
        NULL,
        'The book follows Harry Potter, a young wizard, in his third year at Hogwarts School of Witchcraft and Wizardry',
        TO_DATE('08/07/1999', 'DD/MM/YYYY'),
        'en'),

       ('Harry Potter and the Chamber of Secrets',
        NULL,
        'The plot follows Harry`s second year at Hogwarts School of Witchcraft and Wizardry, during which a series of messages on the walls of the school`s corridors warn that the "Chamber of Secrets" has been opened and that the "heir of Slytherin" would kill all pupils who do not come from all-magical families.',
        TO_DATE('02/07/1998', 'DD/MM/YYYY'),
        'en'),

       ('Harry Potter and the Goblet of Fire',
        NULL,
        'It follows Harry Potter, a wizard in his fourth year at Hogwarts School of Witchcraft and Wizardry and the mystery surrounding the entry of Harry`s name into the Triwizard Tournament, in which he is forced to compete.',
        TO_DATE('08/07/2000', 'DD/MM/YYYY'),
        'en'),

       ('Harry Potter and the Order of the Phoenix',
        NULL,
        'It follows Harry Potter`s struggles through his fifth year at Hogwarts School of Witchcraft and Wizardry, including the surreptitious return of the antagonist Lord Voldemort, O.W.L. exams, and an obstructive Ministry of Magic.',
        TO_DATE('21/07/2003', 'DD/MM/YYYY'),
        'en'),

       ('Harry Potter and the Half-Blood Prince',
        NULL,
        'Harry Potter and the Half-Blood Prince is a fantasy novel written by British author J.K. Rowling and the sixth and penultimate novel in the Harry Potter series. Set during Harry Potter`s sixth year at Hogwarts, the novel explores the past of Harry`s nemesis, Lord Voldemort, and Harry`s preparations for the final battle against Voldemort alongside his headmaster and mentor Albus Dumbledore.',
        TO_DATE('16/07/2005', 'DD/MM/YYYY'),
        'en'),

       ('Harry Potter and the Deathly Hallows',
        NULL,
        'Harry Potter and the Deathly Hallows is a fantasy novel written by British author J. K. Rowling and the seventh and final novel of the Harry Potter series. It was released on 21 July 2007 in the United Kingdom by Bloomsbury Publishing, in the United States by Scholastic, and in Canada by Raincoast Books. The novel chronicles the events directly following Harry Potter and the Half-Blood Prince (2005) and the final confrontation between the wizards Harry Potter and Lord Voldemort.',
        TO_DATE('21/07/2007', 'DD/MM/YYYY'),
        'en'),

       ('The Great Gatsby',
        NULL,
        'The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922. The story primarily concerns the young and mysterious millionaire Jay Gatsby and his quixotic passion and obsession with the beautiful former debutante Daisy Buchanan.',
        TO_DATE('10/04/1925', 'DD/MM/YYYY'),
        'en'),

       ('To Kill a Mockingbird',
        NULL,
        'To Kill a Mockingbird is a novel by Harper Lee published in 1960. Instantly successful, widely read in high schools and middle schools in the United States, it has become a classic of modern American literature, winning the Pulitzer Prize. The plot and characters are loosely based on Lee`s observations of her family, her neighbors and an event that occurred near her hometown of Monroeville, Alabama, in 1936, when she was 10 years old.',
        TO_DATE('11/07/1960', 'DD/MM/YYYY'),
        'en'),

       ('Nineteen Eighty-Four',
        NULL,
        'Nineteen Eighty-Four: A Novel, often published as 1984, is a dystopian novel by English novelist George Orwell. It was published in June 1949 by Secker & Warburg as Orwell`s ninth and final book completed in his lifetime. The story was mostly written at Barnhill, a farmhouse on the Scottish island of Jura, at times while Orwell suffered from severe tuberculosis. Thematically, Nineteen Eighty-Four centres on the consequences of government overreach, totalitarianism, and repressive regimentation of all persons and behaviours within society.',
        TO_DATE('02/10/1984', 'DD/MM/YYYY'),
        'en'),

       ('The Hobbit',
        NULL,
        'The Hobbit is set within Tolkien`s fictional universe and follows the quest of home-loving Bilbo Baggins, the titular hobbit, to win a share of the treasure guarded by Smaug the dragon. Bilbo`s journey takes him from light-hearted, rural surroundings into more sinister territory.',
        TO_DATE('21/09/1937', 'DD/MM/YYYY'),
        'en'),

       ('The Lord of the Rings : The Fellowship of the Ring',
        NULL,
        'The Fellowship of the Ring is the first of three volumes of the epic novel The Lord of the Rings by the English author J. R. R. Tolkien. It is followed by The Two Towers and The Return of the King. It takes place in the fictional universe of Middle-earth. It was originally published on 29 July 1954 in the United Kingdom.',
        TO_DATE('29/07/1954', 'DD/MM/YYYY'),
        'en'),

       ('The Lord of the Rings : The Two Towers',
        NULL,
        'The Two Towers is the second volume of J. R. R. Tolkien`s high fantasy novel The Lord of the Rings. It is preceded by The Fellowship of the Ring and followed by The Return of the King.',
        TO_DATE('11/11/1954', 'DD/MM/YYYY'),
        'en'),

       ('The Lord of the Rings : The Return of the King',
        NULL,
        'The Return of the King is the third and final volume of J. R. R. Tolkien`s The Lord of the Rings, following The Fellowship of the Ring and The Two Towers. The story begins in the kingdom of Gondor, which is soon to be attacked by the Dark Lord Sauron.',
        TO_DATE('20/10/1955', 'DD/MM/YYYY'),
        'en'),

       ('The Sun and Her Flowers',
        NULL,
        'The Sun and Her Flowers (stylized as the sun and her flowers) is Rupi Kaur`s second collection of poetry, published in 2017. It is composed of five chapters, with illustrations by the author.',
        TO_DATE('03/10/2017', 'DD/MM/YYYY'),
        'en'),

       ('Milk and Honey',
        NULL,
        'Milk and Honey (stylized as milk and honey) is a collection of poetry and prose by Rupi Kaur. The collection is about survival. It is divided into four chapters, with each chapter serving a different purpose. Violence, abuse, love, loss and femininity are prevalent themes. Milk and Honey was published on November 4, 2014. This poetry collection was sold over 2.5 million times. It listed on The New York Times Best Seller list for more than 77 weeks. It has been translated into 25 languages',
        TO_DATE('04/11/2014', 'DD/MM/YYYY'),
        'en'),

       ('Iliad',
        NULL,
        'The Iliad is an ancient Greek epic poem in dactylic hexameter, traditionally attributed to Homer. Set during the Trojan War, the ten-year siege of the city of Troy (Ilium) by a coalition of Greek states, it tells of the battles and events during the weeks of a quarrel between King Agamemnon and the warrior Achilles.',
        TO_DATE('10/10/10', 'DD/MM/YYYY'),
        'en'),

       ('The Dialogue of the Dogs',
        NULL,
        'The Dialogue of the Dogs is a short story originating from the fantasy world of Ensign Campuzano, a character from another short story, The Deceitful Marriage ("El casamiento engañoso"). Both are written by author Miguel de Cervantes. It was originally published in a 1613 collection of novellas called "Novelas ejemplares".',
        TO_DATE('29/09/1610', 'DD/MM/YYYY'),
        'es'),

       ('A Journal of the Plague Year',
        NULL,
        'This novel is an account of one man`s experiences of the year 1665, in which the Great Plague or the bubonic plague struck the city of London. The book is told somewhat chronologically, though without sections or chapter headings.',
        TO_DATE('2/12/1722', 'DD/MM/YYYY'),
        'en'),

       ('The Call of the Wild',
        NULL,
        'The Call of the Wild is a short adventure novel by Jack London, published in 1903 and set in Yukon, Canada, during the 1890s Klondike Gold Rush, when strong sled dogs were in high demand. The central character of the novel is a dog named Buck. The story opens at a ranch in Santa Clara Valley, California, when Buck is stolen from his home and sold into service as a sled dog in Alaska.',
        TO_DATE('23/12/1903', 'DD/MM/YYYY'),
        'en'),

       ('White Fang',
        NULL,
        'It is a companion novel (and a thematic mirror) to London`s best-known work, The Call of the Wild, which is about a kidnapped, domesticated dog embracing his wild ancestry to survive and thrive in the wild.',
        TO_DATE('13/03/1906', 'DD/MM/YYYY'),
        'en'),

       ('Martin Eden',
        NULL,
        'Eden represents writers` frustration with publishers by speculating that when he mails off a manuscript, a "cunning arrangement of cogs" immediately puts it in a new envelope and returns it automatically with a rejection slip.',
        TO_DATE('13/03/1906', 'DD/MM/YYYY'),
        'en'),

       ('The Sea-Wolf',
        NULL,
        'The Sea-Wolf is a 1904 psychological adventure novel by American writer Jack London. The book''s protagonist, Humphrey van Weyden, is a literary critic who is a survivor of an ocean collision and who comes under the dominance of Wolf Larsen, the powerful and amoral sea captain who rescues him. Its first printing of forty thousand copies was immediately sold out before publication on the strength of London`s previous The Call of the Wild.',
        TO_DATE('11/01/1904', 'DD/MM/YYYY'),
        'en'),

       ('Fahrenheit 451',
        NULL,
        'Often regarded as one of his best works, the novel presents a future American society where books are outlawed and "firemen" burn any that are found. The book`s tagline explains the title: "Fahrenheit 451 – the temperature at which book paper catches fire, and burns..." The lead character, Guy Montag, is a fireman who becomes disillusioned with his role of censoring literature and destroying knowledge, eventually quitting his job and committing himself to the preservation of literary and cultural writings.',
        TO_DATE('19/10/1953', 'DD/MM/YYYY'),
        'en'),

       ('The Martian Chronicles',
        NULL,
        'The Martian Chronicles is a 1950 science fiction short story fixup by American writer Ray Bradbury, which chronicles the colonization of Mars by humans fleeing from a troubled and eventually atomically devastated Earth, and the conflict between aboriginal Martians and the new colonists. The book lies somewhere in between a short story collection and an episodic novel, containing stories originally published in the late 1940s in science fiction magazines.',
        TO_DATE('04/03/1950', 'DD/MM/YYYY'),
        'en'),

       ('Dandelion Wine',
        NULL,
        'Dandelion Wine is a 1957 novel by Ray Bradbury, taking place in the summer of 1928 in the fictional town of Green Town, Illinois, based upon Bradbury''s childhood home of Waukegan, Illinois. The novel developed from the short story "Dandelion Wine" which appeared in the June 1953 issue of Gourmet magazine.',
        TO_DATE('01/12/1957', 'DD/MM/YYYY'),
        'en');

INSERT INTO "genres"(genre_name)
VALUES ('Heroes and heroism'),
       ('Poetry'),
       ('Novel'),
       ('Historical'),
       ('Adventure'),
       ('Fiction'),
       ('Fantasy'),
       ('Horror'),
       ('Western'),
       ('Thriller'),
       ('Mystery'),
       ('Tragedy');

INSERT INTO book_genre(book_id, genre_id)
VALUES ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Philosopher`s stone'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Don Quixote'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Robinson Crusoe'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fiction')),
       ((SELECT book_id
         FROM books
         WHERE title = 'Robinson Crusoe'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Adventure')),

       ((SELECT book_id
         FROM books
         WHERE title = 'War and Peace'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'War and Peace'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Historical')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Kobzar'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Poetry')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Odiseea'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Heroes and heroism')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Testament'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Poetry')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower: The Gunslinger'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Horror')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower: The Gunslinger'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Western')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower: The Gunslinger'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower: The Gunslinger'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower II: The Drawing of the Three'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Horror')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower II: The Drawing of the Three'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Western')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower II: The Drawing of the Three'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower III: The Waste Lands'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Horror')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower III: The Waste Lands'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Western')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower III: The Waste Lands'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower: The Wind Through the Keyhole'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Horror')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower: The Wind Through the Keyhole'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Western')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower: The Wind Through the Keyhole'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower V: Wolves of the Calla'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Horror')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower V: Wolves of the Calla'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Western')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower V: Wolves of the Calla'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower VI: Song of Susannah'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Horror')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower VI: Song of Susannah'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Western')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower VI: Song of Susannah'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower VII: The Dark Tower'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Horror')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower VII: The Dark Tower'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Western')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower VII: The Dark Tower'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Da Vinci Code'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Thriller')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Da Vinci Code'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Mystery')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Da Vinci Code'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fiction')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Angels & Demons'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Thriller')),
       ((SELECT book_id
         FROM books
         WHERE title = 'Angels & Demons'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Mystery')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Prisoner of Azkaban'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Chamber of Secrets'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Goblet of Fire'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Order of the Phoenix'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Half-Blood Prince'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Deathly Hallows'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Great Gatsby'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Tragedy')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Great Gatsby'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),

       ((SELECT book_id
         FROM books
         WHERE title = 'To Kill a Mockingbird'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Nineteen Eighty-Four'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'Nineteen Eighty-Four'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fiction')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Hobbit'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Hobbit'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Lord of the Rings : The Fellowship of the Ring'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Lord of the Rings : The Fellowship of the Ring'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Lord of the Rings : The Two Towers'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Lord of the Rings : The Two Towers'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Lord of the Rings : The Return of the King'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Lord of the Rings : The Return of the King'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fantasy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Sun and Her Flowers'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Poetry')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Milk and Honey'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Poetry')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Odiseea'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Heroes and heroism')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dialogue of the Dogs'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),

       ((SELECT book_id
         FROM books
         WHERE title = 'A Journal of the Plague Year'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'A Journal of the Plague Year'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Historical')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Call of the Wild'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Call of the Wild'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Adventure')),

       ((SELECT book_id
         FROM books
         WHERE title = 'White Fang'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'White Fang'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Adventure')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Martin Eden'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'Martin Eden'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Adventure')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Sea-Wolf'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Sea-Wolf'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Adventure')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Fahrenheit 451'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel')),
       ((SELECT book_id
         FROM books
         WHERE title = 'Fahrenheit 451'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fiction')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Martian Chronicles'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Fiction')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Dandelion Wine'),
        (SELECT genre_id
         FROM genres
         WHERE genre_name = 'Novel'));

INSERT INTO "authors"(author_name)
VALUES ('Miguel de Cervantes'),
       ('J. K. Rowling'),
       ('Leo Tolstoy'),
       ('Taras Shevchenko'),
       ('Homer'),
       ('Daniel Defoe'),
       ('Steven King'),
       ('Dan Brown'),
       ('F. Scott Fitzgerald'),
       ('Harper Lee'),
       ('George Orwell'),
       ('J. R. R. Tolkien'),
       ('Rupi Kaur'),
       ('Jack London'),
       ('Ray Bradbury');

INSERT INTO book_author(book_id, author_id)
VALUES ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Philosopher`s stone'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'J. K. Rowling')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Don Quixote'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Miguel de Cervantes')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Robinson Crusoe'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Daniel Defoe')),

       ((SELECT book_id
         FROM books
         WHERE title = 'War and Peace'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Leo Tolstoy')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Kobzar'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Taras Shevchenko')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Odiseea'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Homer')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Testament'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Taras Shevchenko')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower: The Gunslinger'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Steven King')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower II: The Drawing of the Three'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Steven King')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower II: The Drawing of the Three'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Steven King')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower III: The Waste Lands'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Steven King')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower: The Wind Through the Keyhole'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Steven King')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower V: Wolves of the Calla'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Steven King')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower VI: Song of Susannah'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Steven King')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower VII: The Dark Tower'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Steven King')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Da Vinci Code'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Dan Brown')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Angels & Demons'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Dan Brown')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Prisoner of Azkaban'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'J. K. Rowling')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Chamber of Secrets'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'J. K. Rowling')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Goblet of Fire'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'J. K. Rowling')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Order of the Phoenix'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'J. K. Rowling')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Half-Blood Prince'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'J. K. Rowling')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Deathly Hallows'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'J. K. Rowling')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Great Gatsby'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'F. Scott Fitzgerald')),

       ((SELECT book_id
         FROM books
         WHERE title = 'To Kill a Mockingbird'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Harper Lee')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Nineteen Eighty-Four'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'George Orwell')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Hobbit'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'J. R. R. Tolkien')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Lord of the Rings : The Fellowship of the Ring'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'J. R. R. Tolkien')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Lord of the Rings : The Two Towers'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'J. R. R. Tolkien')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Lord of the Rings : The Return of the King'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'J. R. R. Tolkien')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Sun and Her Flowers'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Rupi Kaur')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Milk and Honey'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Rupi Kaur')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Iliad'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Homer')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dialogue of the Dogs'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Miguel de Cervantes')),

       ((SELECT book_id
         FROM books
         WHERE title = 'A Journal of the Plague Year'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Daniel Defoe')),

       ((SELECT book_id
         FROM books
         WHERE title = 'White Fang'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Jack London')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Martin Eden'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Jack London')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Sea-Wolf'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Jack London')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Fahrenheit 451'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Ray Bradbury')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Martian Chronicles'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Ray Bradbury')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Dandelion Wine'),
        (SELECT author_id
         FROM authors
         WHERE author_name = 'Ray Bradbury'));
/*-----------------------------------------------------------------------------------------*/


/*----------------------------DEFAULT Announcements----------------------------------------*/
INSERT INTO "announcements"(announcement_date, description, published)
VALUES (TO_DATE('19/11/2019', 'DD/MM/YYYY'), 'Autumn event', true),
       (TO_DATE('24/11/2019', 'DD/MM/YYYY'), '2019 New Year book`s event', true),
       (TO_DATE('20/12/2019', 'DD/MM/YYYY'), 'Add a new genre of books for children', true),
       (TO_DATE('20/12/2019', 'DD/MM/YYYY'), 'Add a new genre of book for adults', true),
       (TO_DATE('24/12/2019', 'DD/MM/YYYY'), 'New books will be published', true),
       (TO_DATE('25/12/2019', 'DD/MM/YYYY'), 'Stephen King books will be translated in ukrainian', true),
       (TO_DATE('07/01/2020', 'DD/MM/YYYY'), 'Christmas event', true),
       (TO_DATE('20/12/2019', 'DD/MM/YYYY'), 'Not published yet', false);

INSERT INTO book_announcement(book_id, announcement_id)
VALUES ((SELECT book_id
         FROM books
         WHERE title = 'The Great Gatsby'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Autumn event')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Da Vinci Code'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Autumn event')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Deathly Hallows'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = '2019 New Year book`s event')),
       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Chamber of Secrets'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = '2019 New Year book`s event')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Sun and Her Flowers'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = '2019 New Year book`s event')),
       ((SELECT book_id
         FROM books
         WHERE title = 'Milk and Honey'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = '2019 New Year book`s event')),
       ((SELECT book_id
         FROM books
         WHERE title = 'A Journal of the Plague Year'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = '2019 New Year book`s event')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Call of the Wild'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = '2019 New Year book`s event')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Prisoner of Azkaban'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Add a new genre of books for children')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Da Vinci Code'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Add a new genre of books for children')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower VII: The Dark Tower'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Add a new genre of books for children')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Harry Potter and the Philosopher`s stone'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Add a new genre of book for adults')),
       ((SELECT book_id
         FROM books
         WHERE title = 'Odiseea'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Add a new genre of book for adults')),
       ((SELECT book_id
         FROM books
         WHERE title = 'Testament'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Add a new genre of book for adults')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Hobbit'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'New books will be published')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Lord of the Rings : The Return of the King'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'New books will be published')),
       ((SELECT book_id
         FROM books
         WHERE title = 'White Fang'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'New books will be published')),

       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower: The Gunslinger'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Stephen King books will be translated in ukrainian')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower II: The Drawing of the Three'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Stephen King books will be translated in ukrainian')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower III: The Waste Lands'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Stephen King books will be translated in ukrainian')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower: The Wind Through the Keyhole'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Stephen King books will be translated in ukrainian')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower VI: Song of Susannah'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Stephen King books will be translated in ukrainian')),
       ((SELECT book_id
         FROM books
         WHERE title = 'The Dark Tower V: Wolves of the Calla'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Stephen King books will be translated in ukrainian')),

       ((SELECT book_id
         FROM books
         WHERE title = 'Robinson Crusoe'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Christmas event')),
       ((SELECT book_id
         FROM books
         WHERE title = 'Kobzar'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Christmas event')),

       ((SELECT book_id
         FROM books
         WHERE title = 'War and Peace'),
        (SELECT announcement_id
         FROM announcements
         WHERE description = 'Not published yet'));
/*-----------------------------------------------------------------------------------------*/
