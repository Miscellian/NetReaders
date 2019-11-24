import {Component, OnInit} from '@angular/core';
import {AnnouncementService} from '../announcement.service';
import {ActivatedRoute, Router} from "@angular/router";
import {Announcement} from "../../model";

@Component({
    selector: 'app-announcement-list',
    templateUrl: './announcement-list.component.html',
    styleUrls: ['./announcement-list.component.css']
})
export class AnnouncementListComponent implements OnInit {

    funcs: any = {
        genre: () => {
            this.func = () => this.announcementService.getByGenre(this.arg);
        },
        author: () => {
            this.func = () => this.announcementService.getByAuthor(this.arg);
        },
        all: () => {
            this.func = () => this.announcementService.getAll();
        }
    };
    arg: string;
    announcements: Announcement[];
    func: any = () => this.announcementService.getByAuthor(this.arg);

    constructor(private announcementService: AnnouncementService,
                private activatedRoute: ActivatedRoute,
                public router: Router) {
    }

    ngOnInit() {
        this.arg = this.activatedRoute.snapshot.paramMap.get('id');
        this.funcs[this.activatedRoute.snapshot.data.filter]();
        this.func().subscribe(response => {
            if (!response.isSuccessful) {
                this.router.navigate(['/error']);
            } else {
                this.announcements = response.obj;
            }
        });
    }

}
