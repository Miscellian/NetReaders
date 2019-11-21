import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms'

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './navbar/navbar.component';
import {UserpageComponent} from './userpage/userpage.component';
import {AdminslistComponent} from './adminslist/adminslist.component';
import {CommonModule} from '@angular/common';
import {BooksModule} from './books/books.module';
import {AnnouncementsModule} from "./announcements/announcements.module";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent,
        UserpageComponent,
        AdminslistComponent,
    ],
    imports: [
        FormsModule,
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BooksModule,
        AnnouncementsModule,
        CommonModule,
        NgbModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
