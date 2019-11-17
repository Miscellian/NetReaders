import { Component, OnInit } from '@angular/core';
import { BookService } from '../book.service';
import { Book, BookDto } from '../../model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-booklist',
  templateUrl: './booklist.component.html',
  styleUrls: ['./booklist.component.css']
})
export class BooklistComponent implements OnInit {
  funcs: any = {
    genre: () => {this.func = () => this.bookService.getByGenre(this.arg); },
    author: () => {this.func = () => this.bookService.getByAuthor(this.arg); },
    name: () => {this.func = () => this.bookService.getByName(this.arg); }
  };

  arg: string;
  bookdtos: BookDto[];
  func: any = () => this.bookService.getByAuthor(this.arg);
  constructor(private bookService: BookService,
              private activatedRoute: ActivatedRoute,
              public router: Router ) { }

  ngOnInit() {
    this.arg = this.activatedRoute.snapshot.paramMap.get('id');
    this.funcs[this.activatedRoute.snapshot.data.filter]();
    this.func().subscribe(response => {
      if (!response.isSuccessful) {
        this.router.navigate(['/error']);
      } else {
        this.bookdtos = response.obj;
      }
    });
  }

}
