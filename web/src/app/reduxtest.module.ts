import { NgZone, NgModule } from '@angular/core';
import { INIT_STATE, rootReducer } from './reducer';
import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { Aktionen } from './Aktionen';
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
                const ngRedux = new NgRedux(zone);
                ngRedux.configureStore(rootReducer, INIT_STATE, [
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
