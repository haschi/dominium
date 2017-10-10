import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { EventBusService } from './shared/event-bus.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { ALL_ROUTES } from './routes';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { AppMaterialModule } from './app-material-module';

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppMaterialModule,
        RouterModule.forRoot(ALL_ROUTES),
    ],
    providers: [EventBusService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
