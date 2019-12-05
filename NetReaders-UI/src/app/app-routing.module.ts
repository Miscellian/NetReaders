import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BookviewComponent} from './books/bookview/bookview.component';
import {BooklistComponent} from './books/booklist/booklist.component';
import {SignupComponent} from './signup/signup.component';
import {ErrorpageComponent} from './errorpage/errorpage.component';
import {LoginComponent} from './login/login.component';
import {ConfirmUserComponent} from './confirmUser/confirmUser.component';
import {ReviewviewComponent} from './reviews/reviewview/reviewview.component';
import {CreateReviewComponent} from './reviews/create-review/create-review.component';
import {EditProfileComponent} from "./profile/edit-profile/edit-profile.component";
import {HomepageComponent} from './homepage/homepage.component';
import {ProfileComponent} from './profile/profile.component';
import {ReviewlistComponent} from './reviews/reviewlist/reviewlist.component';
import {AnnouncementDetailComponent} from "./announcements/announcement-detail/announcement-detail.component";
import {CalendarComponent} from "./announcements/calendar/calendar.component";


const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'home', component: HomepageComponent},
    {path: 'users/:username', component: ProfileComponent, pathMatch: 'full'},
    {path: 'users/editProfile/:username', component: EditProfileComponent},
    {path: 'signup', component: SignupComponent},
    {path: 'login', component: LoginComponent},
    {path: 'confirmRegistration', component: ConfirmUserComponent},
    {
        path: 'books',
        children: [
            {path: ':id', component: BookviewComponent},
            {path: 'byauthor/:id/:page', component: BooklistComponent, data: {filter: 'author'}},
            {path: 'bygenre/:id/:page', component: BooklistComponent, data: {filter: 'genre'}},
            {path: 'byname/:id/:page', component: BooklistComponent, data: {filter: 'name'}},
            {path: 'range/:page', component: BooklistComponent, data: {filter: 'range'}},
            {path: 'byuser/:id/:page', component: BooklistComponent, data: {filter: 'user'}},
            {path: 'byuserFavourites/:id/:page', component: BooklistComponent, data: {filter: 'favourite'}}
        ]
    },
    {
        path: 'reviews',
        children: [
            {path: 'add/:bookid', component: CreateReviewComponent},
            {path: 'published/:bookid/:page', component: ReviewlistComponent, data: {filter: 'publishedByBook'}},
            {path: 'unpublished/:bookid/:page', component: ReviewlistComponent, data: {filter: 'unpublishedByBook'}},
            {path: 'unpublished/:page', component: ReviewlistComponent, data: {filter: 'unpublishedAll'}},
            {path: ':id', component: ReviewviewComponent}
        ]
    },
    {
        path: 'announcements',
        children: [
            {path: ':id', component: AnnouncementDetailComponent},
            {path: 'byauthor/:id/:year/:month', component: CalendarComponent, data: {filter: 'author'}},
            {path: 'bygenre/:id/:year/:month', component: CalendarComponent, data: {filter: 'genre'}},
            {path: 'all/:year/:month', component: CalendarComponent, data: {filter: 'all'}},
        ]
    },
    {path: 'error', component: ErrorpageComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
