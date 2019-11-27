import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BookDto, User} from "../../model";
import {BookService} from "../../books/book.service";

@Component({
    selector: 'app-userbooklist',
    templateUrl: './userbooklist.component.html',
    styleUrls: ['./userbooklist.component.css']
})
export class UserbooklistComponent implements OnInit {
    @Input() public user: User;
    arg: string;
    bookdtos: BookDto[];

    constructor(private activatedRoute: ActivatedRoute,
                private bookService: BookService,
                public router: Router) {
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            params => {
                this.arg = params['username'];
                this.bookService.getByUser(this.arg, "1").subscribe(response => {
                    if (!response.isSuccessful) {
                        this.router.navigate(['/error']);
                    } else {
                        this.bookdtos = response.obj;
                    }
                })
            }
        );
    }

}
