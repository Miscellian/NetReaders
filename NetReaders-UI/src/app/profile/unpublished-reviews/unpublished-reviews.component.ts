import { Component, OnInit } from '@angular/core';
import { Review } from '../../model';
import { ActivatedRoute, Router } from '@angular/router';
import {ReviewService} from '../../reviews/review.service';

@Component({
  selector: 'app-unpublished-reviews',
  templateUrl: './unpublished-reviews.component.html',
  styleUrls: ['./unpublished-reviews.component.css']
})
export class UnpublishedReviewsComponent implements OnInit {
  arg: string;
  reviews: Review[];

  constructor(private activatedRoute: ActivatedRoute,
              private reviewService: ReviewService,
              public router: Router) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(
      params => {
          this.reviewService.getUnpublished(1).subscribe(
              response => this.reviews = response,
              error => this.router.navigate(['/error']));
      }
  );
  }

}
