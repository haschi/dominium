import { Component, OnInit } from '@angular/core';
import { AppState } from '../../../store/model';
import { Observable } from 'rxjs/Observable';
import { NgRedux } from '@angular-redux/store';

@Component({
  selector: 'app-query-progress',
  templateUrl: './query-progress.component.html',
  styleUrls: ['./query-progress.component.scss']
})
export class QueryProgressComponent implements OnInit {

    sendet$: Observable<boolean>;

    constructor(private store: NgRedux<AppState>) {
    }

    ngOnInit() {
        this.sendet$ = this.store.select(state => state.query.sendet);
    }
}
