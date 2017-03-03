import { MyAction } from '../reducer';

export const JOB_ERSTELLT = 'JOB_ERSTELLT';
export const JOB_FEHLGESCHLAGEN = 'JOB_FEHLGESCHLAGEN';
export const JOB_BEENDET = 'JOB_BEENDET';

export const JOB_INIT_STATE: JobState = {
    neu: null,
    laufend: null,
    beendet: null,
    fehler: null,
    alle: []
};

export interface FehlgeschlagenerJob {
    job: string;
    grund: any;
}

export interface BeendeterJob {
    job: string;
    location: string;
}

export interface Job {
    location: string;
    status: string;
}

export class JobState {
    neu: string;
    laufend: string;
    beendet: BeendeterJob;
    fehler: FehlgeschlagenerJob;
    alle: Job[];
}

export function jobs(state: JobState = JOB_INIT_STATE,
                     action: MyAction): JobState {
    switch (action.type) {
        case JOB_ERSTELLT:
            return Object.assign(
                {},
                state,
                {
                    alle: [...state.alle, {
                        location: action.payload.location,
                        status: 'gestartet'
                    }],
                    neu: action.payload.location});
        case JOB_FEHLGESCHLAGEN:
            return Object.assign(
                {},
                state,
                {
                    fehler: action.payload,
                    alle: state.alle.map(j => {
                        if (j.location === action.payload) {
                            return Object.assign({}, j, {status: 'fehler'});
                        } else {
                            return j;
                        }
                    })
                }
            );
        case JOB_BEENDET:
            return Object.assign(
                {},
                state,
                {
                    beendet: action.payload,
                    alle: state.alle.map(j => {
                        if (j.location === action.payload) {
                            return Object.assign({}, j, {status: 'beendet'});
                        } else {
                            return j;
                        }
                    })
                }
            );
        default:
            return state;
    }
}
