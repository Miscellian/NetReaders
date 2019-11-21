import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../books/book.service';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private bookService: BookService,
    private activatedRoute: ActivatedRoute,
    public router: Router) { }

  ngOnInit() {
    
  }

  onEnter(bookname: string) {
    if (!bookname) {
      this.router.navigateByUrl(`/books/range/1`);
      return;
    }
    this.router.navigateByUrl(`/books/byname/${bookname}/1`);
  }

}
