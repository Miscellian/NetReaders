import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BookviewComponent} from './books/bookview/bookview.component';
import {BooklistComponent} from './books/booklist/booklist.component';
import {UserpageComponent} from './userpage/userpage.component';
import {ErrorpageComponent} from './errorpage/errorpage.component';
import {HomepageComponent} from "./homepage/homepage.component";


const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'home', component: HomepageComponent},
    {path: 'users/:id', component: UserpageComponent},
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
    {path: 'error', component: ErrorpageComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
