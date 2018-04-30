import { Inject, InjectionToken, NgModule, Optional } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DevToolsExtension, NgRedux, NgReduxModule } from '@angular-redux/store';
import { Action } from 'redux';
import { createEpicMiddleware, Epic, EpicMiddleware } from 'redux-observable';
import { REDUX_EPIC } from '../shared/redux-utils/provider';
import { QueryGatewayModule } from '../shared/query-gateway/query-gateway.module';
import { QueryGatewayService } from '../shared/query-gateway/query-gateway.service';
import { APP_INITIAL_STATE, AppState } from './model';
import { rootReducer } from './reducers';
import { RootEpicsService } from './root-epics.service';

@NgModule({
    imports: [CommonModule, NgReduxModule, QueryGatewayModule],
    exports: [NgReduxModule],
    providers: [RootEpicsService],
    declarations: []
})
export class StoreModule {
    constructor(store: NgRedux<AppState>,
                rootEpics: RootEpicsService,
                @Inject(REDUX_EPIC) @Optional() epics: Epic<Action, {}>[] = [],
                devTools: DevToolsExtension) {


        const middleware = epics.map(epic => createEpicMiddleware(epic))

        store.configureStore(
            rootReducer,
            APP_INITIAL_STATE,
            [...rootEpics.createEpics(), ...middleware],
            devTools.isEnabled() ? [devTools.enhancer()] : [])
    }
}
