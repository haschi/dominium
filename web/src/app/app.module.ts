import { NgModule, OnInit } from '@angular/core';
import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { CovalentLayoutModule, CovalentCoreModule } from '@covalent/core';
import { FlexLayoutModule } from '@angular/flex-layout';
/*
 * Platform and Environment providers/directives/pipes
 */
// App is our top level component
import { AppComponent } from './app.component';
import { HomeComponent } from './home';
import { AboutComponent } from './about';
import { NoContentComponent } from './no-content';
import { XLargeDirective } from './home/x-large';
import { Aktionen } from './Aktionen';
import { INIT_STATE, rootReducer, AppState } from './reducer';
import { LeererInhaltComponent } from './leerer-inhalt/leerer-inhalt.component';
import { JobService } from './shared/job.service';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
    bootstrap: [AppComponent],
    declarations: [
        AppComponent,
        AboutComponent,
        HomeComponent,
        NoContentComponent,
        LeererInhaltComponent,
        XLargeDirective
    ],
    imports: [ // import Angular's modules
        NgReduxModule,
        BrowserModule,
        ReactiveFormsModule,
        HttpModule,
        AppRoutingModule,
        FlexLayoutModule,
        CovalentLayoutModule.forRoot(),
        CovalentCoreModule.forRoot()
    ],
    providers: [
        JobService,
        Aktionen,
    ]
})
export class AppModule implements OnInit {
    constructor(private store: NgRedux<AppState>) {
        this.store.configureStore(rootReducer, INIT_STATE);
    }

    ngOnInit(): void {
    }
}

