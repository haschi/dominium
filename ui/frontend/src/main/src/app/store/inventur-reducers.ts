import { INVENTUR_INITIAL_STATE, InventurState } from './model';
import { CommandGatewayActionType } from '../shared/command-gateway/command-gateway-actions.service';
import { CommandType } from '../inventur/command-type';
import { QueryGatewayActionType } from '../shared/query-gateway/query-gateway-actions.service';
import { QueryType } from '../shared/query-gateway/query-type';

export function inventurReducer(state: InventurState = INVENTUR_INITIAL_STATE, action): InventurState {
    switch (action.type) {
        case CommandGatewayActionType.gelungen: {
            console.info(`REDUCE INVENTAR ${action.type}: ${action.message.type}`);
            switch (action.message.type) {
                case CommandType.BeginneInventur: {
                    return {...state, inventurId: action.message.payload.id}
                }
                // case CommandType.ErfasseInventar: {
                //     return {...state, inventar: null}
                // }
            }
        }
        case QueryGatewayActionType.gelungen: {
            console.info(`REDUCE INVENTAR ${action.type}: ${action.message.type}`);
            switch (action.message.type) {
                case QueryType.LeseInventar: {
                    console.info(`Ergebnis: ${JSON.stringify(action)}`);
                    return {...state, inventar: action.response.body}
                }
            }
        }
        case QueryGatewayActionType.fehlgeschlagen: {
            console.info(`REDUCE INVENTAR ${action.type}: ${action.message.type}`);
            switch (action.message.type) {
                case QueryType.LeseInventar: {
                    return {...state, inventar: null}
                }
            }
        }
    }
    return state;
}