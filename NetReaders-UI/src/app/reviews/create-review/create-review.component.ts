import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { BookDto } from '../../model';
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
  book: BookDto;
  id: number;

  constructor(private formBuilder: FormBuilder,
              private bookService: BookService,
              private reviewService: ReviewService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    console.log('b');
    this.id = +this.activatedRoute.snapshot.paramMap.get('bookid');
    this.reviewForm = this.formBuilder.group({
      rating: '',
      description: ''
    });
    this.bookService.getById(this.id).subscribe(
      response => {
        if (response.isSuccessful) {
          this.book = response.obj;
        } else {
          this.router.navigateByUrl('http://localhost:8080/error');
        }
      }
    );
    console.log('a');
  }

  onSubmit() {
    this.reviewService.createReview(this.reviewForm, this.book).subscribe(
      response => {
        if (response.status !== 200) {
          this.router.navigateByUrl('http://localhost:8080/error');
        } else {
          alert('Success');
        }
      }
    );
  }

}
