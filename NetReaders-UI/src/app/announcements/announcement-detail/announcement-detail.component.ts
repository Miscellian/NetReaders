import {Component, OnInit} from '@angular/core';
import {Announcement} from "../../model";
import {ActivatedRoute, Router} from "@angular/router";
import {AnnouncementService} from "../announcement.service";
import {Location} from '@angular/common';

@Component({
    selector: 'app-announcement-detail',
    templateUrl: './announcement-detail.component.html',
    styleUrls: ['./announcement-detail.component.css']
})
export class AnnouncementDetailComponent implements OnInit {

    id: number;
    announcement: Announcement;

    constructor(private location: Location,
                private announcementService: AnnouncementService,
                private activatedRoute: ActivatedRoute,
                public router: Router) {
    }

    ngOnInit() {
        this.id = +this.activatedRoute.snapshot.paramMap.get('id');
        this.announcementService.getById(this.id).subscribe(response => {
            this.announcement = response;
            console.log(this.announcement);
        }, error => this.router.navigate(['/error']));
    }

    backClicked(): void {
        this.location.back();
    }
}
