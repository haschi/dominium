import { GruppenState, INVENTURGRUPPEN_INITIAL_STATE } from '../inventur/shared/gruppen.redux';
import {
    INVENTUREINGBAE_INITIAL_STATE,
    InventurEingabeState
} from '../inventur/shared/inventar-eingabe.redux';
import { INVENTUR_INITIAL_STATE, InventurState } from '../inventur/shared/inventur.redux';
import {
    COMMAND_GATEWAY_INITIAL_STATE,
    CommandGatewayState
} from '../shared/command-gateway/command.redux';
import {
    QUERY_GATEWAY_INITIAL_STATE,
    QueryGatewayState
} from '../shared/query-gateway/query.redux';

export interface AppState {
    inventur: InventurState,
    inventurGruppen: GruppenState,
    command: CommandGatewayState,
    query: QueryGatewayState,
    inventureingabe: InventurEingabeState
}

export const APP_INITIAL_STATE: AppState = {
    inventur: INVENTUR_INITIAL_STATE,
    inventurGruppen: INVENTURGRUPPEN_INITIAL_STATE,
    command: COMMAND_GATEWAY_INITIAL_STATE,
    query: QUERY_GATEWAY_INITIAL_STATE,
    inventureingabe: INVENTUREINGBAE_INITIAL_STATE
};
