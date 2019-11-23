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

export class User{
  constructor(
      public user_name: string,
      public fist_name: string,
      public last_name: string,
      public email: string,
      public password: string
  ){}

}
