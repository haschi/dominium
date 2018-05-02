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

export interface InventurState {
    inventurId: string
    inventar: Inventar
    eroeffnungsbilanz: Eroeffnungsbilanz | null
}

export const INVENTUR_INITIAL_STATE: InventurState = {
    inventurId: "",
    inventar: {
        anlagevermoegen: [],
        umlaufvermoegen: [],
        schulden: [],
        reinvermoegen: {
            summeDerSchulden: '',
            summeDesVermoegens: '',
            reinvermoegen: ''
        }
    },
    eroeffnungsbilanz: null
};

enum Inventur {
    Begonnen = 'Inventur.Begonnen',
    InventarGelesen = 'Inventur.InventarGelesen',
    InventarNichtGelesen = 'Inventur.InventarNichtGelesen',
    EroeffnungsbilanzGelesen = 'Inventur.EroeffnungsbilanzGelesen'
}

export function inventurBegonnen(id: string): Action & {id: string} {
    return {
        type: Inventur.Begonnen,
        id: id
    }
}

export function inventarGelesen(inventar: Inventar): AnyAction /*& {inventar: Inventar}*/ {
    return {
        type: Inventur.InventarGelesen,
        inventar: inventar
    }
}

export function inventarNichtGelesen(): AnyAction {
    return {
        type: Inventur.InventarNichtGelesen
    }
}

export function eroeffnungsbilanzGelesen(eroeffnungsbilanz: Eroeffnungsbilanz): Action & {eroeffnungsbilanz: Eroeffnungsbilanz} {
    return {
        type: Inventur.EroeffnungsbilanzGelesen,
        eroeffnungsbilanz: eroeffnungsbilanz
    }
}

export function fallsCommandInventurBegonnenGelungen(): Epic<AnyAction, AppState> {
    return action$ => action$
        .pipe(
            filter(action => action.type === CommandGatewayActionType.gelungen),
            filter(action => action.message.type === CommandType.BeginneInventur),
            map(action => inventurBegonnen(action.message.payload.id))
        )
}

export function fallsQueryInventarGelesenGelungen(): Epic<AnyAction, AppState> {
    return action$ => action$
        .pipe(
            gelungen(QueryType.LeseInventar),
            map(body => inventarGelesen(body)))
}

export function fallsQueryEroeffnungsbilanzGelesenGelungen(): Epic<AnyAction, AppState> {
    return action$ => action$
        .pipe(
            gelungen(QueryType.LeseEroeffnungsbilanz),
            map(body => eroeffnungsbilanzGelesen(body)));
}

export function fallsInventarLesenFehlgeschlagen(): Epic<AnyAction, AppState> {
    return action$ => action$
        .pipe(
            fehlgeschlagen(QueryType.LeseInventar),
            map(response => inventarNichtGelesen())
        )
}

export function inventur(state: InventurState = INVENTUR_INITIAL_STATE, action): InventurState {
    switch (action.type) {
        case Inventur.Begonnen: {
            return {...INVENTUR_INITIAL_STATE, inventurId: action.id}
        }
        case Inventur.InventarGelesen: {
            return {...state, inventar: action.inventar}
        }
        case Inventur.EroeffnungsbilanzGelesen: {
            return {...state, eroeffnungsbilanz: action.eroeffnungsbilanz}
        }
        case Inventur.InventarNichtGelesen: {
            return {...state, inventar: null}
        }
    }

    return state;
}