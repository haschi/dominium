import { Injectable } from '@angular/core';
import { CommandMessageAction, CommandResponseAction } from './command-bus.model';
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

    // gesendet = (message: CommandMessage): CommandMessageAction => ({
    //     type: CommandBusActions.gesendet,
    //     ...message
    // });

    gelungen = (response: HttpResponse<any>): CommandResponseAction => ({
        type: CommandBusActions.gelungen,
        response: {
            status: response.status,
            message: response.statusText
        }
    });

    fehlgeschlagen = (status: number, message: string): CommandResponseAction => ({
        type: CommandBusActions.fehlgeschlagen, response: {status, message}
    })
}
