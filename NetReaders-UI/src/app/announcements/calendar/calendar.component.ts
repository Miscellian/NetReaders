import {Component, DoCheck, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {isSameDay, isSameMonth} from 'date-fns';
import {CalendarEvent, CalendarEventAction, CalendarView} from 'angular-calendar';
import {DOCUMENT} from '@angular/common';
import {ActivatedRoute, Router} from "@angular/router";
import {AnnouncementService} from "../announcement.service";
import {Announcement} from "../../model";

const colors: any = {
    red: {
        primary: '#ad2121',
        secondary: '#FAE3E3'
    },
    blue: {
        primary: '#1e90ff',
        secondary: '#FAE3E3'
    },
    yellow: {
        primary: '#e3bc08',
        secondary: '#FAE3E3'
    }
};

@Component({
    selector: 'app-announcement-calendar',
    styleUrls: ['calendar.component.scss'],
    templateUrl: 'calendar.component.html',
    encapsulation: ViewEncapsulation.None
})
export class CalendarComponent implements OnInit {
    //Frontend
    private readonly customTheme = 'custom_theme';
    view: CalendarView = CalendarView.Month;
    CalendarView = CalendarView;
    viewDate: Date = new Date();
    events: CalendarEvent[] = [];

    activeDayIsOpen: boolean = false;

    //Backend
    announcements: Announcement[];
    id: number;
    year: number;
    month: number;

    count: number;

    // Current date by default
    func: any = () => this.announcementService.getAll(this.viewDate.getFullYear(), this.viewDate.getMonth() + 1);

    // or factory if had params
    funcs: any = {
        genre: () => {
            this.func = () => this.announcementService.getByGenre(this.id, this.year, this.month);
        },
        author: () => {
            this.func = () => this.announcementService.getByAuthor(this.id, this.year, this.month);
        },
        all: () => {
            this.func = () => this.announcementService.getAll(this.year, this.month);
        },
    };

    constructor(@Inject(DOCUMENT) private document,
                private announcementService: AnnouncementService,
                private activatedRoute: ActivatedRoute,
                public router: Router) {
    }

    dayClicked({date, events}: { date: Date; events: CalendarEvent[] }): void {
        if (isSameMonth(date, this.viewDate)) {
            if (
                (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
                events.length === 0
            ) {
                this.activeDayIsOpen = false;
            } else {
                this.activeDayIsOpen = true;
            }
            this.viewDate = date;
        }
    }

    closeOpenMonthViewDay(): void {
        this.activeDayIsOpen = false;
    }

    fetchEvents(): void {
        for (let i = 0; i < this.announcements.length; i++) {
            this.events.push(
                {
                    start: new Date(this.announcements[i].announcement_date),
                    title: this.announcements[i].description,
                    id: this.announcements[i].id,
                    color: colors.blue
                }
            )
        }
    }

    eventClicked(event: CalendarEvent<Announcement>): void {
        this.router.navigate(['/announcements', event.id]);
    }

    ngOnInit(): void {
        this.activatedRoute.params.subscribe(
            params => {
                this.id = params['id'];
                this.year = params['year'];
                this.month = params['month'];
                this.funcs[this.activatedRoute.snapshot.data.filter]();
                this.func().subscribe(response => {
                    this.announcements = response;
                    this.fetchEvents();
                }, () => this.router.navigate(['/error']));
            }
        );
    }
}
