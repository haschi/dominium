import { dispatch, NgRedux } from '@angular-redux/store';
import { Injectable } from '@angular/core';
import { AnyAction } from 'redux';
import { Observable } from 'rxjs/Observable';
import { AppState } from '../../store/model';
import { Gruppe, InventurGruppe } from './gruppen.redux';
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
    gruppen$: Observable<InventurGruppe>

    constructor(private store: NgRedux<AppState>) {
      this.eingaben$ = this.store.select(s => s.inventureingabe.eingaben)
        this.gruppen$ = this.store.select(s => s.inventurGruppen.gruppen)
    }

    @dispatch()
    hinzufügen(gruppe: string, kategorie: number, eingabe: PositionEingabe): AnyAction {
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
