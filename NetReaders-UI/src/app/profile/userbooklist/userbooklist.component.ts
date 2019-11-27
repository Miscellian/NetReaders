import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-userbooklist',
    templateUrl: './userbooklist.component.html',
    styleUrls: ['./userbooklist.component.css']
})
export class UserbooklistComponent implements OnInit {

    arg: string;

    constructor(private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            params => {
                this.arg = params['username'];
                this.userService.getByUsername(this.arg).subscribe(response => {
                    if (!response.isSuccessful) {
                        this.router.navigate(['/error']);
                    } else {
                        this.user = response.obj;
                    }
                })
            }
        );
    }

}
