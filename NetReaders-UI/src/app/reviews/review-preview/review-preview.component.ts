import { Component, OnInit, Input } from '@angular/core';
import { Review } from '../../model';
import { ReviewService } from '../review.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-review-preview',
  templateUrl: './review-preview.component.html',
  styleUrls: ['./review-preview.component.css']
})
export class ReviewPreviewComponent implements OnInit {
  @Input() review: Review;
  constructor(public router: Router) { }

  ngOnInit() {
  }

}
