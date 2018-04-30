import { HttpClient, HttpResponse } from '@angular/common/http';
import { Provider } from '@angular/core';
import { Action, AnyAction } from 'redux';
import { Epic, ofType } from 'redux-observable';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { QueryType } from '../../inventur/query-type';
import { AppState } from '../../store/model';
import { filter, map } from 'rxjs/operators';

// TODO: Obwohl Observable Operatoren nicht importiert sind, funktionieren diese
// Imports prüfen!
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

export interface QueryGatewayState {
    message: QueryMessage
    response: QueryResponse
    sendet: boolean
}

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

export function queryGelungen(message: QueryMessage, response: QueryResponse): QueryResponseAction {

    return {
        type: QueryGatewayActionType.gelungen,
        message: message,
        response: {
            status: response.status,
            message: response.message,
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

// queryAngefordert -> queryGelungen | queryFehlgeschlagen
export function fallsQueryAngefordert(http: HttpClient): Epic<QueryAction, AppState> {
    return (action$) => action$
        .ofType(QueryGatewayActionType.angefordert)
        .mergeMap(action => postQuery(http, action as QueryMessageAction)
            .map(response => queryGelungen(action.message, {
                status: response.status,
                message: response.statusText,
                body: response.body
            }))
            .catch(error => of(queryFehlgeschlagen(action.message, error.status, "Fehler"))));
}

function postQuery(http: HttpClient, action: QueryMessageAction): Observable<HttpResponse<Object>> {
    return Observable.timer(1000)
        .flatMap(nix =>
            http.post("/gateway/query", action.message, {observe: 'response'})
                .retryWhen(error =>
                    error.do(val => this.logger.log(`QUERY get ERROR ${val.message}`))
                        .delay(1000)
                        .take(4)
                        .concat(Observable.throw(new Error('Zeit-Limit überschritten!')))));
}

export const gelungen = (q: string) => (source: Observable<AnyAction>) =>
    source.pipe(
        filter(action => action.type === QueryGatewayActionType.gelungen),
        filter(action => action.message.type === q),
        map(action => action.response.body))