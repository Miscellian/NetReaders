import { Component, OnInit, Input } from '@angular/core';
import { BookDto, Book } from '../../model';
import { BookService } from '../book.service';

@Component({
  selector: 'app-booklist-item',
  templateUrl: './booklist-item.component.html',
  styleUrls: ['./booklist-item.component.css']
})
export class BooklistItemComponent implements OnInit {
  @Input() public bookdto: BookDto;
  book: Book;
  genres: string;
  authors: string;
  constructor() { }

  ngOnInit() {
    this.authors = this.bookdto.authors.map(a => a.name).join(', ');
    this.genres = this.bookdto.genres.map(g => g.name).join(', ');
    this.book = this.bookdto.book;
  }

}
