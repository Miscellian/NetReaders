import {Component, OnInit} from '@angular/core';
import {Author, Book, Genre} from '../../model';
import {BookService} from '../book.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    selector: 'app-bookview',
    templateUrl: './bookview.component.html',
    styleUrls: ['./bookview.component.css']
})
export class BookviewComponent implements OnInit {
    id: number;
    book: Book;
    genres: Genre[];
    authors: Author[];

    constructor(private bookService: BookService,
                private activatedRoute: ActivatedRoute,
                public router: Router) {
    }

    ngOnInit() {
        this.id = +this.activatedRoute.snapshot.paramMap.get('id');
        this.bookService.getById(this.id).subscribe(response => {
            if (response.status != 200) {
                // Maybe create multiple error pages for different http codes
                console.log("Error", response.status)
                this.router.navigate(['/error']);
            } else {
                this.book = response.body;
                this.genres = response.body.genres;
                this.authors = response.body.authors;
            }
        });
    }
}
