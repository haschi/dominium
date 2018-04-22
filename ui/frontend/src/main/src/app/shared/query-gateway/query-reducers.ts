import {
    QUERY_GATEWAY_INITIAL_STATE,
    QueryGatewayState
} from './query-gateway.model';
import { QueryGatewayActionType } from './query-gateway-actions.service';

export function queryReducer(state: QueryGatewayState = QUERY_GATEWAY_INITIAL_STATE, action): QueryGatewayState {

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