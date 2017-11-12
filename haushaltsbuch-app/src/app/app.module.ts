import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { DEMO_APP_ROUTES } from './routes';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { AppMaterialModule } from './shared/app-material-module';
import { HttpModule } from '@angular/http';
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
    bootstrap: [AppComponent]
})
export class AppModule {
}
