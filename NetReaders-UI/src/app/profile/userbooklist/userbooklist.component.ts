import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Book, User} from '../../model';
import {BookService} from '../../books/book.service';

@Component({
    selector: 'app-userbooklist',
    templateUrl: './userbooklist.component.html',
    styleUrls: ['./userbooklist.component.css']
})
export class UserbooklistComponent implements OnInit {
    @Input() public user: User;
    arg: string;
    books: Book[];
    favourites: Book[];
    toReadList: Book[];

    constructor(private activatedRoute: ActivatedRoute,
                private bookService: BookService,
                public router: Router) {
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            params => {
                this.arg = params.username;
                this.bookService.getByUser(this.arg, '1').subscribe(
                    response => this.books = response,
                    () => this.router.navigate(['/error']));
                this.bookService.getFavouritesByUser(this.arg, '1').subscribe(
                    response => this.favourites = response,
                    () => this.router.navigate(['/error']));
                this.bookService.getToReadListByUser(this.arg, '1').subscribe(
                    response => this.toReadList = response,
                    () => this.router.navigate(['/error']));
            }
        );
    }

}
