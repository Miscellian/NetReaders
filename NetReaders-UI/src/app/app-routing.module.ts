import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BookviewComponent } from './books/bookview/bookview.component';
import { BooklistComponent } from './books/booklist/booklist.component';
import { UserpageComponent } from './userpage/userpage.component';
import { AdminslistComponent } from './adminslist/adminslist.component';
import { SignupComponent } from './signup/signup.component';
import { ErrorpageComponent } from './errorpage/errorpage.component';
import { LoginComponent } from './login/login.component';
import { ConfirmUserComponent } from './confirmUser/confirmUser.component';
import { ReviewviewComponent } from './reviews/reviewview/reviewview.component';
import { CreateReviewComponent } from './reviews/create-review/create-review.component';


const routes: Routes = [
  { path: '', redirectTo: '/userpage', pathMatch: 'full' },
  { path: 'error', component: ErrorpageComponent },
  { path: 'userpage', component: UserpageComponent },
  { path: 'newadmin', component: AdminslistComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'login', component: LoginComponent },
  { path: 'confirmRegistration', component: ConfirmUserComponent },
  {
    path: 'books',
    children: [
      { path: ':id', component: BookviewComponent },
      { path: 'byauthor/:id/:page', component: BooklistComponent, data: { filter: 'author' } },
      { path: 'bygenre/:id/:page', component: BooklistComponent, data: { filter: 'genre' } },
      { path: 'byname/:id/:page', component: BooklistComponent, data: { filter: 'name' } },
      { path: 'range/:page', component: BooklistComponent, data: { filter: 'range' } }
    ]
  },
  {
    path: 'reviews',
    children: [
      { path: 'add/:bookid', component: CreateReviewComponent},
      { path: ':id', component: ReviewviewComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
