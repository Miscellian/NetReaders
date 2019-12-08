import {Component, Input, OnInit} from '@angular/core';
import {Book, User} from "../../model";
import {Router} from "@angular/router";
import {BookService} from "../../books/book.service";

@Component({
    selector: 'app-recomendations',
    templateUrl: './recomendations.component.html',
    styleUrls: ['./recomendations.component.css']
})
export class RecomendationsComponent implements OnInit {
    @Input() public user: User;
    bookRecomendations: Book[];

    constructor(private bookService: BookService,
                private router: Router) {
    }

    ngOnInit() {
        this.bookService.getByUserPreferences(this.user.username).subscribe(
            books => {
                this.bookRecomendations = books;
            }, err => this.router.navigate(['/error']));
    }

}
