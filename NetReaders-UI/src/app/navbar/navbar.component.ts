import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../books/book.service';
import { NavbarService } from './navbar.service';
import { Genre } from '../model';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  genres: Genre[];

  constructor(private navbarService: NavbarService,
    private activatedRoute: ActivatedRoute,
    public router: Router) { }

  ngOnInit() {
    this.navbarService.getAllGenres().subscribe(
      response => {
        if (!response.isSuccessful) {
          this.router.navigate(['/error']);
        } else {
          this.genres = response.obj;
        }
      }
    );
  }

  onEnter(bookname: string) {
    if (!bookname) {
      this.router.navigateByUrl(`/books/range/1`);
      return;
    }
    this.router.navigateByUrl(`/books/byname/${bookname}/1`);
  }

}
