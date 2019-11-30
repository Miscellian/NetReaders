import { Component, OnInit, Input } from '@angular/core';
import { Book, Genre, Author } from '../../model';

@Component({
  selector: 'app-booklist-item',
  templateUrl: './booklist-item.component.html',
  styleUrls: ['./booklist-item.component.css']
})
export class BooklistItemComponent implements OnInit {
  @Input() public book: Book;
  constructor() { }

  ngOnInit() {
  }

}
