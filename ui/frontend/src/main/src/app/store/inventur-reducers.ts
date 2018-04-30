import { Action, AnyAction } from 'redux';
import { Epic } from 'redux-observable';
import { Observable } from 'rxjs/Observable';
import { Eroeffnungsbilanz } from '../inventur/bilanz/bilanz.model';
import { Inventar } from '../inventur/inventar';
import {
    CommandGatewayActionType,
    CommandResponseAction
} from '../shared/command-gateway/command.redux';
import { gelungen, QueryGatewayActionType } from '../shared/query-gateway/query.redux';
import { AppState, INVENTUR_INITIAL_STATE, InventurState } from './model';
import { CommandType } from '../inventur/command-type';
import { QueryType } from '../inventur/query-type';
import { filter, map } from 'rxjs/operators'

enum Inventur {
    Begonnen = 'Inventur.Begonnen',
    InventarGelesen = 'Inventur.InventarGelesen',
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

export function inventurReducer(state: InventurState = INVENTUR_INITIAL_STATE, action): InventurState {
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
    }

    switch (action.type) {
        case QueryGatewayActionType.fehlgeschlagen: {
            switch (action.message.type) {
                case QueryType.LeseInventar: {
                    return {...state, inventar: null}
                }
            }
        }
    }
    return state;
}