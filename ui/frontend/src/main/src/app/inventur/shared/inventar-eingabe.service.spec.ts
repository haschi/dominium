import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { inject, TestBed } from '@angular/core/testing';
import { APP_INITIAL_STATE, AppState } from '../../store/model';
import { rootReducer } from '../../store/reducers';

import { InventarEingabeService } from './inventar-eingabe.service';
import { Inventarposition, PositionEingabe } from './inventarposition';

fdescribe('Inventar Eingabe', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [NgReduxModule],
            providers: [InventarEingabeService]
        });
    });

    describe('im anfänglichen Zustand', () => {
        beforeEach(inject([NgRedux], (store: NgRedux<AppState>) => {
            store.configureStore(rootReducer, APP_INITIAL_STATE)
        }))

        it('sollte keine Eingaben enthalten', inject([InventarEingabeService], (service: InventarEingabeService) => {
            service.eingaben$.subscribe(eingaben => expect(eingaben).toEqual([]))
        }))

        describe('Mit Eingabe', () => {
            const eingabe: PositionEingabe[] = [{
                position: 'VW',
                waehrungsbetrag: {betrag: '1234.56', waehrung: 'EUR'}
            }, {
                position: 'Girokonto',
                waehrungsbetrag: {betrag: '10.000,00', waehrung: 'EUR'}
            }]

            beforeEach(inject([InventarEingabeService], (service: InventarEingabeService) => {
                service.hinzufügen('anlagevermoegen', 0, eingabe);
            }))

            it('sollte Eingabe hinzufügen', inject([InventarEingabeService], (service: InventarEingabeService) => {
                service.eingaben$.subscribe(eingaben => {
                    expect(eingaben).toEqual(eingabe.map(e => {return {gruppe: 'anlagevermoegen', kategorie: 0, position: e}}));
                })
            }))

            it('sollte Eingabe anhand Index entfernen', inject([InventarEingabeService], (service: InventarEingabeService) => {
                service.entfernen(1);
                service.eingaben$.subscribe(eingaben => {
                    expect(eingaben).toEqual([eingabe[0]].map(e => {return {gruppe: 'anlagevermoegen', kategorie: 0, position: e}}))
                })
            }))

            it('sollte Eingabe vollständig entfernen', inject([InventarEingabeService], (service: InventarEingabeService) => {
                service.leeren();
                service.eingaben$.subscribe(eingaben => {
                    expect(eingaben).toEqual([])
                })
            }))
        })
    })

    describe('mit Eingabe des Anlagevermögens', () => {
        const anlagevermoegen = [
            {
                gruppe: 'anlagevermoegen',
                kategorie: 0,
                position: {
                    position: 'VW Aktien',
                    waehrungsbetrag: {
                        betrag: '1200,00',
                        waehrung: 'EUR'
                    }
                }
            },
            {
                gruppe: 'anlagevermoegen',
                kategorie: 1,
                position: {
                    position: 'Lebensversicherung',
                    waehrungsbetrag: {
                        betrag: '10.000,00',
                        waehrung: 'EUR'
                    }
                }
            }
        ];

        beforeEach(inject([NgRedux], (store: NgRedux<AppState>) => {
            store.configureStore(rootReducer, {...APP_INITIAL_STATE, inventureingabe: {
                eingaben: anlagevermoegen
                }})
        }))

        describe('Mit Eingabe des Umlaufvermögens', () => {
            const umlaufvermoegen: PositionEingabe[] = [{
                position: 'Sparbuch',
                waehrungsbetrag: {betrag: '1234.56', waehrung: 'EUR'}
            }, {
                position: 'Girokonto',
                waehrungsbetrag: {betrag: '10.000,00', waehrung: 'EUR'}
            }]

            beforeEach(inject([InventarEingabeService], (service: InventarEingabeService) => {
                service.hinzufügen('umlaufvermoegen', 0, umlaufvermoegen)
            }))

            it('sollte Eingabe hinzufügen', inject([InventarEingabeService], (service: InventarEingabeService) => {
                service.eingaben$.subscribe(eingaben => {
                    expect(eingaben).toEqual(
                        anlagevermoegen
                            .concat(umlaufvermoegen.map(eingabe => {return {gruppe: 'umlaufvermoegen', kategorie: 0, position: eingabe}}))
                    )
                })
            }))

            describe('Löschen des Eintrags 3', () => {

                beforeEach(inject([InventarEingabeService], (service: InventarEingabeService) => {
                    service.entfernen(2)
                }))

                it('sollte Sparbuch-Eintrag löschen', inject([InventarEingabeService], (service: InventarEingabeService) => {
                    service.eingaben$.subscribe(eingaben => {
                        expect(eingaben).toEqual([
                            {
                                gruppe: 'anlagevermoegen',
                                kategorie: 0,
                                position: {
                                    position: 'VW Aktien',
                                    waehrungsbetrag: {
                                        betrag: '1200,00',
                                        waehrung: 'EUR'
                                    }
                                }
                            },
                            {
                                gruppe: 'anlagevermoegen',
                                kategorie: 1,
                                position: {
                                    position: 'Lebensversicherung',
                                    waehrungsbetrag: {
                                        betrag: '10.000,00',
                                        waehrung: 'EUR'
                                    }
                                }
                            },
                            {
                                gruppe: 'umlaufvermoegen',
                                kategorie: 0,
                                position: {
                                    position: 'Girokonto',
                                    waehrungsbetrag: {
                                        betrag: '10.000,00',
                                        waehrung: 'EUR'
                                    }
                                }
                            }
                            ]
                        )
                    })
                }))
            })
        })
    })
});
