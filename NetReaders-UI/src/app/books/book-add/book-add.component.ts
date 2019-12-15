import { Component, OnInit } from '@angular/core';
import { Book, Genre, Author } from '../../model';
import { BookService } from '../book.service';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, FormControl, FormArray } from '@angular/forms';

@Component({
  selector: 'app-book-add',
  templateUrl: './book-add.component.html',
  styleUrls: ['./book-add.component.css']
})
export class BookAddComponent implements OnInit {
  bookForm: FormGroup;
  constructor(private bookService: BookService,
              private formBuilder: FormBuilder,
              public router: Router) {
  }

  get genres() {
    return this.bookForm.get('genres') as FormArray;
  }

  addGenre() {
    this.genres.push(this.formBuilder.control(''));
  }

  removeGenre(index) {
    this.genres.removeAt(index);
  }

  get authors() {
    return this.bookForm.get('authors') as FormArray;
  }

  addAuthor() {
    this.authors.push(this.formBuilder.control(''));
  }
  removeAuthor(index) {
    this.authors.removeAt(index);
  }

  ngOnInit() {
    this.bookForm = this.formBuilder.group({
      title: [''],
      description: [''],
      release_date: [''],
      book_language: [''],
      genres: this.formBuilder.array([]),
      authors: this.formBuilder.array([])
    });
  }

  onSubmit() {
    const book: Book = {
      title : this.bookForm.value['title'],
      description: this.bookForm.value['description'],
      release_date: this.bookForm.value['release_date'],
      book_language: this.bookForm.value['book_language'],
      genres: this.genres.value
        .map(genre => {
          return {name: genre};
        }),
      authors: this.authors.value
        .map(author => {
          return {name: author};
        })} as Book;
    this.bookService.createBook(book).subscribe(
      () => this.router.navigate(['/books/byrange/1']),
      () => this.router.navigate(['/error'])
    );
  }

}
