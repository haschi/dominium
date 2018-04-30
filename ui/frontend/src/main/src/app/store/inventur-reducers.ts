import { Action, AnyAction } from 'redux';
import { Epic } from 'redux-observable';
import {
    CommandGatewayActionType,
    CommandResponseAction
} from '../shared/command-gateway/command.redux';
import { QueryGatewayActionType } from '../shared/query-gateway/query.redux';
import { AppState, INVENTUR_INITIAL_STATE, InventurState } from './model';
import { CommandType } from '../inventur/command-type';
import { QueryType } from '../inventur/query-type';

enum Inventur {
    Begonnen = 'Inventur.Begonnen'
}

export function inventurBegonnen(id: string): Action & {id: string} {
    return {
        type: Inventur.Begonnen,
        id: id
    }
}

export function fallsCommandInventurBegonnenGelungen(): Epic<AnyAction, AppState> {
    return action$ => action$
        .ofType(CommandGatewayActionType.gelungen)
        .filter(action => action.message.type === CommandType.BeginneInventur)
        .map(action => inventurBegonnen(action.message.payload.id))
}

export function inventurReducer(state: InventurState = INVENTUR_INITIAL_STATE, action): InventurState {
    switch (action.type) {
        case Inventur.Begonnen: {
            return {...INVENTUR_INITIAL_STATE, inventurId: action.id}
        }
    }

    switch (action.type) {
        case QueryGatewayActionType.gelungen: {
            switch (action.message.type) {
                case QueryType.LeseInventar: {
                    return {...state, inventar: action.response.body}
                }
                case QueryType.LeseEroeffnungsbilanz: {
                    return {...state, eroeffnungsbilanz: action.response.body}
                }
            }
        }
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