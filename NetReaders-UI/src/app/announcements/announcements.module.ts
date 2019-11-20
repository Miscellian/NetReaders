import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AnnouncementListComponent} from './announcement-list/announcement-list.component';
import {AnnouncementItemComponent} from './announcement-item/announcement-item.component';
import {AnnouncementDetailComponent} from './announcement-detail/announcement-detail.component';


@NgModule({
    declarations: [
        AnnouncementListComponent,
        AnnouncementItemComponent,
        AnnouncementDetailComponent
    ],
    exports: [
        AnnouncementListComponent
    ],
    imports: [
        CommonModule
    ]
})
export class AnnouncementsModule {
}
