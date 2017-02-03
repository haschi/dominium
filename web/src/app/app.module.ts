import {NgModule, ApplicationRef, OnInit} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RouterModule, PreloadAllModules, Router} from "@angular/router";
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
import {INIT_STATE, rootReducer, AppState, VerbindungState} from "./reducer";
import {MaterializeModule} from "angular2-materialize";
import {LeererInhaltComponent} from "./leerer-inhalt/leerer-inhalt.component";

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
        LeererInhaltComponent,
        XLarge
    ],
    imports: [ // import Angular's modules
        MaterializeModule,
        NgReduxModule,
        BrowserModule,
        ReactiveFormsModule,
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
    constructor(public appRef: ApplicationRef, private reduxStore: NgRedux<AppState>, private router: Router) {
        console.info("App Module erzeugt.");
        this.reduxStore.configureStore(rootReducer, INIT_STATE);
    }

    ngOnInit(): void {
        console.info("AppModule initialisiert");
        // this.reduxStore.select(s => s.verbindung)
        //     .subscribe((verbindung: VerbindungState) => {
        //         if(verbindung.nachricht === true)
        //             this.router.navigate(['leer'])
        //     });
    }
}

