import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DevToolsExtension, NgRedux } from '@angular-redux/store';
import { APP_INITIAL_STATE, AppState } from './model';
import { rootReducer } from './reducers';

@NgModule({
    imports: [CommonModule],
    declarations: []
})
export class StoreModule {
    constructor(store: NgRedux<AppState>,
                devTools: DevToolsExtension) {
        console.info("Redux Store initialisieren");
        store.configureStore(
            rootReducer,
            APP_INITIAL_STATE,
            [],
            devTools.isEnabled() ? [devTools.enhancer()] : [])
    }
}
