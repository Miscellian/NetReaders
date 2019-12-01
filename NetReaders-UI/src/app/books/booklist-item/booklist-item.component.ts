import {Component, Input, OnInit} from '@angular/core';
import {Book} from '../../model';
import {Router} from "@angular/router";
import {BookService} from "../book.service";

@Component({
  selector: 'app-booklist-item',
  templateUrl: './booklist-item.component.html',
  styleUrls: ['./booklist-item.component.css']
})
export class BooklistItemComponent implements OnInit {
  @Input() public book: Book;

  constructor(public router: Router,
              private bookService: BookService) {
  }

  ngOnInit() {
  }

  addToLibrary() {
    if (localStorage.getItem('UserName') === null) {
      this.router.navigate(['login']);
    } else {
      let username = localStorage.getItem("UserName");
      this.bookService.addToLibrary(username, this.book.id);
      this.router.navigateByUrl('/users/' + username);
    }
  }

}
