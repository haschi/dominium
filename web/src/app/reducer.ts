import {combineReducers, Reducer, Action} from "redux";

export class AppState {
    konfiguration: KonfigurationState;
    verbindung: VerbindungState;
}

export const KONFIGURATION_INIT_STATE: KonfigurationState = {
    name: "Nicht Benannt",
    version: null,
    _links: []
};

export const VERBINDUNG_INIT_STATE: VerbindungState = {
    nachricht: null,
    route: null,
    kompensation: []
};

export const INIT_STATE: AppState = {
    konfiguration: KONFIGURATION_INIT_STATE,
    verbindung: VERBINDUNG_INIT_STATE
};

export class KonfigurationState {
    name: String;
    version: number;
    _links: any[]
}

export class VerbindungState {
    nachricht: string;
    route: string;
    kompensation: Kompensation[];
}

export class Kompensation {
    titel: string;
    aktion: any;
}

export const rootReducer: Reducer<AppState> = combineReducers<AppState>({
    konfiguration: konfigurationReducer,
    verbindung: verbindungReducer
});

export const AKTION = {
    LADEN: "LADEN",
    OFFLINE_GEHEN: "OFFLINE_GEHEN",
    FEHLER: "FEHLER"
};

export class MyAction implements Action {
    type: any;
    payload: any;
}

export function konfigurationReducer(state: KonfigurationState = KONFIGURATION_INIT_STATE, action: MyAction): KonfigurationState {

    switch (action.type) {

        case "LADEN":
            return Object.assign({}, state, action.payload);

        default:
            return state;
    }
}

export function verbindungReducer(state: VerbindungState = VERBINDUNG_INIT_STATE, action: MyAction) : VerbindungState {
    switch (action.type) {
        case "OFFLINE_GEHEN":
            return Object.assign({}, state, action.payload);
        case "FEHLER":
            return Object.assign({}, state, action.payload);
        default:
            return state;
    }
}
