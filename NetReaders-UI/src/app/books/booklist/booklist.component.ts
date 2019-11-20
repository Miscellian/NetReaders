import { Component, OnInit } from '@angular/core';
import { BookService } from '../book.service';
import { Book, BookDto } from '../../model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-booklist',
  templateUrl: './booklist.component.html',
  styleUrls: ['./booklist.component.css']
})
export class BooklistComponent implements OnInit {
  funcs: any = {
    genre: () => { this.func = () => this.bookService.getByGenre(this.arg); },
    author: () => { this.func = () => this.bookService.getByAuthor(this.arg); },
    name: () => { this.func = () => this.bookService.getByName(this.arg); },
    range: () => { this.func = () => this.bookService.getByRange(this.arg); }
  };

  arg: string;
  count: number;
  bookdtos: BookDto[];
  func: any = () => this.bookService.getByRange(this.arg);
  constructor(private bookService: BookService,
    private activatedRoute: ActivatedRoute,
    public router: Router) { }

  ngOnInit() {
<<<<<<< HEAD
    this.bookService.getCount().subscribe(response => {
      if (!response.isSuccessful) {
        this.router.navigate(['/error']);
      }
      else {
        this.count = response.obj;
      }
    });
    this.arg = this.activatedRoute.snapshot.paramMap.get('id');
    this.funcs[this.activatedRoute.snapshot.data.filter]();
    this.func().subscribe(response => {
      if (!response.isSuccessful) {
        this.router.navigate(['/error']);
      } else {
        this.bookdtos = response.obj;
=======
    this.activatedRoute.params.subscribe(
      params => {
        this.arg = params['id'];
        this.funcs[this.activatedRoute.snapshot.data.filter]();
        this.func().subscribe(response => {
          if (!response.isSuccessful) {
            this.router.navigate(['/error']);
          } else {
            this.bookdtos = response.obj;
          }
        });
>>>>>>> c1df63278c3049810abe1eff0c3791c3bdc988da
      }
    );
  }

}
