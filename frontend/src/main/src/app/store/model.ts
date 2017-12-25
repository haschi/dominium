import { Inventar } from '../inventur/inventar';

export interface AppState {
    inventur: InventurState
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
    inventur: INVENTUR_INITIAL_STATE
};

export class Store {
}
