import { QueryGatewayActionType } from '../shared/query-gateway/query-reducers';
import { INVENTUR_INITIAL_STATE, InventurState } from './model';
import { CommandGatewayActionType } from '../shared/command-gateway/command-gateway-actions.service';
import { CommandType } from '../inventur/command-type';
import { QueryType } from '../inventur/query-type';

export function inventurReducer(state: InventurState = INVENTUR_INITIAL_STATE, action): InventurState {
    switch (action.type) {
        case CommandGatewayActionType.gelungen: {
            switch (action.message.type) {
                case CommandType.BeginneInventur: {
                    return {...INVENTUR_INITIAL_STATE, inventurId: action.message.payload.id}
                }
            }
        }
        case QueryGatewayActionType.gelungen: {
            switch (action.message.type) {
                case QueryType.LeseInventar: {
                    return {...state, inventar: action.response.body}
                }
                case QueryType.LeseEroeffnungsbilanz: {
                    return {...state, eroeffnungsbilanz: action.response.body}
                }
            }
        }
        case QueryGatewayActionType.fehlgeschlagen: {
            switch (action.message.type) {
                case QueryType.LeseInventar: {
                    return {...state, inventar: null}
                }
            }
        }
    }
    return state;
}