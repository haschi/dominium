import { Inventar } from '../inventur/inventar';
import {
    COMMAND_GATEWAY_INITIAL_STATE,
    CommandGatewayState
} from '../shared/command-gateway/command-gateway.model';

export interface AppState {
    inventur: InventurState
    command: CommandGatewayState
}

export interface InventurState {
    inventurId: string
    inventar: Inventar
}

export const INVENTUR_INITIAL_STATE: InventurState = {
    inventurId: "",
    inventar: {
        anlagevermoegen: [],
        umlaufvermoegen: [],
        schulden: []
    }
};

export const APP_INITIAL_STATE: AppState = {
    inventur: INVENTUR_INITIAL_STATE,
    command: COMMAND_GATEWAY_INITIAL_STATE
};
