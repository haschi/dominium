import { INVENTUR_INITIAL_STATE, InventurState } from '../inventur/inventur.redux';
import {
    COMMAND_GATEWAY_INITIAL_STATE,
    CommandGatewayState
} from '../shared/command-gateway/command.redux';
import {
    QUERY_GATEWAY_INITIAL_STATE,
    QueryGatewayState
} from '../shared/query-gateway/query.redux';

export interface AppState {
    inventur: InventurState
    command: CommandGatewayState,
    query: QueryGatewayState
}

export const APP_INITIAL_STATE: AppState = {
    inventur: INVENTUR_INITIAL_STATE,
    command: COMMAND_GATEWAY_INITIAL_STATE,
    query: QUERY_GATEWAY_INITIAL_STATE
};
