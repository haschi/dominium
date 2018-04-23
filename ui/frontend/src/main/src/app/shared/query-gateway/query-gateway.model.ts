import { Action } from 'redux';

export interface QueryGatewayState {
    message: QueryMessage
    response: QueryResponse
    sendet: boolean
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
