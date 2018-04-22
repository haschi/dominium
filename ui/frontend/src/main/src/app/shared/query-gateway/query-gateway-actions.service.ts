import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { QueryMessage, QueryMessageAction, QueryResponseAction } from './query-gateway.model';
import { QueryType } from './query-type';
import { ResultType } from './result-type';

export enum QueryGatewayActionType {
    angefordert = 'qga_angefordert',
    gelungen = 'qga_gelungen',
    fehlgeschlagen = 'qga_fehlgeschlagen'
}

export function queryAngefordert(type: QueryType, payload: any, result: ResultType): QueryMessageAction {
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

export function queryFehlgeschlagen(cmd: QueryMessage, status: number, message: string): QueryResponseAction {
    return {
        type: QueryGatewayActionType.fehlgeschlagen,
        message: cmd,
        response: {status: status, message: message, body: {}}
    }
}