import { MyAction } from '../reducer';
import { JobActions } from './jobs.actions';

export const JOB_INIT_STATE: JobState = {
    location: null,
    loading: false,
    error: null,
    redirect: null
};

export class JobState {
    location: string;
    loading: boolean;
    error?: any;
    redirect?: string;
}

export function jobs(state: JobState = JOB_INIT_STATE,
                     action: MyAction): JobState {
    switch (action.type) {
        case JobActions.JOB_ERSTELLT:
            return {location: action.payload.location, loading: true, error: null, redirect: null};

        case JobActions.JOB_FEHLGESCHLAGEN:
            return Object.assign({}, state, {loading: false, error: action.payload.error});

        case JobActions.JOB_BEENDET:
            return Object.assign({}, state, {loading: false, redirect: action.payload.redirect});

        default:
            return state;
    }
}
