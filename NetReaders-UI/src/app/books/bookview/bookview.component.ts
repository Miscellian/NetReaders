import {Component, OnInit} from '@angular/core';
import {Book, UserBookLibrary} from '../../model';
import {BookService} from '../book.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    selector: 'app-bookview',
    templateUrl: './bookview.component.html',
    styleUrls: ['./bookview.component.css']
})
export class BookviewComponent implements OnInit {
    id: number;
    book: Book;
    userBook: UserBookLibrary;
    inUserLibrary: boolean;
    username: string;
    bookRecomendations: Book[];

    constructor(private bookService: BookService,
                private activatedRoute: ActivatedRoute,
                public router: Router) {
        this.userBook = new UserBookLibrary();
        this.username = localStorage.getItem("UserName");
    }

    ngOnInit() {
        this.id = +this.activatedRoute.snapshot.paramMap.get('id');
        this.bookService.getById(this.id).subscribe(response => {
            this.book = response;
            this.LoadUserBook();
        }, error => this.router.navigate(['/error']));
        this.bookService.getByUserPreferences(this.username).subscribe(
            response => {
                this.bookRecomendations = response;
            }
        );
    }

    LoadUserBook() {
        this.userBook.username = this.username;
        this.userBook.bookId = this.book.id;
        this.bookService.checkInLibrary(this.userBook).subscribe(
            response => this.inUserLibrary = (<boolean>response),
            error => this.router.navigate(['/error'])
        )
    }

    addToLibrary() {
        if (this.username === null) {
            this.router.navigate(['login']);
        } else {
            this.bookService.addToLibrary(this.userBook).subscribe(
                response => this.router.navigateByUrl('/users/' + this.username),
                error => this.router.navigate(['/error'])
            );
        }
    }

    removeFromLibrary() {
        this.bookService.removeFromLibrary(this.userBook).subscribe(
            response => this.router.navigateByUrl('/users/' + this.username),
            error => this.router.navigate(['/error'])
        );
    }
}
