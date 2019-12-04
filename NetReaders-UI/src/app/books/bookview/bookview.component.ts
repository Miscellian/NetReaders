import {Component, OnInit} from '@angular/core';
import {Book, UserBookLibrary, Review, Authority} from '../../model';
import {BookService} from '../book.service';
import {ActivatedRoute, Router} from '@angular/router';
import { ReviewService } from '../../reviews/review.service';

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
    inFavourites: boolean;
    username: string;
    bookRecomendations: Book[];
    authorities: string[];
    publishedReviews: Review[];
    unpublishedReviews: Review[];

    constructor(private bookService: BookService,
                private activatedRoute: ActivatedRoute,
                private reviewService: ReviewService,
                public router: Router) {
        this.userBook = new UserBookLibrary();
        this.username = localStorage.getItem("UserName");
        this.authorities = JSON.parse(localStorage.getItem("Authorities")).map(val => val.authority);
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            params => {
                this.id = +this.activatedRoute.snapshot.paramMap.get('id');
                this.bookService.getById(this.id).subscribe(response => {
                    this.book = response;
		            this.loadBookReviews();
                    this.LoadUserBook();
                }, error => this.router.navigate(['/error']));
            });
        if (this.username) {
            this.bookService.getByUserPreferences(this.username).subscribe(
                response => {
                    this.bookRecomendations = response;
                }
            );
        }
    }

    LoadUserBook() {
        if (this.username === null) {
            this.inUserLibrary = false;
            this.inFavourites = false;
        } else {
            this.userBook.username = this.username;
            this.userBook.bookId = this.book.id;
            this.bookService.checkInLibrary(this.userBook).subscribe(
                response => this.inUserLibrary = (<boolean>response),
                error => this.router.navigate(['/error'])
            );
            this.bookService.checkInFavourites(this.userBook).subscribe(
                response => this.inFavourites = (<boolean>response),
                error => this.router.navigate(['error'])
            );
    	}
    }

    loadBookReviews() {
        this.reviewService.getPublishedByBookId(this.book.id, 1).subscribe(
            response => {
                this.publishedReviews = response;
            }
        );
        if (this.authorities.indexOf('REVIEW_MODERATOR') > -1) {
            this.reviewService.getUnpublishedByBookId(this.book.id, 1).subscribe(
                response => {
                    this.unpublishedReviews = response;
                }
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

    addToFavourites() {
        if (this.username === null) {
            this.router.navigate(['login']);
        } else {
            this.bookService.addToFavourites(this.userBook).subscribe(
                response => window.location.reload(),
                error => this.router.navigate(['/error'])
            );
        }
    }

    removeFromFavourites() {
        this.bookService.removeFromFavourites(this.userBook).subscribe(
            response => window.location.reload(),
            error => this.router.navigate(['/error'])
        );
    }
}
