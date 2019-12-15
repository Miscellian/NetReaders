import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookviewComponent } from './bookview/bookview.component';
import { BooklistComponent } from './booklist/booklist.component';
import { BooklistItemComponent } from './booklist-item/booklist-item.component';
import { RouterModule } from '@angular/router';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { ReviewsModule } from '../reviews/reviews.module';
import { BookAddComponent } from './book-add/book-add.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    BookviewComponent,
    BooklistComponent,
    BooklistItemComponent,
    BookAddComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReviewsModule,
    NgbPaginationModule,
    ReactiveFormsModule
  ],
  exports: [
    BooklistItemComponent
  ]
})
export class BooksModule { }
