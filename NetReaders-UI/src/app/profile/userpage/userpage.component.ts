import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../model";
import {AuthenticationService} from "../../login/authentication.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-userpage',
    templateUrl: './userpage.component.html',
    styleUrls: ['./userpage.component.css']
})

export class UserpageComponent implements OnInit {
    @Input() public user: User;
    arg: string;
    storageUsername: String;

    constructor(private authentificationService: AuthenticationService,
                public router: Router) {
        this.storageUsername = localStorage.getItem("UserName");
    }

    ngOnInit() {
    }

    logout() {
        this.authentificationService.logout();
        this.router.navigate(['/login']);
    }
}
