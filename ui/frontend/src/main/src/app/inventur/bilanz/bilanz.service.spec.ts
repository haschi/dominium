import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { inject, TestBed } from '@angular/core/testing';
import { LoggerService } from '../../shared/logger.service';
import { QueryGatewayService } from '../../shared/query-gateway/query-gateway.service';
import { INVENTUR_INITIAL_STATE } from '../shared/inventur.redux';
import { QueryType } from '../shared/query-type';
import { ResultType } from '../shared/result-type';
import { APP_INITIAL_STATE, AppState} from '../../store/model';
import { rootReducer } from '../../store/reducers';

import { BilanzService } from './bilanz.service';
import { Eroeffnungsbilanz } from './bilanz.model';

describe('BilanzService', () => {

    const mock = {
        send: jasmine.createSpy('send')
    }

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
                BilanzService,
                LoggerService,
                {provide: QueryGatewayService, useValue: mock}
            ],
            imports: [NgReduxModule],
            schemas: [NO_ERRORS_SCHEMA]
        });
    });

    it('should be created', inject([BilanzService], (service: BilanzService) => {
        expect(service).toBeTruthy();
    }));

    interface Testfall {
        bezeichnung: string
        inventurId: string
        eroeffnungsbilanz: Eroeffnungsbilanz
    }

    const testfälle: Testfall[] = [
        {
            bezeichnung: 'leere Eröffnungsbilanz',
            inventurId: '4712',
            eroeffnungsbilanz: {
                aktiva: {
                    anlagevermoegen: [],
                    umlaufvermoegen: [],
                    fehlbetrag: [],
                    summe: '0,00 EUR'
                },
                passiva: {
                    eigenkapital: [],
                    fremdkapital: [],
                    summe: '0,00 EUR'
                }
            }
        },
        {
            bezeichnung: 'vollständige Eröffnungsbilanz',
            inventurId: '4713',
            eroeffnungsbilanz: {
                aktiva: {
                    anlagevermoegen: [
                        {kategorie: "Sonstiges", position: 'Eigentumswohnung', waehrungsbetrag: '120.000,00 EUR'},
                        {kategorie: "Sonstiges", position: 'Kraftfahrzeug', waehrungsbetrag: '15.000,00 EUR'}
                    ],
                    umlaufvermoegen: [
                        {kategorie: "Sonstiges", position: 'Girokonto', waehrungsbetrag: '12.345,67 EUR'},
                        {kategorie: "Sonstiges", position: 'Sparbuch', waehrungsbetrag: '23.456,78 EUR'},
                        {kategorie: "Sonstiges", position: 'Geldbörse', waehrungsbetrag: '123,45 EUR'}
                    ],
                    fehlbetrag: [
                        {kategorie: "Sonstiges", position: 'Fehlbetrag', waehrungsbetrag: '1.000,00 EUR'}
                    ],
                    summe: '1.00 EUR'
                },
                passiva: {
                    eigenkapital: [
                        {kategorie: "Sonstiges", position: 'Eigenkapital', waehrungsbetrag: '135.000,00 EUR'}
                    ],
                    fremdkapital: [
                        {kategorie: "Sonstiges", position: 'Autokredit', waehrungsbetrag: '2.000,00 EUR'},
                        {kategorie: "Sonstiges", position: 'Hypotheken', waehrungsbetrag: '100.000,00 EUR'},
                        {kategorie: "Sonstiges", position: 'Provatkredite', waehrungsbetrag: '100,00 EUR'}
                    ],
                    summe: '1.00 EUR'
                },
            }
        }
    ]

    testfälle.forEach(testfall => {
        describe(testfall.bezeichnung, () => {
            beforeEach(inject([NgRedux], (store: NgRedux<AppState>) => {
                store.configureStore(rootReducer, {
                    ...APP_INITIAL_STATE,
                    inventur: {
                        ...INVENTUR_INITIAL_STATE,
                        inventurId: testfall.inventurId,
                        eroeffnungsbilanz: testfall.eroeffnungsbilanz
                    }
                })
            }))

            it('sollte die Eröffnungsbilanz laden',
                inject([BilanzService], (service: BilanzService) => {
                    service.ladeBilanz()
                    expect(mock.send).toHaveBeenCalledWith(
                        QueryType.LeseEroeffnungsbilanz,
                        {inventurId: testfall.inventurId},
                        ResultType.Eroeffnungsbilanz)
                }))

            it('sollte Eröffnungsbilanz liefern',
                inject([BilanzService], (service: BilanzService) => {
                service.bilanz$.subscribe(
                    bilanz => expect(bilanz).toEqual(testfall.eroeffnungsbilanz))
            }))
        })
    })
});
