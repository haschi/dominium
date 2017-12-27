import { Injectable } from '@angular/core';
import { CommandMessage, CommandMessageAction, CommandResponseAction } from './command-bus.model';
import { HttpResponse } from '@angular/common/http';

export enum CommandBusActions {
    angefordert = 'cba_angefordert',
    gesendet = 'cba_gesendet',
    gelungen = 'cba_gelungen',
    fehlgeschlagen = 'cba_fehlgeschlagen'
}

@Injectable()
export class CommandBusActionsService {

    constructor() {
    }

    angefordert = (type: string, payload: any, meta: any): CommandMessageAction => ({
        type: CommandBusActions.angefordert,
        message: {
            type: type,
            payload: payload,
            meta: meta
        }
    });

    gelungen = (message: CommandMessage, response: HttpResponse<any>): CommandResponseAction => ({
        type: CommandBusActions.gelungen,
        message: message,
        response: {
            status: response.status,
            message: response.statusText
        }
    });

    fehlgeschlagen = (cmd: CommandMessage, status: number, message: string): CommandResponseAction => ({
        type: CommandBusActions.fehlgeschlagen,
        message: cmd,
        response: {status, message}
    })
}
