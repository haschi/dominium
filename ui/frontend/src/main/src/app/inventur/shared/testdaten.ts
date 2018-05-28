import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { InventurService } from '../inventur.service';
import { GruppenState } from './gruppen.redux';

// export const state: GruppenState = {gruppen: InventurService.leereGruppen}

export const gruppen$ = new BehaviorSubject(InventurService.leereGruppen);

export const testgruppen: GruppenState = {
    gruppen: {
        anlagevermoegen: {bezeichnung: 'Anlagevermögen', kategorien: [{kategorie: 'Aktien'}, {kategorie: 'Lebensversicherung'}, {kategorie: 'Rentenfonds'}]},
        umlaufvermoegen: {bezeichnung: 'Umlaufvermögen', kategorien: [{kategorie: 'Girokonto'}, {kategorie: 'Geld-Börse'}, {kategorie: 'Sparbuch'}]},
        schulden: {bezeichnung: 'Schulden', kategorien: [{kategorie: 'Verbraucherkredite'}, {kategorie: 'Hypotheken'}]}
    }
}

export const params$ = new BehaviorSubject({id: '4711', gruppe: 'schulden', kategorie: 0})