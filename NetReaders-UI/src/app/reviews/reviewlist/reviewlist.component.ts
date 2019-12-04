import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../review.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Review } from '../../model';

@Component({
  selector: 'app-reviewlist',
  templateUrl: './reviewlist.component.html',
  styleUrls: ['./reviewlist.component.css']
})
export class ReviewlistComponent implements OnInit
{

  reviewFunctions: any = {
    publishedByBook: () => {
      this.dataSourceFunction = () => this.reviewService.getPublishedByBookId(this.bookid, this.page);
      this.dataCountFunction = () => this.reviewService.getPublishedByBookIdCount(this.bookid);
    },
    unpublishedByBook: () => {
      this.dataSourceFunction = () => this.reviewService.getUnpublishedByBookId(this.bookid, this.page);
      this.dataCountFunction = () => this.reviewService.getUnpublishedByBookIdCount(this.bookid);
    },
    unpublishedAll: () => {
      this.dataSourceFunction = () => this.reviewService.getUnpublished(this.page);
      this.dataCountFunction = () => this.reviewService.getUnpublishedCount();
    }
  };

  dataSourceFunction: any;
  dataCountFunction: any;
  reviews: Review[];
  page: number;
  bookid: number;
  count: number;
  routeFilter: string;

  constructor(private reviewService: ReviewService,
              private activatedRoute: ActivatedRoute,
              public router: Router) { }

  loadPage() {
    let newPageUrl = '';
    let urlSplitted = this.router.url.split('/');
    for (let i = 0; i < urlSplitted.length - 1; i++) {
      newPageUrl += urlSplitted[i] + "/";
    }
    this.router.navigateByUrl(`${newPageUrl}${this.page}`);
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(
      params => {
        this.bookid = +params['bookid'];
        this.page = +params['page'];
        this.reviewFunctions[this.activatedRoute.snapshot.data.filter]();
        this.dataSourceFunction().subscribe(response => {
          this.reviews = response;
        });
        this.dataCountFunction().subscribe(response => {
          this.count = response;
        });
      }
    );
  }

}
