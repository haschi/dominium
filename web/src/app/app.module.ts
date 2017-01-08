import {NgModule, ApplicationRef, OnInit} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RouterModule, PreloadAllModules} from "@angular/router";
import {removeNgStyles, createNewHosts, createInputTransfer} from "@angularclass/hmr";
/*
 * Platform and Environment providers/directives/pipes
 */
import {ENV_PROVIDERS} from "./environment";
import {ROUTES} from "./app.routes";
// App is our top level component
import {AppComponent} from "./app.component";
import {APP_RESOLVER_PROVIDERS} from "./app.resolver";
import {HomeComponent} from "./home";
import {AboutComponent} from "./about";
import {NoContentComponent} from "./no-content";
import {XLarge} from "./home/x-large";
import {Aktionen} from "./Aktionen";
import {NgRedux, NgReduxModule} from "ng2-redux";
import {INIT_STATE, rootReducer, AppState} from "./reducer";

// Application wide providers
const APP_PROVIDERS = [
    ...APP_RESOLVER_PROVIDERS,
];

/**
 * `AppModule` is the main entry point into Angular2's bootstraping process
 */
@NgModule({
    bootstrap: [AppComponent],
    declarations: [
        AppComponent,
        AboutComponent,
        HomeComponent,
        NoContentComponent,
        XLarge
    ],
    imports: [ // import Angular's modules
        NgReduxModule,
        BrowserModule,
        FormsModule,
        HttpModule,
        RouterModule.forRoot(ROUTES, {useHash: true, preloadingStrategy: PreloadAllModules})
    ],
    providers: [ // expose our Services and Providers into Angular's dependency injection
        Aktionen,
        ENV_PROVIDERS,
        APP_PROVIDERS
    ]
})
export class AppModule implements OnInit {
    constructor(public appRef: ApplicationRef, private reduxStore: NgRedux<AppState>) {
        console.info("App Module erzeugt.");
        this.reduxStore.configureStore(rootReducer, INIT_STATE);
    }

    ngOnInit(): void {
        console.info("AppModule initialisiert");
    }
}

