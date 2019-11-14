import { Component, OnInit } from '@angular/core';
import { BookDto } from '../../model';
import { BookService } from '../book.service';

@Component({
  selector: 'app-booklist-item',
  templateUrl: './booklist-item.component.html',
  styleUrls: ['./booklist-item.component.css']
})
export class BooklistItemComponent implements OnInit {
  books: BookDto[];
  constructor(private bookService: BookService) { }

  ngOnInit() {
  }

}
