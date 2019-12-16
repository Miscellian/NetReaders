import {Component, OnInit} from '@angular/core';
import {Book} from '../../model';
import {ActivatedRoute, Router} from '@angular/router';
import {BookService} from '../../books/book.service';

@Component({
    selector: 'app-unpublished-books',
    templateUrl: './unpublished-books.component.html',
    styleUrls: ['./unpublished-books.component.css']
})
export class UnpublishedBooksComponent implements OnInit {
    arg: string;
    books: Book[];

    constructor(private activatedRoute: ActivatedRoute,
                private bookService: BookService,
                public router: Router) {
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            params => {
                this.bookService.getUnpublished('1').subscribe(
                    response => this.books = response,
                    error => this.router.navigate(['/error']));
            }
        );
    }
}
