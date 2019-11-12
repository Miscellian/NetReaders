import { Component, OnInit, Input } from '@angular/core';
import {Book} from '../../model';
import { BookService } from '../book.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-bookview',
  templateUrl: './bookview.component.html',
  styleUrls: ['./bookview.component.css']
})
export class BookviewComponent implements OnInit {
  id: number;
  book: Book;

  constructor(private bookService: BookService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.id = +this.activatedRoute.snapshot.paramMap.get('id');
    this.bookService.getById(this.id).subscribe(book => this.book = book);
  }
}
