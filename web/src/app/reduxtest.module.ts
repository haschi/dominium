import { NgZone, NgModule } from '@angular/core';
import { INIT_STATE, rootReducer } from './reducer';
import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { Aktionen } from './Aktionen';
import * as createLogger from 'redux-logger';
import { combineEpics, createEpicMiddleware } from 'redux-observable';
import { JobEpics } from './shared/jobs.epics';

@NgModule({
    imports: [NgReduxModule],
    providers: [
        Aktionen,
        JobEpics,
        {
            provide: NgRedux,
            useFactory: (zone: NgZone, jobEpics: JobEpics) => {
                let ngRedux = new NgRedux(zone);
                ngRedux.configureStore(rootReducer, INIT_STATE, [
                    createLogger(),
                    createEpicMiddleware(combineEpics(...jobEpics.epics))
                ]);
                return ngRedux;
            },
            deps: [NgZone, JobEpics]
        },
    ]
})
export class ReduxTestModule {
}
