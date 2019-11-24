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
            if (!response.isSuccessful) {
                this.router.navigate(['/error']);
            } else {
                this.book = response.obj;
                this.genres = response.obj.genres;
                this.authors = response.obj.authors;
            }
        });
    }
}
