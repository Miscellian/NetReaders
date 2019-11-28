import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewitemComponent } from './reviewitem/reviewitem.component';
import { ReviewviewComponent } from './reviewview/reviewview.component';
import { ReviewlistComponent } from './reviewlist/reviewlist.component';
import { RouterModule } from '@angular/router';
import { NgbPagination, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { BooksModule } from '../books/books.module';
import { CreateReviewComponent } from './create-review/create-review.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    ReviewitemComponent,
    ReviewviewComponent,
    ReviewlistComponent,
    CreateReviewComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    BooksModule,
    NgbPaginationModule,
    ReactiveFormsModule
  ]
})
export class ReviewsModule { }
