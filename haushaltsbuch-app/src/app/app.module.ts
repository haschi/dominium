import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { EventBusService } from './shared/event-bus.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { ALL_ROUTES, DEMO_APP_ROUTES } from './routes';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { AppMaterialModule } from './shared/app-material-module';
import { HttpModule } from '@angular/http';
import { InventurComponent } from './inventur/inventur.component';
import { AppCovalentModuleModule } from './shared/app-covalent-module.module';
import { InventurModule } from './inventur/inventur.module';

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        HttpModule,
        AppMaterialModule,
        AppCovalentModuleModule,
        RouterModule.forRoot(DEMO_APP_ROUTES, {enableTracing: true}),
        InventurModule
    ],
    providers: [EventBusService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
