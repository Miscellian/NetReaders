export class ResponseMessage<T> {
  isSuccessful: boolean;
  errorMessage: string;
  obj: T;
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
  username: string;
  userPassword: string;
  email: string;
  firstName: string;
  lastName: string;
  profilePhoto: number;
}
export class LoginInfo {
  token: string;
  type: string;
  username: string;
  authorities: Authority[];
}

export class Authority {
  authority: string;
}

export class Review {
  id: number;
  rating: number;
  description: string;
  published: boolean;
  book: Book;
}

export class UserBookLibrary {
    username: string;
    bookId: number;
}