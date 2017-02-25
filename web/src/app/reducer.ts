import { combineReducers, Reducer, Action } from 'redux';

export class AppState {
    konfiguration: KonfigurationState;
    verbindung: VerbindungState;
    haushaltsbuch: HaushaltsbuchState;
    job: JobState;
}

export const KONFIGURATION_INIT_STATE: KonfigurationState = {
    name: 'Nicht Benannt',
    version: null,
    _links: []
};

export const VERBINDUNG_INIT_STATE: VerbindungState = {
    nachricht: null,
    route: null,
    kompensation: []
};

export const HAUSHALTSBUCH_INIT_STATE: HaushaltsbuchState = {
    location: null,
    id: null
};
export const JOB_INIT_STATE: JobState = {location: null};
export class JobState {
    location: string;
}

export const INIT_STATE: AppState = {
    konfiguration: KONFIGURATION_INIT_STATE,
    verbindung: VERBINDUNG_INIT_STATE,
    haushaltsbuch: HAUSHALTSBUCH_INIT_STATE,
    job: JOB_INIT_STATE
};

export class HaushaltsbuchState {
    location: string;
    id: any;
}


export class KonfigurationState {
    name: String;
    version: number;
    _links: any[];
}

export class VerbindungState {
    nachricht: string;
    route: any[];
    kompensation: Kompensation[];
}

export class Kompensation {
    titel: string;
    aktion: any;
}

export const rootReducer: Reducer<AppState> = combineReducers<AppState>({
    konfiguration: konfigurationReducer,
    verbindung: verbindungReducer,
    haushaltsbuch: hauhaltsbuchReducer,
    job: jobReducer
});

export const AKTION = {
    LADEN: 'LADEN',
    OFFLINE_GEHEN: 'OFFLINE_GEHEN',
    FEHLER: 'FEHLER',
    HAUSHALTSBUCH_ERSTELLT: 'HAUSHALTSBUCH_ERSTELLT',
    JOB_ERSTELLT: 'JOB_ERSTELLT'
};

export class MyAction implements Action {
    type: any;
    payload: any;
}

export function konfigurationReducer(
    state: KonfigurationState = KONFIGURATION_INIT_STATE,
    action: MyAction): KonfigurationState {

    switch (action.type) {

        case 'LADEN':
            return Object.assign({}, state, action.payload);

        default:
            return state;
    }
}

export function verbindungReducer(
    state: VerbindungState = VERBINDUNG_INIT_STATE,
    action: MyAction): VerbindungState {
    switch (action.type) {
        case 'OFFLINE_GEHEN':
            return Object.assign({}, state, action.payload);
        case 'FEHLER':
            return Object.assign({}, state, action.payload);
        default:
            return state;
    }
}

export function hauhaltsbuchReducer(
    state: HaushaltsbuchState = HAUSHALTSBUCH_INIT_STATE,
    action: MyAction): HaushaltsbuchState {
    switch (action.type) {
        case 'HAUSHALTSBUCH_ERSTELLT':
            return Object.assign({}, state, {location: action.payload});
        default:
            return state;
    }
}

export function jobReducer(
    state: JobState = JOB_INIT_STATE,
    action: MyAction): JobState {
        console.info("jobReducer: " + action.type);
    switch (action.type) {
        case 'JOB_ERSTELLT':
            return Object.assign({}, state, action.payload);
        default:
            return state;
    }
}
