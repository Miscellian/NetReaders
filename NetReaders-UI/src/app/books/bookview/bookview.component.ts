import { Component, OnInit, Input } from '@angular/core';
import { Book, BookDto, Author, Genre } from '../../model';
import { BookService } from '../book.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-bookview',
  templateUrl: './bookview.component.html',
  styleUrls: ['./bookview.component.css']
})
export class BookviewComponent implements OnInit {
  id: number;
  book: Book;
  genres: Genre[];
  authors: Author[];
  bookdto: BookDto;

  constructor(private bookService: BookService,
    private activatedRoute: ActivatedRoute,
    public router: Router) { }

  ngOnInit() {
    this.id = +this.activatedRoute.snapshot.paramMap.get('id');
    this.bookService.getById(this.id).subscribe(response => {
      if (!response.isSuccessful) {
        this.router.navigate(['/error']);
      } else {
        this.book = response.obj.book;
        this.genres = response.obj.genres;
        this.authors = response.obj.authors;
      }
    });
  }
}
