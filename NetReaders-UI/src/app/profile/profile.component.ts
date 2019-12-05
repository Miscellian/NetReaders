import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "./user.service";
import {Authority, User, Book} from "../model";
import { BookService } from '../books/book.service';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

    user: User;
    arg: string;
    storageUsername: string;
    bookRecomendations: Book[];

    constructor(private activatedRoute: ActivatedRoute,
                private userService: UserService,
                private bookService: BookService,
                public router: Router) {
        this.storageUsername = localStorage.getItem("UserName");
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            params => {
                this.arg = params['username'];
                this.userService.getByUsername(this.arg).subscribe(
                    response => {
                        this.user = response;
                        if (this.hasAuthority('USER')) {
                            this.loadSuggestions();
                        }
                    },
                    error => this.router.navigate(['/error'])
                );
                
            }
        );
    }

    loadSuggestions() {
        this.bookService.getByUserPreferences(this.user.username).subscribe(
            books => {
                this.bookRecomendations = books;
            },err => this.router.navigate(['/error']));
    }

    hasAuthority(authority: string): boolean {
        const authorities: Authority[] = JSON.parse(localStorage.getItem('Authorities'));
        if (!authorities) return false;
        return authorities
            .map(val => val.authority)
            .includes(authority);
    }
}
