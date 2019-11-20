import { Component, OnInit, Input } from '@angular/core';
import { BookDto, Book, Genre, Author } from '../../model';

@Component({
  selector: 'app-booklist-item',
  templateUrl: './booklist-item.component.html',
  styleUrls: ['./booklist-item.component.css']
})
export class BooklistItemComponent implements OnInit {
  @Input() public bookdto: BookDto;
  book: Book;
  genres: Genre[];
  authors: Author[];
  constructor() { }

  ngOnInit() {
    this.authors = this.bookdto.authors;
    this.genres = this.bookdto.genres;
    this.book = this.bookdto.book;
  }

}
