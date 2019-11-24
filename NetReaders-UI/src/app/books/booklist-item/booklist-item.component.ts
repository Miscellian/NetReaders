import {Component, Input, OnInit} from '@angular/core';
import {Author, Book, Genre} from '../../model';

@Component({
    selector: 'app-booklist-item',
    templateUrl: './booklist-item.component.html',
    styleUrls: ['./booklist-item.component.css']
})
export class BooklistItemComponent implements OnInit {
    @Input() public book: Book;
    genres: Genre[];
    authors: Author[];

    constructor() {
    }

    ngOnInit() {
        this.authors = this.book.authors;
        this.genres = this.book.genres;
    }

}
