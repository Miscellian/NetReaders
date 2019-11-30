import {Component, OnInit} from '@angular/core';

import {Router} from '@angular/router';
import {NavbarService} from './navbar.service';
import {Genre} from '../model';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

    public navbarCollapsed = true;

    genres: Genre[];

    constructor(private navbarService: NavbarService,
                public router: Router) {
    }

    ngOnInit() {
        this.navbarService.getAllGenres().subscribe(
            response => {
                this.genres = response;
            }, error => this.router.navigate(['/error']));
    }

    onEnter(bookname: string) {
        if (!bookname) {
            this.router.navigate(['/books/range/1']);
            return;
        }
        this.router.navigate([`/books/byname/${bookname}/1`]);
    }

    profileOfLogin() {
        if (localStorage.getItem('UserName') === null) {
            this.router.navigate(['login']);
        } else {
            let username = localStorage.getItem('UserName');
            this.router.navigateByUrl('/users/' + username);
        }
    }

}
