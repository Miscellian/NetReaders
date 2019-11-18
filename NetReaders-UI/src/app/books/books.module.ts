import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookviewComponent } from './bookview/bookview.component';
import { BooklistComponent } from './booklist/booklist.component';
import { BooklistItemComponent } from './booklist-item/booklist-item.component';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    BookviewComponent,
    BooklistComponent,
    BooklistItemComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class BooksModule { }
