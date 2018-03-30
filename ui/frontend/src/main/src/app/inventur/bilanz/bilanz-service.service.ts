import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { Eroeffnungsbilanz } from './bilanz.model';

@Injectable()
export class BilanzServiceService {

    bilanz$: Observable<Eroeffnungsbilanz>;

    constructor() {
        // mock
        this.bilanz$ = new BehaviorSubject({
            aktiva: {
                anlagevermoegen: [
                    {position: 'Eigentumswohnung', waehrungsbetrag: '120.000,00 EUR'},
                    {position: 'Kfz VW Polo', waehrungsbetrag: '15.000,00 EUR'}
                ],
                umlaufvermoegen: [
                    {position: 'Girokonto ING-DiBa', waehrungsbetrag: '1.234,56 EUR'},
                    {position: 'Geldbörse', waehrungsbetrag: '111,22 EUR'}
                ]
            },
            passiva: {
                eigenkapital: [
                    {position: 'Eigenkapital', waehrungsbetrag: '135.000,00 EUR'}
                ],
                fremdkapital: [
                    {position: 'Autokredit', waehrungsbetrag: '5.000,00 EUR'}
                ]
            },
            summe: "10.000,00 EUR"
        })
    }
}
