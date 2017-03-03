import { JOB_ERSTELLT, JOB_BEENDET, JOB_FEHLGESCHLAGEN } from './jobs.redux';

export function jobGestartet(location: string) {
    return {
        type: JOB_ERSTELLT,
        payload: {
            location: location,
        }};
}

export function jobBeendet(job: string, location: string) {
    return {
        type: JOB_BEENDET,
        payload: {
            job: job,
            location: location,
        }};
}

export function jobFehlgeschlagen(job: string, err: any) {
    return {
        type: JOB_FEHLGESCHLAGEN,
        payload: {job: job, grund: err}
    };
}

