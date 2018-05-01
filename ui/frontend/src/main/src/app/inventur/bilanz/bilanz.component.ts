import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { BilanzService } from './bilanz.service';
import { Bilanzgruppe, Eroeffnungsbilanz, Gruppe } from './bilanz.model';

@Component({
    selector: 'app-bilanz',
    templateUrl: './bilanz.component.html',
    styleUrls: ['./bilanz.component.scss']
})
export class BilanzComponent implements OnInit {

    bilanz$: Observable<Eroeffnungsbilanz>;

    constructor(private service: BilanzService) {
        this.bilanz$ = service.bilanz$
    }

    ngOnInit() {
        this.service.ladeBilanz()
    }

}
