import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {addDays, addMonths, addWeeks, isSameDay, isSameMonth, subDays, subMonths, subWeeks} from 'date-fns';
import {CalendarEvent, CalendarView} from 'angular-calendar';
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

type CalendarPeriod = 'day' | 'week' | 'month';

function addPeriod(period: CalendarPeriod, date: Date, amount: number): Date {
    return {
        day: addDays,
        week: addWeeks,
        month: addMonths
    }[period](date, amount);
}

function subPeriod(period: CalendarPeriod, date: Date, amount: number): Date {
    return {
        day: subDays,
        week: subWeeks,
        month: subMonths
    }[period](date, amount);
}

@Component({
    selector: 'app-announcement-calendar',
    styleUrls: ['calendar.component.css'],
    templateUrl: 'calendar.component.html',
    encapsulation: ViewEncapsulation.None,
})
export class CalendarComponent implements OnInit {

    //Frontend (angular calendar)
    view: CalendarView = CalendarView.Month;
    CalendarView = CalendarView;
    viewDate: Date;
    events: CalendarEvent[] = [];
    activeDayIsOpen: boolean = false;

    //Backend
    announcements: Announcement[];
    id: number;
    year: number;
    month: number;
    filter: string;

    // Current date by default
    func: any = () => this.announcementService.getAll(this.viewDate.getFullYear(), this.viewDate.getMonth() + 1);

    // or factory if had params
    funcs: any = {
        bygenre: () => {
            this.func = () => this.announcementService.getByGenre(this.id, this.year, this.month);
        },
        byauthor: () => {
            this.func = () => this.announcementService.getByAuthor(this.id, this.year, this.month);
        },
        all: () => {
            this.func = () => this.announcementService.getAll(this.year, this.month);
        },
    };

    constructor(private announcementService: AnnouncementService,
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

    increment(): void {
        this.changedDate(addPeriod(this.view, this.viewDate, 1));
    }

    decrement(): void {
        this.changedDate(subPeriod(this.view, this.viewDate, 1));
    }

    today(): void {
        this.changedDate(new Date());
    }

    changedDate(date: Date): void {
        if (this.filter === "all") {
            this.router.navigate(["/announcements/", this.filter, date.getFullYear(), date.getMonth() + 1]);

        } else {
            this.router.navigate(["/announcements/", this.filter, this.id, date.getFullYear(), date.getMonth() + 1]);
        }
    }

    newRequest(): void {
        this.activatedRoute.params.subscribe(
            params => {
                this.id = params['id'];
                this.year = params['year'];
                this.month = params['month'];
                this.filter = this.activatedRoute.snapshot.data.filter;
                this.funcs[this.filter]();
                this.func().subscribe(response => {
                    this.announcements = response;
                    this.fetchEvents();
                }, () => this.router.navigate(['/error']));
            }
        );
    }

    fetchEvents(): void {
        let temp = [];
        for (let i = 0; i < this.announcements.length; i++) {
            temp.push(
                {
                    start: new Date(this.announcements[i].announcement_date),
                    title: this.announcements[i].description,
                    id: this.announcements[i].id,
                    color: colors.blue
                }
            )
        }
        this.events = temp;
    }

    eventClicked(event: CalendarEvent<Announcement>): void {
        this.router.navigate(['/announcements', event.id]);
    }

    setUpCalendar(newDate: Date) {

        this.viewDate = new Date(newDate.getFullYear(), newDate.getMonth())

    }

    ngOnInit(): void {

        this.newRequest();

        this.setUpCalendar(new Date());
    }
}
