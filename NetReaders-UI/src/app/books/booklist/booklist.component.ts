import { Component, OnInit } from '@angular/core';
import { BookService } from '../book.service';
import { BookDto } from '../../model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-booklist',
  templateUrl: './booklist.component.html',
  styleUrls: ['./booklist.component.css']
})
export class BooklistComponent implements OnInit {
  funcs: any = {
    genre: () => { this.func = () => this.bookService.getByGenre(this.arg, this.page.toString()); },
    author: () => { this.func = () => this.bookService.getByAuthor(this.arg, this.page.toString()); },
    name: () => { this.func = () => this.bookService.getByName(this.arg, this.page.toString()); },
    range: () => { this.func = () => this.bookService.getByRange(this.page.toString()); }
  };

  funcsCount: any = {
    range: () => { this.func = () => this.bookService.getCount(); },
    author: () => { this.func = () => this.bookService.getCountByAuthor(this.arg); },
    genre: () => { this.func = () => this.bookService.getCountByGenre(this.arg) }
  }

  page: number;
  arg: string;
  paramArgs: any;
  count: number;
  bookdtos: BookDto[];
  func: any = () => this.bookService.getByRange(this.page.toString());
  constructor(private bookService: BookService,
    private activatedRoute: ActivatedRoute,
    public router: Router) { }

  loadPage(page: number) {
    this.router.navigateByUrl(`/books/range/${this.page}`);
  }

  loadData(params: any) {
    this.arg = params['id'];
    this.page = params['page'];
    this.paramArgs = params;
    this.funcsCount[this.activatedRoute.snapshot.data.filter]();
    this.func().subscribe(response => {
      if (!response.isSuccessful) {
        this.router.navigate(['/error']);
      } else {
        this.count = + response.obj;
      }
    });
    this.funcs[this.activatedRoute.snapshot.data.filter]();
    this.func().subscribe(response => {
      if (!response.isSuccessful) {
        this.router.navigate(['/error']);
      } else {
        this.bookdtos = response.obj;
      }
    });
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(
      params =>
        this.loadData(params)
    );
  }
}
