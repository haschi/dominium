import { HttpClient, HttpResponse } from '@angular/common/http';
import { TdDialogService } from '@covalent/core';
import { Action } from 'redux';
import { Epic } from 'redux-observable';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { CommandType } from '../../inventur/command-type';
import { AppState } from '../../store/model';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/delay';

export interface CommandGatewayState {
    message: CommandMessage
    response: CommandResponse
    sendet: boolean
}

export interface CommandMessage {
    type: string,
    payload: any,
    meta: any,
}

export interface CommandResponse {
    status: number,
    message: string
}

export type CommandMessageAction = Action & { message: CommandMessage }
export type CommandResponseAction = CommandMessageAction & { response: CommandResponse }
export type CommandAction = CommandMessageAction | CommandResponseAction
export const COMMAND_GATEWAY_INITIAL_STATE: CommandGatewayState = {
    sendet: false,
    message: {type: CommandType.KeinCommand, payload: {}, meta: {}},
    response: {status: 0, message: ''}
};

export enum CommandGatewayActionType {
    angefordert = 'cba_angefordert',
    gelungen = 'cba_gelungen',
    fehlgeschlagen = 'cba_fehlgeschlagen'
}

export function commandAngefordert(type: CommandType, payload: any, meta: any): CommandMessageAction {
    return {
        type: CommandGatewayActionType.angefordert,
        message: {
            type: type,
            payload: payload,
            meta: meta
        }
    }
};

export function commandGelungen(message: CommandMessage, response: CommandResponse): CommandResponseAction {
    return {
        type: CommandGatewayActionType.gelungen,
        message: message,
        response: response
    }
};

export function commandFehlgeschlagen(cmd: CommandMessage, status: number, message: string): CommandResponseAction {
    return {
        type: CommandGatewayActionType.fehlgeschlagen,
        message: cmd,
        response: {status, message}
    }
}

export function command(state: CommandGatewayState = COMMAND_GATEWAY_INITIAL_STATE, action): CommandGatewayState {
    switch (action.type) {
        case CommandGatewayActionType.angefordert: {
            return {...state, sendet: true, message: action.message}
        }
        case CommandGatewayActionType.gelungen: {
            return {...state, response: action.response, sendet: false}
        }
        case CommandGatewayActionType.fehlgeschlagen: {
            return {...state, response: action.response, sendet: false}
        }
    }

    return state;
}

// angefordert -> gelungen | fehlgeschlagen
export function fallsCommandAngefordert(http: HttpClient, dialogService: TdDialogService): Epic<CommandAction, AppState> {
    return (action$) => action$
        .ofType(CommandGatewayActionType.angefordert)
        .mergeMap(action => post(http, action.message)
            .map(response => commandGelungen(action.message, {status: response.status, message: response.statusText}))
            .catch(error => onError(dialogService, error, action)));
}

function post(http: HttpClient, message: CommandMessage): Observable<HttpResponse<Object>> {
    return http.post("/gateway/command", message, {observe: 'response'});
}

function onError(dialogService: TdDialogService, error: any, action: CommandAction): Observable<CommandAction> {
    if (error.status >= 500) {
    return openRetryDialog(dialogService)
        .map((accept: boolean) => {
            if (accept) {
                return commandAngefordert(action.type, action.message.payload, action.message.meta)

            } else {
                return commandFehlgeschlagen(action.message, error.status, "Fehler")
            }
        }).delay(2000)
    }
    return of(commandFehlgeschlagen(action.message, error.status, "Fehler"))
}

function openRetryDialog(dialogService: TdDialogService): Observable<boolean> {
    return dialogService.openConfirm({
        message: 'Der Server ist derzeit nicht verfügbar. Soll die Anfrage wiederholt werden',
        title: 'Verbindungsfehler',
        cancelButton: 'Nein',
        acceptButton: 'Ja',
    }).afterClosed();
}
