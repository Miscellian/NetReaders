import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Book } from '../../model';
import { BookService } from '../../books/book.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ReviewService } from '../review.service';

@Component({
  selector: 'app-create-review',
  templateUrl: './create-review.component.html',
  styleUrls: ['./create-review.component.css']
})
export class CreateReviewComponent implements OnInit {
  reviewForm: FormGroup;
  book: Book;
  id: number;

  constructor(private formBuilder: FormBuilder,
              private bookService: BookService,
              private reviewService: ReviewService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.id = +this.activatedRoute.snapshot.paramMap.get('bookid');
    this.reviewForm = this.formBuilder.group({
      rating: '',
      description: ''
    });
    this.bookService.getById(this.id).subscribe(
      response => {
          this.book = response;
      }
    );
  }

  onSubmit() {
    this.reviewService.createReview(this.reviewForm, this.book).subscribe(
      response => {
          alert('Success');
      }, error => this.router.navigate(['/error']));
  }

}
