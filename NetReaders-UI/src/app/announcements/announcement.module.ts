import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {NgbDropdownModule, NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
import {CalendarComponent} from './calendar/calendar.component';
import {AnnouncementDetailComponent} from './announcement-detail/announcement-detail.component';
import {CalendarModule, DateAdapter} from "angular-calendar";
import {adapterFactory} from "angular-calendar/date-adapters/date-fns";
import {FormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
    declarations: [
        CalendarComponent,
        AnnouncementDetailComponent
    ],
    imports: [
        CommonModule,
        RouterModule,
        FormsModule,
        NgbPaginationModule,
        CalendarModule.forRoot({
                provide: DateAdapter,
                useFactory: adapterFactory
            }
        ),
        NgbDropdownModule,
        BrowserAnimationsModule
    ],
    exports: [CalendarComponent]
})

export class AnnouncementModule {

}
