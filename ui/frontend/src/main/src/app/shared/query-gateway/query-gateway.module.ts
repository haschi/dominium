import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QueryGatewayService } from './query-gateway.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CovalentDialogsModule } from '@covalent/core';
import { AppMaterialModule } from '../app-material-module';
import { QueryProgressComponent } from './query-progress/query-progress.component';
import { QueryErrorComponent } from './query-error/query-error.component';
import { QueryEpicProvider } from './query.redux';

@NgModule({
    providers: [QueryGatewayService, QueryEpicProvider],
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        AppMaterialModule,
        CovalentDialogsModule
    ],
    declarations: [QueryProgressComponent, QueryErrorComponent],
    exports: [QueryProgressComponent, QueryErrorComponent]
})
export class QueryGatewayModule {
}
