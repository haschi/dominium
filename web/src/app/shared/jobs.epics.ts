import { Injectable } from '@angular/core';
import { Action, Store } from 'redux';
import { Epic } from 'redux-observable';
import { JobActions } from './jobs.actions';
import { JobService, RunningJob } from './job.service';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { of } from 'rxjs/observable/of';

@Injectable()
export class JobEpics {
    epics: Epic<Action, Store<any>>[];

    constructor(private service: JobService) {
        this.epics = [ this.fallsJobErstellt ];
    }

    // So weiter machen: https://redux-observable.js.org/docs/basics/Epics.html
    fallsJobErstellt = action$ => action$
        .ofType(JobActions.JOB_ERSTELLT)
        .switchMap(a => {
            return this.service.poll(a.payload.location)
                .map((r: RunningJob) =>
                    JobActions.jobBeendet(r.response.headers.get('Location')))
                .catch(response => of(JobActions.jobFehlgeschlagen(response.message)));
        })
}
