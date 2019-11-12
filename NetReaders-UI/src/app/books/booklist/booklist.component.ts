import { Component, OnInit } from '@angular/core';
import { BookService } from '../book.service';
import { Book } from '../../model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-booklist',
  templateUrl: './booklist.component.html',
  styleUrls: ['./booklist.component.css']
})
export class BooklistComponent implements OnInit {
  id: number;
  books: Book[];
  func: any = () => this.bookService.getByAuthor(this.id);
  constructor(private bookService: BookService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
  }

}
