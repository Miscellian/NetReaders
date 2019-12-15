import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-homepage',
    templateUrl: './homepage.component.html',
    styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
    storageUsername: string;

    constructor() {
        this.storageUsername = localStorage.getItem('UserName');
    }

    ngOnInit() {
    }

}
