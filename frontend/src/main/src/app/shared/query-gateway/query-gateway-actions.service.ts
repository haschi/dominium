import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { QueryMessage, QueryMessageAction, QueryResponseAction } from './query-gateway.model';
import { QueryType } from './query-type';

export enum QueryGatewayActionType {
    angefordert = 'qga_angefordert',
    gelungen = 'qga_gelungen',
    fehlgeschlagen = 'qga_fehlgeschlagen'
}
@Injectable()
export class QueryGatewayActionsService {

  constructor() { }

    angefordert = (type: QueryType, payload: any, meta: any): QueryMessageAction => ({
        type: QueryGatewayActionType.angefordert,
        message: {
            type: type,
            payload: payload,
            meta: meta
        }
    });

    gelungen = (message: QueryMessage, response: HttpResponse<any>): QueryResponseAction => ({
        type: QueryGatewayActionType.gelungen,
        message: message,
        response: {
            status: response.status,
            message: response.statusText
        }
    });

    fehlgeschlagen = (cmd: QueryMessage, status: number, message: string): QueryResponseAction => ({
        type: QueryGatewayActionType.fehlgeschlagen,
        message: cmd,
        response: {status, message}
    })

}
