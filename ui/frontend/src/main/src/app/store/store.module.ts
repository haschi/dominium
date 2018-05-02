import { Inject, InjectionToken, NgModule, Optional } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DevToolsExtension, NgRedux, NgReduxModule } from '@angular-redux/store';
import { Action } from 'redux';
import { createEpicMiddleware, Epic, EpicMiddleware } from 'redux-observable';
import { CommandGatewayModule } from '../shared/command-gateway/command-gateway.module';
import { EpicProvider, REDUX_EPIC } from '../shared/redux-utils/provider';
import { QueryGatewayModule } from '../shared/query-gateway/query-gateway.module';
import { QueryGatewayService } from '../shared/query-gateway/query-gateway.service';
import {
    fallsCommandInventurBegonnenGelungen, fallsInventarLesenFehlgeschlagen,
    fallsQueryEroeffnungsbilanzGelesenGelungen,
    fallsQueryInventarGelesenGelungen
} from '../inventur/shared/inventur.redux';
import { APP_INITIAL_STATE, AppState } from './model';
import { rootReducer } from './reducers';

@NgModule({
    imports: [CommonModule, NgReduxModule, QueryGatewayModule, CommandGatewayModule],
    exports: [NgReduxModule],

    declarations: []
})
export class StoreModule {
    constructor(store: NgRedux<AppState>,
                @Inject(REDUX_EPIC) @Optional() epics: Epic<Action, {}>[] = [],
                devTools: DevToolsExtension) {


        const middleware = epics.map(epic => createEpicMiddleware(epic))

        store.configureStore(
            rootReducer,
            APP_INITIAL_STATE,
            [...middleware],
            devTools.isEnabled() ? [devTools.enhancer()] : [])
    }
}
