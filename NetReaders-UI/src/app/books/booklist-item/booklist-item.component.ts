import {Component, Input, OnInit} from '@angular/core';
import {Book, UserBookLibrary} from '../../model';
import {Router} from "@angular/router";
import {BookService} from "../book.service";

@Component({
    selector: 'app-booklist-item',
    templateUrl: './booklist-item.component.html',
    styleUrls: ['./booklist-item.component.css']
})

export class BooklistItemComponent implements OnInit {
    @Input() public book: Book;
    userBook: UserBookLibrary;
    inUserLibrary: boolean;
    username: string;
    authorities: string[];

    constructor(public router: Router,
                private bookService: BookService) {
        this.userBook = new UserBookLibrary();
        this.username = localStorage.getItem("UserName");
        if(this.username){
            this.authorities = JSON.parse(localStorage.getItem("Authorities")).map(val => val.authority);
        } else {
            this.authorities = [];
        }
    }

    ngOnInit() {
        this.LoadUserBook();
    }

    LoadUserBook() {
        if (this.username === null || this.authorities.indexOf('USER') < 0) {
            this.inUserLibrary = false;
        } else {
            this.userBook.username = this.username;
            this.userBook.bookId = this.book.id;
            this.bookService.checkInLibrary(this.userBook).subscribe(
                response => this.inUserLibrary = (<boolean>response),
                error => this.router.navigate(['/error'])
            );
        }
    }

    addToLibrary() {
        if (this.username === null) {
            this.router.navigate(['login']);
        } else {
            this.bookService.addToLibrary(this.userBook).subscribe(
                response => window.location.reload(),
                error => this.router.navigate(['/error'])
            );
        }
    }

    removeFromLibrary() {
        this.bookService.removeFromLibrary(this.userBook).subscribe(
            response => window.location.reload(),
            error => this.router.navigate(['/error'])
        );
    }
}
