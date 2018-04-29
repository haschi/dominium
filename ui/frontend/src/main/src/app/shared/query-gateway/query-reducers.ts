import { HttpResponse } from '@angular/common/http';

import { Action } from 'redux';
import { Epic } from 'redux-observable';
import { of } from 'rxjs/observable/of';
import { AppState } from '../../store/model';
import { QueryGatewayService } from './query-gateway.service';

// TODO: nicht exportieren
export enum QueryGatewayActionType {
    angefordert = 'qga_angefordert',
    gelungen = 'qga_gelungen',
    fehlgeschlagen = 'qga_fehlgeschlagen'
}

export interface QueryMessage {
    type: string,
    payload: any,
    result: string
}

export interface QueryResponse {
    status: number,
    message: string,
    body: any
}

export type QueryMessageAction = Action & { message: QueryMessage }
export type QueryResponseAction = QueryMessageAction & { response: QueryResponse }
export type QueryAction = QueryMessageAction | QueryResponseAction

export const QUERY_GATEWAY_INITIAL_STATE: QueryGatewayState = {
    sendet: false,
    message: {type: '', payload: {}, result: ''},
    response: {status: 0, message: '', body: {}}
};

export function queryAngefordert(type: string, payload: any, result: string): QueryMessageAction {
    return {
        type: QueryGatewayActionType.angefordert,
        message: {
            type: type,
            payload: payload,
            result: result
        }
    }
};

export function queryGelungen(message: QueryMessage, response: HttpResponse<any>): QueryResponseAction {
    return {
        type: QueryGatewayActionType.gelungen,
        message: message,
        response: {
            status: response.status,
            message: response.statusText,
            body: response.body
        }
    }
};

export function queryFehlgeschlagen(query: QueryMessage, status: number, message: string): QueryResponseAction {
    return {
        type: QueryGatewayActionType.fehlgeschlagen,
        message: query,
        response: {status: status, message: message, body: {}}
    }
}

export interface QueryGatewayState {
    message: QueryMessage
    response: QueryResponse
    sendet: boolean
}

export function query(state: QueryGatewayState = QUERY_GATEWAY_INITIAL_STATE, action): QueryGatewayState {

    switch (action.type) {
        case QueryGatewayActionType.angefordert: {
            return {...state, sendet: true, message: action.message}
        }
        case QueryGatewayActionType.gelungen: {
            return {...state, response: action.response, sendet: false}
        }
        case QueryGatewayActionType.fehlgeschlagen: {
            return {...state, response: action.response, sendet: false}
        }
    }
    return state;
}

// queryAngefordert -> queryGelungen
// queryAngefordert -> queryFehlgeschlagen
export function createAngefordertEpic(service: QueryGatewayService): Epic<QueryAction, AppState> {
    return (action$) => action$
        .ofType(QueryGatewayActionType.angefordert)
        .mergeMap(action => service.get(action as QueryMessageAction)
            .map(response => queryGelungen(action.message, response))
            .catch(error => of(queryFehlgeschlagen(action.message, error.status, "Fehler"))));
}