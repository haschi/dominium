import { Action, AnyAction } from 'redux';
import { Epic } from 'redux-observable';
import { filter, map } from 'rxjs/operators'
import { CommandGatewayActionType } from '../../shared/command-gateway/command.redux';
import { fehlgeschlagen, gelungen } from '../../shared/query-gateway/query.redux';
import { AppState } from '../../store/model';
import { Eroeffnungsbilanz } from '../bilanz/bilanz.model';
import { CommandType } from './command-type';
import { Inventar } from './inventar';
import { QueryType } from './query-type';

export interface Kategorie {
    kategorie: string
}

interface Gruppe {
    bezeichnung: string;
    kategorien: Kategorie[]
}

interface InventurGruppe {
    anlagevermoegen: Gruppe;
    umlaufvermoegen: Gruppe;
    schulden: Gruppe
}

export interface GruppenState {
    gruppen: InventurGruppe
}

export const INVENTURGRUPPEN_INITIAL_STATE: GruppenState = {
    gruppen: {
        anlagevermoegen: {bezeichnung: '', kategorien: []},
        umlaufvermoegen: {bezeichnung: '', kategorien: []},
        schulden: {bezeichnung: '', kategorien: []}
    }
};

enum InventurGruppen {
    InventurGruppenGelesen = 'InventurGruppen.Gelesen',
}

export function inventurGruppenGelesen(gruppen: InventurGruppen): Action & {gruppen: InventurGruppen} {
    return {
        type: InventurGruppen.InventurGruppenGelesen,
        gruppen: gruppen
    }
}

export function fallsQueryInventurGruppenGelesenGelungen(): Epic<AnyAction, AppState> {
    return action$ => action$
        .pipe(
            gelungen(QueryType.LeseInventurGruppen),
            map(body => inventurGruppenGelesen(body)))
}

export function inventurGruppen(state: GruppenState = INVENTURGRUPPEN_INITIAL_STATE, action): GruppenState {
    switch (action.type) {
        case InventurGruppen.InventurGruppenGelesen: {
            return {...INVENTURGRUPPEN_INITIAL_STATE, gruppen: action.gruppen}
        }
    }

    return state;
}