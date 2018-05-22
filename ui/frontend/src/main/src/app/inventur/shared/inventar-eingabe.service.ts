import { dispatch, NgRedux } from '@angular-redux/store';
import { Injectable } from '@angular/core';
import { AnyAction } from 'redux';
import { Observable } from 'rxjs/Observable';
import { AppState } from '../../store/model';
import {
    Eingabe,
    eingabeEntfernen,
    eingabeHinzufügen,
    eingabeLeeren,
    inventureingabe
} from './inventar-eingabe.redux';
import { Inventarposition, PositionEingabe } from './inventarposition';

@Injectable()
export class InventarEingabeService {

    eingaben$: Observable<Eingabe[]>;

    constructor(private store: NgRedux<AppState>) {
      this.eingaben$ = this.store.select(s => s.inventureingabe.eingaben)

    }

    @dispatch()
    hinzufügen(gruppe: string, kategorie: number, eingabe: PositionEingabe[]): AnyAction {
      return eingabeHinzufügen(gruppe, kategorie, eingabe);
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
