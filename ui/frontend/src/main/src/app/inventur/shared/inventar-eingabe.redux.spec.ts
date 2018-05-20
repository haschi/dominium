import { NgRedux } from '@angular-redux/store';
import { async } from '@angular/core/testing';
import { createStore, Reducer, Store } from 'redux';
import { AppState } from '../../store/model';
import {
    eingabeEntfernen,
    eingabeHinzufügen,
    INVENTUREINGBAE_INITIAL_STATE,
    inventureingabe,
    InventurEingabeState
} from './inventar-eingabe.redux';
import { Inventarposition } from './inventarposition';

describe('Inventar-Eingabe', () => {
    var store: Store<InventurEingabeState>;


    describe('mit Gruppen', () => {

        const eingabe: Inventarposition[] = [{
            kategorie: 'Kredit',
            position: 'Autokredit',
            waehrungsbetrag: '12.400 EUR'
        }, {
            kategorie: 'Kredit',
            position: 'Privatekredit',
            waehrungsbetrag: '5.000 EUR'
        }];


        beforeEach(() => {
            store = createStore(inventureingabe, INVENTUREINGBAE_INITIAL_STATE)
            store.dispatch(eingabeHinzufügen(eingabe));
        })


        it('sollte eingabe speichern', () => {
            expect(store.getState()).toEqual({eingaben: eingabe})
        })

        it('sollte Eingabe entfernen', () => {
            store.dispatch(eingabeEntfernen(1))
            expect(store.getState()).toEqual({eingaben: [eingabe[0]]})
        })
    })
})