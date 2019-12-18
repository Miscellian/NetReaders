import {Component, OnInit} from '@angular/core';
import {Book} from '../../model';
import {BookService} from '../book.service';
import {Router} from '@angular/router';
import {FormArray, FormBuilder, FormGroup, Validators, ValidatorFn, ValidationErrors} from '@angular/forms';

@Component({
  selector: 'app-book-add',
  templateUrl: './book-add.component.html',
  styleUrls: ['./book-add.component.css']
})
export class BookAddComponent implements OnInit {
  bookForm: FormGroup;

  constructor(private bookService: BookService,
              private formBuilder: FormBuilder,
              private router: Router) {
  }

  get genres() {
    return this.bookForm.get('genres') as FormArray;
  }

  addGenre() {
    this.genres.push(this.formBuilder.control('', Validators.required));
  }

  removeGenre(index) {
    this.genres.removeAt(index);
  }

  get authors() {
    return this.bookForm.get('authors') as FormArray;
  }

  addAuthor() {
    this.authors.push(this.formBuilder.control('', Validators.required));
  }
  removeAuthor(index) {
    this.authors.removeAt(index);
  }

  authorsAndGenresProvided: ValidatorFn = (group: FormGroup): ValidationErrors | null => {
    const genresExist = group.value.genres.length > 0;
    const authorsExist = group.value.authors.length > 0;
    return genresExist && authorsExist ? null : {'noGenresOrAuthors': true};
  }

  ngOnInit() {
    this.bookForm = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      release_date: ['', Validators.required],
      book_language: ['', Validators.required],
      genres: this.formBuilder.array([]),
      authors: this.formBuilder.array([])
    }, {validators: this.authorsAndGenresProvided});
  }

  onSubmit() {
    if (this.bookForm.invalid) {
      return;
    }
    const book: Book = {
      title: this.bookForm.value.title,
      description: this.bookForm.value.description,
      release_date: this.bookForm.value.release_date,
      book_language: this.bookForm.value.book_language,
      genres: this.genres.value
          .map(genre => {
            return {name: genre};
          }),
      authors: this.authors.value
          .map(author => {
            return {name: author};
          })
    } as Book;
    this.bookService.checkBookExists(book.title).subscribe(
      response => {
        if (response) {
          alert('A book with such title already exists');
          return;
        } else {
          this.bookService.createBook(book).subscribe(
            () => {
              window.location.reload();
              this.router.navigate(['/books/range/1']);
            },
            error => this.router.navigate(['/error'])
          );
        }
      }, () => this.router.navigate(['/error']));
  }
}
