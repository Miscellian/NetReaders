import {Component, OnInit} from '@angular/core';
import {BookService} from '../book.service';
import {Book} from '../../model';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    selector: 'app-booklist',
    templateUrl: './booklist.component.html',
    styleUrls: ['./booklist.component.css']
})
export class BooklistComponent implements OnInit {
    page: number = 1;
    previousPage: number;

    func: any = () => this.bookService.getByRange("1");

    funcs: any = {
        genre: () => {
            this.func = () => this.bookService.getByGenre(this.arg, this.page.toString());
        },
        author: () => {
            this.func = () => this.bookService.getByAuthor(this.arg, this.page.toString());
        },
        name: () => {
            this.func = () => this.bookService.getByName(this.arg, this.page.toString());
        },
        range: () => {
            this.func = () => this.bookService.getByRange(this.page.toString());
        },
        user: () => {
            this.func = () => this.bookService.getByUser(this.arg, this.page.toString());
        },
        favourite: () => {
            this.func = () => this.bookService.getFavouritesByUser(this.arg, this.page.toString());
        },
        toReadList: () => {
            this.func = () => this.bookService.getToReadListByUser(this.arg, this.page.toString());
        }
    };
    arg: string;
    count: number;
    books: Book[];
    funcsCount: any = {
        genre: () => {
            this.func = () => this.bookService.getCountByGenre(this.arg);
        },
        author: () => {
            this.func = () => this.bookService.getCountByAuthor(this.arg);
        },
        name: () => {
            this.func = () => this.bookService.getCountByName(this.arg);
        },
        range: () => {
            this.func = () => this.bookService.getCount();
        },
        user: () => {
            this.func = () => this.bookService.getCountByUser(this.arg);
        },
        favourite: () => {
            this.func = () => this.bookService.getFavouritesCountByUser(this.arg);
        },
        toReadList: () => {
            this.func = () => this.bookService.getToReadListCountByUser(this.arg);
        }
    };

    constructor(private bookService: BookService,
                private activatedRoute: ActivatedRoute,
                public router: Router) {
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            window.scroll(0, 0);
            this.previousPage = page;
            this.page = page;
            this.loadBooks();
        }
    }

    loadBooks() {
        this.activatedRoute.params.subscribe(
            params => {
                this.arg = params['id'];
                this.funcsCount[this.activatedRoute.snapshot.data.filter]();
                this.func().subscribe(response => {
                    this.count = +response;
                }, error => this.router.navigate(['/error']));
                this.funcs[this.activatedRoute.snapshot.data.filter]();
                this.func().subscribe(response => {
                    this.books = response;
                }, error => this.router.navigate(['/error']));
            }
        );
    }

    ngOnInit() {
        this.loadBooks();
    }
}
