import {Component, Input, OnInit} from '@angular/core';
import {Announcement} from "../../model";

@Component({
    selector: 'app-announcement-item',
    templateUrl: './announcement-item.component.html',
    styleUrls: ['./announcement-item.component.css']
})
export class AnnouncementItemComponent implements OnInit {
    @Input() public announcement: Announcement;
    id: number;

    constructor() {
    }

    ngOnInit() {
    }

}
