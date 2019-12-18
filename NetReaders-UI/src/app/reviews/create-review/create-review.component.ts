import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Book } from '../../model';
import { BookService } from '../../books/book.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ReviewService } from '../review.service';
import { isNull } from 'util';

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
      rating: ['0', [Validators.required, Validators.pattern('^[0-9]*$'), Validators.max(100), Validators.min(0)]],
      description: ['', [Validators.required]]
    });
    this.bookService.getById(this.id).subscribe(
      response => {
          this.book = response;
      }
    );
  }

  onSubmit() {
    if (this.reviewForm.invalid) {
      return;
    }
    const rating = this.reviewForm.value['rating'];
    if (isNull(rating)) {
      alert('Invalid rating');
      return;
    }
    if (+rating > 100 || +rating < 0) {
      alert('Rating should be between 0 and 100');
      return;
    }
    this.reviewService.createReview(this.reviewForm, this.book).subscribe(
      response => {
          this.router.navigate([`/books/${this.book.id}`]);
      }, error => this.router.navigate(['/error']));
  }

}
