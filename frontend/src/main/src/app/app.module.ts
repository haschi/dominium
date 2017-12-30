import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { DEMO_APP_ROUTES } from './routes';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { AppMaterialModule } from './shared/app-material-module';
import { AppCovalentModuleModule } from './shared/app-covalent-module.module';
import { InventurModule } from './inventur/inventur.module';
import { NgReduxModule } from '@angular-redux/store';
import { StoreModule } from './store/store.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommandGatewayModule } from './shared/command-gateway/command-gateway.module';
import { QueryGatewayModule } from './shared/query-gateway/query-gateway.module';

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppMaterialModule,
        AppCovalentModuleModule,
        RouterModule.forRoot(DEMO_APP_ROUTES, {enableTracing: false}),
        InventurModule,
        NgReduxModule,
        StoreModule,
        CommandGatewayModule,
        QueryGatewayModule
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
