import { Inventar } from '../inventur/inventar';
import { COMMAND_BUS_INITIAL_STATE, CommandBusState } from '../shared/command-bus.model';

export interface AppState {
    inventur: InventurState
    command: CommandBusState
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
    command: COMMAND_BUS_INITIAL_STATE
};

export class Store {
}
