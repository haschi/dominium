import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { BilanzServiceService } from './bilanz-service.service';
import { Bilanzgruppe, Eroeffnungsbilanz, Gruppe } from './bilanz.model';

@Component({
    selector: 'app-bilanz',
    templateUrl: './bilanz.component.html',
    styleUrls: ['./bilanz.component.scss']
})
export class BilanzComponent implements OnInit {

    bilanz$: Observable<Eroeffnungsbilanz>;

    constructor(private service: BilanzServiceService) {
        this.bilanz$ = service.bilanz$

        // this.bilanz$ = new BehaviorSubject<Eroeffnungsbilanz>({
        //     aktiva: {
        //         anlagevermoegen: [],
        //         umlaufvermoegen: []
        //     },
        //     passiva: {
        //         eigenkapital: [],
        //         fremdkapital: []
        //     },
        //     summe: "10.000,00 EUR"
        // })
    }

    ngOnInit() {
    }

}
