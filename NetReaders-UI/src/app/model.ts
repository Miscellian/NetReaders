export class ResponseMessage<T> {
  isSuccessful: boolean;
  errorMessage: string;
  obj: T;
}

export class BookDto {
  book: Book;
  genres: Genre[];
  authors: Author[];
}

export class Book {
  id: number;
  title: string;
  photo: number;
  description: string;
  release_date: Date;
  book_language: string;
}

export class Genre {
  id: number;
  name: string;
}

export class Author {
  id: number;
  name: string;
}

export class User {
  userId: number;
  userNickname: string;
  userPassword: string;
  email: string;
  firstName: string;
  lastName: string;
  profilePhoto: number;
}