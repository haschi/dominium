import { Injectable } from '@angular/core';

@Injectable()
export class JobActions {
    static readonly JOB_ERSTELLT = 'JOB_ERSTELLT';
    static readonly JOB_GESTARTET = 'JOB_GESTARTET';
    static readonly JOB_BEENDET = 'JOB_BEENDET';
    static readonly JOB_FEHLGESCHLAGEN = 'JOB_FEHLGESCHLAGEN';

    static jobErstellt(location: string) {
        return {
            type: JobActions.JOB_ERSTELLT,
            payload: {location: location}
        };
    }

    static jobGestartet(location: string) {
        return {
            type: JobActions.JOB_GESTARTET,
            payload: {location: location}
        };
    }

    static jobBeendet(redirect: string) {
        return {
            type: JobActions.JOB_BEENDET,
            payload: {redirect: redirect}
        };
    }

    static jobFehlgeschlagen(err: any) {
        return {
            type: JobActions.JOB_FEHLGESCHLAGEN,
            payload: {error: err}
        };
    };
}


