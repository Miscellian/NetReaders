import {Component, OnInit} from '@angular/core';
import {Book, Review, UserBookLibrary} from '../../model';
import {BookService} from '../book.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ReviewService} from '../../reviews/review.service';

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
    inToReadList: boolean;
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
        this.username = localStorage.getItem('UserName');
        if (this.username) {
            this.authorities = JSON.parse(localStorage.getItem('Authorities')).map(val => val.authority);
        } else {
            this.authorities = [];
        }
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            () => {
                this.id = +this.activatedRoute.snapshot.paramMap.get('id');
                this.bookService.getById(this.id).subscribe(response => {
                    this.book = response;
                    this.loadBookReviews();
                    this.LoadUserBook();
                }, () => this.router.navigate(['/error']));
            });
    }

    LoadUserBook() {
        if (this.username === undefined || this.authorities.indexOf('USER') < 0) {
            this.inUserLibrary = false;
            this.inFavourites = false;
            this.inToReadList = false;
        } else {
            this.userBook.username = this.username;
            this.userBook.bookId = this.book.id;
            this.checkInLibrary();
            this.checkInFavourites();
            this.checkInToReadList();
        }
    }

    checkInLibrary() {
        this.bookService.checkInLibrary(this.userBook).subscribe(
            response => this.inUserLibrary = (response as boolean),
            () => this.router.navigate(['/error'])
        );
    }

    checkInFavourites() {
        this.bookService.checkInFavourites(this.userBook).subscribe(
            response => this.inFavourites = (response as boolean),
            () => this.router.navigate(['error'])
        );
    }

    checkInToReadList() {
        this.bookService.checkInToReadList(this.userBook).subscribe(
            response => this.inToReadList = (response as boolean),
            () => this.router.navigate(['error'])
        );
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

    addToFavourites() {
        if (this.username === null) {
            this.router.navigate(['login']);
        } else {
            this.bookService.addToFavourites(this.userBook).subscribe(
                () => this.checkInFavourites(),
                () => this.router.navigate(['/error'])
            );
        }
    }

    removeFromFavourites() {
        this.bookService.removeFromFavourites(this.userBook).subscribe(
            () => this.checkInFavourites(),
            () => this.router.navigate(['/error'])
        );
    }

    addToToReadList() {
        if (this.username === null) {
            this.router.navigate(['login']);
        } else {
            this.bookService.addToToReadList(this.userBook).subscribe(
                () => this.checkInToReadList(),
                () => this.router.navigate(['/error'])
            );
        }
    }

    removeFromToReadList() {
        this.bookService.removeFromToReadList(this.userBook).subscribe(
            () => this.checkInToReadList(),
            () => this.router.navigate(['/error'])
        );
    }

    addReview() {
        this.router.navigate([`/reviews/add/${this.book.id}`]);
    }

    onPublish() {
        this.bookService.publishBook(this.book.id).subscribe(
            () => this.router.navigate([`/books/unpublished`]),
            () => this.router.navigate(['/error'])
        );
    }
}
