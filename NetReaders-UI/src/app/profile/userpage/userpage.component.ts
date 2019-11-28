import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../model";

@Component({
    selector: 'app-userpage',
    templateUrl: './userpage.component.html',
    styleUrls: ['./userpage.component.css']
})
export class UserpageComponent implements OnInit {
    @Input() public user: User;
    arg: string;

    constructor() {
    }

    ngOnInit() {
    }

}
