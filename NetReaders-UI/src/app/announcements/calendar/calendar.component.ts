import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {addDays, addMonths, addWeeks, isSameDay, isSameMonth, subDays, subMonths, subWeeks} from 'date-fns';
import {CalendarEvent, CalendarView} from 'angular-calendar';
import {ActivatedRoute, Router} from "@angular/router";
import {AnnouncementService} from "../announcement.service";
import {Announcement} from "../../model";
import {Subscription} from "rxjs";

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
export class CalendarComponent implements OnInit, OnDestroy {

    view: CalendarView = CalendarView.Month;
    CalendarView = CalendarView;
    viewDate: Date;
    events: CalendarEvent[] = [];
    activeDayIsOpen: boolean = false;

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

    private subscription: Subscription = new Subscription();

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

    onNextDayClicked(): void {
        this.changedDate(addPeriod(this.view, this.viewDate, 0));
    }

    onPrevDayClicked(): void {
        this.changedDate(subPeriod(this.view, this.viewDate, 0));
    }

    onTodayDayClicked(): void {
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
        this.subscription.add(this.activatedRoute.params.subscribe(
            params => {
                this.id = params['id'];
                this.year = params['year'];
                this.month = params['month'];
                this.filter = this.activatedRoute.snapshot.data.filter;
                this.funcs[this.filter]();

                this.subscription.add(this.func().subscribe(response => {
                    this.announcements = response;
                    this.fetchEvents();
                }, () => this.router.navigate(['/error'])));
            },
            () => this.router.navigate(['/error'])
        ));
    }

    fetchEvents(): void {
        let temp = [];
        for (let announcement of this.announcements) {
            temp.push(
                {
                    start: new Date(announcement.announcement_date),
                    title: announcement.description,
                    id: announcement.id,
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

    ngOnDestroy(): void {

        this.subscription.unsubscribe();

    }
}
