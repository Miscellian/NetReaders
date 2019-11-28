import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewitemComponent } from './reviewitem/reviewitem.component';
import { ReviewviewComponent } from './reviewview/reviewview.component';
import { ReviewlistComponent } from './reviewlist/reviewlist.component';
import { RouterModule } from '@angular/router';
import { NgbPagination, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { BooksModule } from '../books/books.module';



@NgModule({
  declarations: [
    ReviewitemComponent,
    ReviewviewComponent,
    ReviewlistComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    BooksModule,
    NgbPaginationModule
  ]
})
export class ReviewsModule { }
