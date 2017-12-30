import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QueryGatewayActionsService } from './query-gateway-actions.service';
import { QueryGatewayService } from './query-gateway.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CovalentDialogsModule } from '@covalent/core';
import { AppMaterialModule } from '../app-material-module';

@NgModule({
    providers: [
        QueryGatewayActionsService,
        QueryGatewayService
    ],
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        AppMaterialModule,
        CovalentDialogsModule
    ],
    declarations: []
})
export class QueryGatewayModule {
}
