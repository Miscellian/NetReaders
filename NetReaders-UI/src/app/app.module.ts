import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './navbar/navbar.component';
import {UserpageComponent} from './profile/userpage/userpage.component';
import {SignupComponent} from './signup/signup.component';
import {CommonModule} from '@angular/common';
import {BooksModule} from './books/books.module';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from './_helpers/auth.guard';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ErrorpageComponent} from './errorpage/errorpage.component';
import {HomepageComponent} from './homepage/homepage.component';
import {UserbooklistComponent} from './profile/userbooklist/userbooklist.component';
import {ProfileComponent} from './profile/profile.component';
import {AuthenticationService} from './login/authentication.service';
import {JwtInterceptor} from './_helpers/jwt.interceptor';
import {ConfirmUserComponent} from './confirmUser/confirmUser.component';
import {EditProfileComponent} from './profile/edit-profile/edit-profile.component';
import {AdminslistComponent} from './profile/adminslist/adminslist.component';
import {ModeratorslistComponent} from './profile/moderatorslist/moderatorslist.component';
import {ApiInterceptor} from './_helpers/api.interceptor';
import {ReviewsModule} from './reviews/reviews.module';
import {CalendarComponent} from "./calendar/calendar.component";
import {UserService} from "./profile/user.service";

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent,
        UserpageComponent,
        ErrorpageComponent,
        SignupComponent,
        LoginComponent,
        HomepageComponent,
        UserbooklistComponent,
        ProfileComponent,
        ConfirmUserComponent,
        CalendarComponent,
        EditProfileComponent,
        AdminslistComponent,
        ModeratorslistComponent
    ],
    imports: [
        ReactiveFormsModule,
        FormsModule,
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BooksModule,
        CommonModule,
        NgbModule,
        ReviewsModule
    ],
    providers: [
        AuthGuard,
        AuthenticationService,
        UserService,
        {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
        // { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
        {provide: HTTP_INTERCEPTORS, useClass: ApiInterceptor, multi: true}
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
