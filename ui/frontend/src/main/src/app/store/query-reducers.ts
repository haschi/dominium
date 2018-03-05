import {
    QUERY_GATEWAY_INITIAL_STATE,
    QueryGatewayState
} from '../shared/query-gateway/query-gateway.model';
import { QueryGatewayActionType } from '../shared/query-gateway/query-gateway-actions.service';

export function queryReducer(state: QueryGatewayState = QUERY_GATEWAY_INITIAL_STATE, action): QueryGatewayState {
    console.info(`REDUCE QUERY ${action.type}`);
    switch (action.type) {
        case QueryGatewayActionType.angefordert: {
            return {...state, sendet: true, message: action.message}
        }
        case QueryGatewayActionType.gelungen: {
            return {...state, response: action.response, sendet: false}
        }
        case QueryGatewayActionType.fehlgeschlagen: {
            console.error(`REDUCE QUERY fehlgeschlagen: ${JSON.stringify(action)}`);
            return {...state, response: action.response, sendet: false}
        }
    }
    return state;
}