import { Injectable } from '@angular/core';
import { AppState, AKTION } from './reducer';
import { NgRedux } from '@angular-redux/store';

@Injectable()
export class Ereignis
{
    constructor(private store: NgRedux<AppState>) {}

    jobGestartet(location: string) {
        this.store.dispatch({
            type: AKTION.JOB_ERSTELLT,
            payload: {
                location: location,
            }});
    }
}
