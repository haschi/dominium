import { Injectable } from '@angular/core';
import {
    CommandMessage, CommandMessageAction,
    CommandResponseAction
} from './command-gateway.model';
import { HttpResponse } from '@angular/common/http';
import { CommandType } from '../../inventur/command-type';

export enum CommandGatewayActionType {
    angefordert = 'cba_angefordert',
    gelungen = 'cba_gelungen',
    fehlgeschlagen = 'cba_fehlgeschlagen'
}

@Injectable()
export class CommandGatewayActionsService {

    constructor() {
    }

    angefordert = (type: CommandType, payload: any, meta: any): CommandMessageAction => ({
        type: CommandGatewayActionType.angefordert,
        message: {
            type: type,
            payload: payload,
            meta: meta
        }
    });

    gelungen = (message: CommandMessage, response: HttpResponse<any>): CommandResponseAction => ({
        type: CommandGatewayActionType.gelungen,
        message: message,
        response: {
            status: response.status,
            message: response.statusText
        }
    });

    fehlgeschlagen = (cmd: CommandMessage, status: number, message: string): CommandResponseAction => ({
        type: CommandGatewayActionType.fehlgeschlagen,
        message: cmd,
        response: {status, message}
    })
}
