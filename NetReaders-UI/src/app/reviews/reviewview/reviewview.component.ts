import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Review } from '../../model';
import {BooklistItemComponent} from '../../books/booklist-item/booklist-item.component';

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
    console.log('b');
    this.reviewService.getById(this.id).subscribe(
      response => {
        if (response.status !== 200) {
          console.log('no login');
          this.router.navigate(['/error']);
        } else {
          console.log('yay');
          this.review = response.body as Review;
        }
      }
    );
    console.log('a');
  }

}
