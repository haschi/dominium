import {combineReducers, Reducer} from "redux";

export interface AppState {
    konfiguration: KonfigurationState
}

export const KONFIGURATION_INIT_STATE: KonfigurationState = {
    name: "Nicht Benannt",
    build: null,
    _links: []
};

export const INIT_STATE: AppState = {
    konfiguration: KONFIGURATION_INIT_STATE
};

export class KonfigurationState {
    name: String;
    build: number;
    _links: any[]
}


export const rootReducer: Reducer<AppState> = combineReducers<AppState>({
    konfiguration: konfigurationReducer
});

export const AKTION = {
    LADEN: "LADEN"
};

export function konfigurationReducer(state: KonfigurationState = KONFIGURATION_INIT_STATE, action): KonfigurationState {
    switch (action.type) {

        case "LADEN":
            return Object.assign({}, state, action.payload);

        default:
            return state;
    }
}
