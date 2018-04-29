import { Eroeffnungsbilanz } from '../inventur/bilanz/bilanz.model';
import { Inventar } from '../inventur/inventar';
import {
    COMMAND_GATEWAY_INITIAL_STATE,
    CommandGatewayState
} from '../shared/command-gateway/command-gateway.model';
import {
    QUERY_GATEWAY_INITIAL_STATE,
    QueryGatewayState
} from '../shared/query-gateway/query.redux';

export interface AppState {
    inventur: InventurState
    command: CommandGatewayState,
    query: QueryGatewayState
}

export interface InventurState {
    inventurId: string
    inventar: Inventar
    eroeffnungsbilanz: Eroeffnungsbilanz | null
}

export const INVENTUR_INITIAL_STATE: InventurState = {
    inventurId: "",
    inventar: {
        anlagevermoegen: [],
        umlaufvermoegen: [],
        schulden: [],
        reinvermoegen: {
            summeDerSchulden: '',
            summeDesVermoegens: '',
            reinvermoegen: ''
        }
    },
    eroeffnungsbilanz: null
};

export const APP_INITIAL_STATE: AppState = {
    inventur: INVENTUR_INITIAL_STATE,
    command: COMMAND_GATEWAY_INITIAL_STATE,
    query: QUERY_GATEWAY_INITIAL_STATE
};
