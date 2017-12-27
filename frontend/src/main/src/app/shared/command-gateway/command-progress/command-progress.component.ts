import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { NgRedux } from '@angular-redux/store';
import { AppState } from '../../../store/model';

import 'rxjs/add/operator/delay';

@Component({
    selector: 'app-command-progress',
    templateUrl: './command-progress.component.html',
    styleUrls: ['./command-progress.component.scss']
})
export class CommandProgressComponent implements OnInit {

    sendet$: Observable<boolean>;

    constructor(private store: NgRedux<AppState>) {
    }

    ngOnInit() {
        this.sendet$ = this.store.select(state => state.command.sendet);
        // this.sendet$ = this.store
        //     .select(state => state.command.sendet)
        //     .flatMap(s => s ? of(s).delay(500) : of(s))
    }
}
