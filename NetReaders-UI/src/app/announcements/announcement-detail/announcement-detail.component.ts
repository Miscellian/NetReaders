import {Component, OnInit} from '@angular/core';
import {Announcement} from "../../model";
import {AnnouncementService} from "../announcement.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'app-announcement-detail',
    templateUrl: './announcement-detail.component.html',
    styleUrls: ['./announcement-detail.component.css']
})
export class AnnouncementDetailComponent implements OnInit {

    id: number;
    announcement: Announcement;
    func: any = () => this.announcementService.getById(this.id);

    constructor(private announcementService: AnnouncementService,
                private activatedRoute: ActivatedRoute,
                public router: Router) {
    }

    ngOnInit() {
        this.id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
        this.func().subscribe(response => {
            if (response.status != 200) {
                // Maybe create multiple error pages for different http codes
                console.log("Error", response.status);
                this.router.navigate(['/error']);
            } else {
                this.announcement = response.body;
            }
        });
    }
}
