import {Component, AfterViewInit} from '@angular/core';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements AfterViewInit {

  mlist = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

  dlist = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

  currentDate: Date;
  currentYear: number;
  currentMonth: number;
  currentDay: number;

  firstDay: number;
  lastDay: number;

  tmpDate: Date;

  settings = {
    container: 'calendar',
    calendar: 'front',
    days: 'weeks',
    form: 'back',
    buttons: 'back'
  };

  constructor() {
    this.currentDate = new Date();
    this.currentYear = this.currentDate.getFullYear();
    this.currentMonth = this.currentDate.getMonth();
    this.currentDay = this.currentDate.getDate();
    this.firstDay = new Date(this.currentYear, this.currentMonth, 1).getDate();
    this.lastDay = new Date(this.currentYear, this.currentMonth, 0).getDate();
    this.tmpDate = this.getLastMonday(this.currentMonth - 1, this.currentYear);
  }

  getMonthYear() {
    return this.mlist[this.currentMonth] + ' ' + this.currentYear;
  }

  getDay() {
    const day = this.currentDay;
    let dayStr = '';
    if (day > 3 && day < 21) {
      dayStr = day + 'th';
    }
    switch (day % 10) {
      case 1:
        dayStr = day + 'st';
        break;
      case 2:
        dayStr = day + 'nd';
        break;
      case 3:
        dayStr = day + 'rd';
        break;
      default:
        dayStr = day + 'th';
        break;
    }
    return dayStr + ' ' + this.dlist[this.currentDay - 1];
  }

  setNewDay(n) {
    const copy = new Date(this.tmpDate);
    copy.setDate(copy.getDate() + n);
    return copy;
  }

  getLastMonday(month, year) {

    const d = new Date(year, month + 1, 1);

    while (d.getDay() !== 1) {
      d.setDate(d.getDate() - 1);
    }
    return d;
  }

  getDaysInMonth(month, year) {
    return new Date(year, month, 0).getDate();
  }

  ngAfterViewInit() {

    this.bindUIActions();
    const listBack = document.getElementsByClassName(this.settings.buttons);
    [].forEach.call(listBack, (el) => {
      const list = el.getElementsByTagName('button');
      [].forEach.call(list, (elList) => {
        elList.addEventListener('click', () => {
          this.swap(this.settings.form, this.settings.calendar);
        });
      });
    });
  }

  swap(currentSide, desiredSide) {
    const hide = document.getElementsByClassName(currentSide);
    [].forEach.call(hide, (el) => {
      el.style.display = 'none';
    });
    const show = document.getElementsByClassName(desiredSide);
    [].forEach.call(show, (el) => {
      el.style.display = 'block';
    });
  }

  bindUIActions() {
    const listDays = document.getElementsByClassName(this.settings.days);
    [].forEach.call(listDays, (el) => {
      const list = el.getElementsByTagName('span');
      [].forEach.call(list, (elList) => {
        elList.addEventListener('click', () => {
          this.swap(this.settings.calendar, this.settings.form);
        });
      });
    });
  }
}
