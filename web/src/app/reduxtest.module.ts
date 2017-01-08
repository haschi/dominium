
import {NgZone, NgModule} from "@angular/core";
import {INIT_STATE, rootReducer} from "./reducer";
import {NgRedux, NgReduxModule} from "ng2-redux";
import {Aktionen} from "./Aktionen";

@NgModule({
    imports: [NgReduxModule],
    providers: [
        Aktionen,
        {
            provide: NgRedux,
            useFactory: (zone: NgZone) => {
                let ngRedux = new NgRedux(zone);
                ngRedux.configureStore(rootReducer, INIT_STATE);
                return ngRedux;
            },
            deps: [NgZone]
        },
    ]
})
export class ReduxTestModule {
}
