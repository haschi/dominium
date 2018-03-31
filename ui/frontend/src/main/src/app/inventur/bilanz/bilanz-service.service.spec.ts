import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { inject, TestBed } from '@angular/core/testing';
import { LoggerService } from '../../shared/logger.service';
import { QueryGatewayService } from '../../shared/query-gateway/query-gateway.service';
import { QueryType } from '../../shared/query-gateway/query-type';
import { ResultType } from '../../shared/query-gateway/result-type';
import { APP_INITIAL_STATE, AppState, INVENTUR_INITIAL_STATE } from '../../store/model';
import { rootReducer } from '../../store/reducers';

import { BilanzServiceService } from './bilanz-service.service';
import { Eroeffnungsbilanz } from './bilanz.model';

describe('BilanzServiceService', () => {

    const mock = {
        send: jasmine.createSpy('send')
    }

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
                BilanzServiceService,
                LoggerService,
                {provide: QueryGatewayService, useValue: mock}
            ],
            imports: [NgReduxModule],
            schemas: [NO_ERRORS_SCHEMA]
        });
    });

    it('should be created', inject([BilanzServiceService], (service: BilanzServiceService) => {
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
                    umlaufvermoegen: []
                },
                passiva: {
                    eigenkapital: [],
                    fremdkapital: []
                },
                summe: '0,00 EUR'
            }
        },
        {
            bezeichnung: 'vollständige Eröffnungsbilanz',
            inventurId: '4713',
            eroeffnungsbilanz: {
                aktiva: {
                    anlagevermoegen: [
                        {position: 'Eigentumswohnung', waehrungsbetrag: '120.000,00 EUR'},
                        {position: 'Kraftfahrzeug', waehrungsbetrag: '15.000,00 EUR'}
                    ],
                    umlaufvermoegen: [
                        {position: 'Girokonto', waehrungsbetrag: '12.345,67 EUR'},
                        {position: 'Sparbuch', waehrungsbetrag: '23.456,78 EUR'},
                        {position: 'Geldbörse', waehrungsbetrag: '123,45 EUR'}
                    ]
                },
                passiva: {
                    eigenkapital: [
                        {position: 'Eigenkapital', waehrungsbetrag: '135.000,00 EUR'}
                    ],
                    fremdkapital: [
                        {position: 'Autokredit', waehrungsbetrag: '2.000,00 EUR'},
                        {position: 'Hypotheken', waehrungsbetrag: '100.000,00 EUR'},
                        {position: 'Provatkredite', waehrungsbetrag: '100,00 EUR'}
                    ]
                },
                summe: '1.00 EUR'
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
                inject([BilanzServiceService], (service: BilanzServiceService) => {
                    service.ladeBilanz()
                    expect(mock.send).toHaveBeenCalledWith(
                        QueryType.LeseEroeffnungsbilanz,
                        {id: testfall.inventurId},
                        ResultType.Eroeffnungsbilanz)
                }))

            it('sollte Eröffnungsbilanz liefern',
                inject([BilanzServiceService], (service: BilanzServiceService) => {
                service.bilanz$.subscribe(
                    bilanz => expect(bilanz).toEqual(testfall.eroeffnungsbilanz))
            }))
        })
    })
});
