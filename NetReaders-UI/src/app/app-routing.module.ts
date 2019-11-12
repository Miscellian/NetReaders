import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BookviewComponent } from './books/bookview/bookview.component';


const routes: Routes = [
  {path: 'books/:id', component: BookviewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
