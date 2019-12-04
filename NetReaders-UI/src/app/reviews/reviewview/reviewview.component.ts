import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Review, Authority } from '../../model';
import {BooklistItemComponent} from '../../books/booklist-item/booklist-item.component';
import { HttpResponse, HttpResponseBase } from '@angular/common/http';

@Component({
  selector: 'app-reviewview',
  templateUrl: './reviewview.component.html',
  styleUrls: ['./reviewview.component.css']
})
export class ReviewviewComponent implements OnInit {
  id: number;
  review: Review;
  constructor(private reviewService: ReviewService,
              private activatedRoute: ActivatedRoute,
              public router: Router) { }

  ngOnInit() {
    this.id = +this.activatedRoute.snapshot.paramMap.get('id');
    this.reviewService.getById(this.id).subscribe(
      response => {
          this.review = response.body as Review;
      }
    );
  }

  isReviewModerator(): boolean {
    const authorities: Authority[]  = JSON.parse(localStorage.getItem('Authorities'));
    if(!authorities) return false;
    return authorities
    .map((val,index,arr) => val.authority)
    .includes('REVIEW_MODERATOR');
  }

  onPublish() {
    this.reviewService.publishReview(this.review).subscribe(
      response => {
          alert('Updated successfuly!');
      }, error => this.router.navigate(['/error']))
  }
}
