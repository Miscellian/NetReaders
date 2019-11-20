import {Component, OnInit} from '@angular/core';
import {AnnouncementDto} from "../../model";
import {AnnouncementService} from "../announcement.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'app-announcement-detail',
    templateUrl: './announcement-detail.component.html',
    styleUrls: ['./announcement-detail.component.css']
})
export class AnnouncementDetailComponent implements OnInit {

    id: number;
    announcementDto: AnnouncementDto;
    func: any = () => this.announcementService.getById(this.id);

    constructor(private announcementService: AnnouncementService,
                private activatedRoute: ActivatedRoute,
                public router: Router) {
    }

    ngOnInit() {
        this.id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
        this.func().subscribe(response => {
            if (!response.isSuccessful) {
                this.router.navigate(['/error']);
            } else {
                this.announcementDto = response.obj;
            }
        });
    }
}
