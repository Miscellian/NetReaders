export class ResponseMessage {
    code: string;
    errorMessage: string;
}

export class Book {
    id: number;
    title: string;
    photo: number;
    description: string;
    release_date: Date;
    book_language: string;

    genres: Genre[];
    authors: Author[];
}

export class Announcement {
    id: number;
    announcement_date: Date;
    description: string;
    published: boolean;

    books: Book[];
}

export class Genre {
    id: number;
    name: string;
}

export class Author {
    id: number;
    name: string;
}
