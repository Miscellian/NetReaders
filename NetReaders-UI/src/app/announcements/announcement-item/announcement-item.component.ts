import {Component, Input, OnInit} from '@angular/core';
import {AnnouncementDto} from "../../model";

@Component({
    selector: 'app-announcement-item',
    templateUrl: './announcement-item.component.html',
    styleUrls: ['./announcement-item.component.css']
})
export class AnnouncementItemComponent implements OnInit {
    @Input() public announcementDto: AnnouncementDto;
    id: number;

    constructor() {
    }

    ngOnInit() {
    }

}
