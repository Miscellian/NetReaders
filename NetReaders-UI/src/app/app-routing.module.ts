import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BookviewComponent } from './books/bookview/bookview.component';
import { BooklistComponent } from './books/booklist/booklist.component';
import { UserpageComponent } from './userpage/userpage.component';
import { AdminslistComponent } from './adminslist/adminslist.component';


const routes: Routes = [
  {path: '',  redirectTo: '/userpage', pathMatch: 'full' },
  {path: 'userpage', component: UserpageComponent},
  {path: 'newadmin', component: AdminslistComponent},
  {path: 'books',
    children: [
      {path: ':id', component: BookviewComponent },
      {path: 'byauthor/:id', component: BooklistComponent, data: {filter: 'author'}},
      {path: 'bygenre/:id', component: BooklistComponent, data: {filter: 'genre'}},
      {path: 'byname/:id', component: BooklistComponent, data: {filter: 'name'}}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
