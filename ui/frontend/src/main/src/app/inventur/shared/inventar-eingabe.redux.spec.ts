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
import { Inventarposition, PositionEingabe } from './inventarposition';

describe('Inventar-Eingabe', () => {
    var store: Store<InventurEingabeState>;


    describe('mit Gruppen', () => {

        const eingabe: PositionEingabe[] = [{
            position: 'Autokredit',
            waehrungsbetrag: {betrag: '12.400', waehrung: 'EUR'}
        }, {
            position: 'Privatkredit',
            waehrungsbetrag: {betrag: '5.000', waehrung: 'EUR'}
        }];


        beforeEach(() => {
            store = createStore(inventureingabe, INVENTUREINGBAE_INITIAL_STATE)
            store.dispatch(eingabeHinzufügen('schulden', 0, eingabe[0]));
            store.dispatch(eingabeHinzufügen('schulden', 0, eingabe[1]));
        })


        it('sollte eingabe speichern', () => {
            expect(store.getState()).toEqual({eingaben: [{
                    gruppe: 'schulden',
                    kategorie: 0,
                    position: {
                    position: 'Autokredit',
                    waehrungsbetrag: {betrag: '12.400', waehrung: 'EUR'}}
                }, {
                    gruppe: 'schulden',
                    kategorie: 0,
                    position: {
                    position: 'Privatkredit',
                    waehrungsbetrag: {betrag: '5.000', waehrung: 'EUR'}}
                }]})
        })

        it('sollte Eingabe entfernen', () => {
            store.dispatch(eingabeEntfernen(1))
            expect(store.getState()).toEqual({eingaben: [{
                    gruppe: 'schulden',
                    kategorie: 0,
                    position: {
                        position: 'Autokredit',
                        waehrungsbetrag: {betrag: '12.400', waehrung: 'EUR'}}
                }]})
        })
    })
})