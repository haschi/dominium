import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { inject, TestBed } from '@angular/core/testing';
import { APP_INITIAL_STATE, AppState } from '../../store/model';
import { rootReducer } from '../../store/reducers';

import { InventarEingabeService } from './inventar-eingabe.service';
import { Inventarposition } from './inventarposition';

describe('Inventar Eingabe', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [NgReduxModule],
            providers: [InventarEingabeService]
        });
    });

    beforeEach(inject([NgRedux], (store: NgRedux<AppState>) => {
        store.configureStore(rootReducer, APP_INITIAL_STATE)
    }))

    it('sollte Anfangs leer sein', inject([InventarEingabeService], (service: InventarEingabeService) => {
        service.eingaben$.subscribe(eingaben => expect(eingaben).toEqual([]))
    }))

    describe('Mit Eingabe', () => {
        const eingabe: Inventarposition[] = [{
            kategorie: 'Aktien',
            position: 'VW',
            waehrungsbetrag: '1234.56 EUR'
        }, {
            kategorie: 'Bankguthaben',
            position: 'Girokonto',
            waehrungsbetrag: '10.000,00 EUR,'
        }]

        beforeEach(inject([InventarEingabeService], (service: InventarEingabeService) => {
            service.hinzufügen(eingabe);
        }))

        it('sollte Eingabe hinzufügen', inject([InventarEingabeService], (service: InventarEingabeService) => {
            service.eingaben$.subscribe(eingaben => {
                expect(eingaben).toEqual(eingabe);
            })
        }))
        it('sollte Eingabe anhand Index entfernen', inject([InventarEingabeService], (service: InventarEingabeService) => {
            service.entfernen(1);
            service.eingaben$.subscribe(eingaben => {
                expect(eingaben).toEqual([eingabe[0]])
            })
        }))

        it('sollte Eingabe vollständig entfernen', inject([InventarEingabeService], (service: InventarEingabeService) => {
            service.leeren();
            service.eingaben$.subscribe(eingaben => {
                expect(eingaben).toEqual([])
            })
        }))
    })
});
