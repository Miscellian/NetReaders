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
        }
    };

    funcsCount: any = {
        genre: () => {
            this.func = () => this.bookService.getCountByGenre(this.arg)
        },
        author: () => {
            this.func = () => this.bookService.getCountByAuthor(this.arg);
        },
        name: () => {
            this.func = () => this.bookService.getCountByName(this.arg)
        },
        range: () => {
            this.func = () => this.bookService.getCount();
        }
    }

    page: number;
    arg: string;
    paramArgs: any;
    count: number;
    books: Book[];
    func: any = () => this.bookService.getByRange(this.page.toString());

    constructor(private bookService: BookService,
                private activatedRoute: ActivatedRoute,
                public router: Router) {
    }

    loadPage(page: number) {
        let newPageUrl = ""
        let urlSplitted = this.router.url.split('/');
        for (let i = 0; i < urlSplitted.length - 1; i++) {
            newPageUrl += urlSplitted[i] + "/";
        }

        this.router.navigateByUrl(`${newPageUrl}${this.page}`);
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            params => {
                this.arg = params['id'];
                this.page = params['page'];
                this.funcsCount[this.activatedRoute.snapshot.data.filter]();
                this.func().subscribe(response => {
                    if (response.status != 200) {
                        // Maybe create multiple error pages for different http codes
                        console.log("Error", response.status)
                        this.router.navigate(['/error']);
                    } else {
                        this.count = +response.obj;
                    }
                });
                this.funcs[this.activatedRoute.snapshot.data.filter]();
                this.func().subscribe(response => {
                    if (response.status != 200) {
                        // Maybe create multiple error pages for different http codes
                        console.log("Error", response.status)
                        this.router.navigate(['/error']);
                    } else {
                        this.books = response.body;
                    }
                });
            }
        );
    }
}
