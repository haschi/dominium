import { Injectable } from '@angular/core';
import { AppState } from './reducer';
import { NgRedux } from '@angular-redux/store';
import { JOB_ERSTELLT, JOB_BEENDET, JOB_FEHLGESCHLAGEN } from './shared/jobs.redux';

@Injectable()
export class Ereignis
{
    constructor(private store: NgRedux<AppState>) {}

    jobGestartet(location: string) {
        this.store.dispatch({
            type: JOB_ERSTELLT,
            payload: {
                location: location,
            }});
    }

    jobBeendet(job: string, location: string) {
        this.store.dispatch({
            type: JOB_BEENDET,
            payload: {
                job: job,
                location: location,
            }});
    }

    jobFehlgeschlagen(job: string, err: any) {
        this.store.dispatch({
            type: JOB_FEHLGESCHLAGEN,
            payload: {job: job, grund: err}
        });
    }
}
