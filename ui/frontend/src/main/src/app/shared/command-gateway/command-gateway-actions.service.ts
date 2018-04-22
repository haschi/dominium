import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommandType } from '../../inventur/command-type';
import {
    CommandMessage,
    CommandMessageAction,
    CommandResponseAction
} from './command-gateway.model';

export enum CommandGatewayActionType {
    angefordert = 'cba_angefordert',
    gelungen = 'cba_gelungen',
    fehlgeschlagen = 'cba_fehlgeschlagen'
}

@Injectable()
export class CommandGatewayActionsService {

    constructor() {
    }

    angefordert(type: CommandType, payload: any, meta: any): CommandMessageAction {
        return {
            type: CommandGatewayActionType.angefordert,
            message: {
                type: type,
                payload: payload,
                meta: meta
            }
        }
    };

    gelungen(message: CommandMessage, response: HttpResponse<any>): CommandResponseAction {
        return {
            type: CommandGatewayActionType.gelungen,
            message: message,
            response: {
                status: response.status,
                message: response.statusText
            }
        }
    };

    fehlgeschlagen(cmd: CommandMessage, status: number, message: string): CommandResponseAction {
        return {
            type: CommandGatewayActionType.fehlgeschlagen,
            message: cmd,
            response: {status, message}
        }
    }

}
