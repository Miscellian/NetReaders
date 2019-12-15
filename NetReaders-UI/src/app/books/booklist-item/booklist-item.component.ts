import {Component, Input, OnInit} from '@angular/core';
import {Book, UserBookLibrary} from '../../model';
import {Router} from '@angular/router';
import {BookService} from '../book.service';
import {BooklistComponent} from '../booklist/booklist.component';

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
                private bookService: BookService,
                private booklistComponent: BooklistComponent) {
        this.userBook = new UserBookLibrary();
        this.username = localStorage.getItem('UserName');
        if (this.username) {
            this.authorities = JSON.parse(localStorage.getItem('Authorities')).map(val => val.authority);
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
            this.checkInLibrary();
        }
    }

    checkInLibrary() {
        this.bookService.checkInLibrary(this.userBook).subscribe(
            response => this.inUserLibrary = (response as boolean),
            () => this.router.navigate(['/error'])
        );
    }

    addToLibrary() {
        if (this.username === null) {
            this.router.navigate(['login']);
        } else {
            this.bookService.addToLibrary(this.userBook).subscribe(
                () => this.checkInLibrary(),
                () => this.router.navigate(['/error'])
            );
        }
    }

    removeFromLibrary() {
        this.bookService.removeFromLibrary(this.userBook).subscribe(
            () => this.checkInLibrary(),
            () => this.router.navigate(['/error'])
        );
    }

    onPublish() {
        this.bookService.publishBook(this.book.id).subscribe(
            () => this.booklistComponent.loadBooks(),
            () => this.router.navigate(['/error'])
        );
    }
}
