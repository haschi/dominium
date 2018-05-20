import { dispatch, NgRedux } from '@angular-redux/store';
import { Injectable } from '@angular/core';
import { AnyAction } from 'redux';
import { Observable } from 'rxjs/Observable';
import { AppState } from '../../store/model';
import {
    eingabeEntfernen,
    eingabeHinzufügen,
    eingabeLeeren,
    inventureingabe
} from './inventar-eingabe.redux';
import { Inventarposition } from './inventarposition';

@Injectable()
export class InventarEingabeService {

    eingaben$: Observable<Inventarposition[]>;

    constructor(private store: NgRedux<AppState>) {
      this.eingaben$ = this.store.select(s => s.inventureingabe.eingaben)
    }

    @dispatch()
    hinzufügen(eingabe: Inventarposition[]): AnyAction {
      return eingabeHinzufügen(eingabe);
    }

    @dispatch()
    entfernen(index: number): AnyAction {
        return eingabeEntfernen(index);
    }

    @dispatch()
    leeren(): AnyAction {
        return eingabeLeeren();
    }
}
