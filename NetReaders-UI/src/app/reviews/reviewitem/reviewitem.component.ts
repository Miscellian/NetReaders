import { Component, OnInit, Input } from '@angular/core';
import { Review } from '../../model';
import { ReviewService } from '../review.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-reviewitem',
  templateUrl: './reviewitem.component.html',
  styleUrls: ['./reviewitem.component.css']
})
export class ReviewitemComponent implements OnInit {
  @Input() public review: Review;
  constructor(private reviewService: ReviewService,
              private activatedRoute: ActivatedRoute,
              public router: Router) { }

  ngOnInit() {
  }

}
