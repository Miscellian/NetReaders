import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BookviewComponent} from './books/bookview/bookview.component';
import {BooklistComponent} from './books/booklist/booklist.component';
import {UserpageComponent} from './userpage/userpage.component';
import {AdminslistComponent} from './adminslist/adminslist.component';
import {AnnouncementListComponent} from "./announcements/announcement-list/announcement-list.component";
import {AnnouncementDetailComponent} from "./announcements/announcement-detail/announcement-detail.component";


const routes: Routes = [
    {path: '', redirectTo: '/userpage', pathMatch: 'full'},
    {path: 'userpage', component: UserpageComponent},
    {path: 'newadmin', component: AdminslistComponent},
    {
        path: 'books',
        children: [
            {path: ':id', component: BookviewComponent},
            {path: 'byauthor/:id/:page', component: BooklistComponent, data: {filter: 'author'}},
            {path: 'bygenre/:id/:page', component: BooklistComponent, data: {filter: 'genre'}},
            {path: 'byname/:id/:page', component: BooklistComponent, data: {filter: 'name'}},
            {path: 'range/:page', component: BooklistComponent, data: {filter: 'range'}}
        ]
    },
    {
        path: 'announcements',
        children: [
            {path: ':id', component: AnnouncementDetailComponent},
            {path: 'byauthor/:id', component: AnnouncementListComponent, data: {filter: 'author'}},
            {path: 'bygenre/:id', component: AnnouncementListComponent, data: {filter: 'genre'}},
            {path: '**', component: AnnouncementListComponent, data: {filter: 'all'}}
        ];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
