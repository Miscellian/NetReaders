import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookviewComponent } from './bookview/bookview.component';
import { BooklistComponent } from './booklist/booklist.component';
import { BooklistItemComponent } from './booklist-item/booklist-item.component';



@NgModule({
  declarations: [
    BookviewComponent,
    BooklistComponent,
    BooklistItemComponent
  ],
  imports: [
    CommonModule
  ]
})
export class BooksModule { }
