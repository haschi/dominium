import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommandGatewayActionsService } from './command-gateway-actions.service';
import { CommandGatewayEpicsService } from './command-gateway-epics.service';
import { CommandGatewayService } from './command-gateway.service';
import { IdGeneratorService } from './id-generator.service';
import { CommandProgressComponent } from './command-progress/command-progress.component';
import { AppMaterialModule } from '../app-material-module';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
    providers: [
        CommandGatewayActionsService,
        CommandGatewayEpicsService,
        CommandGatewayService,
        IdGeneratorService
    ],
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        AppMaterialModule
    ],
    declarations: [CommandProgressComponent],
    exports: [CommandProgressComponent]
})
export class CommandGatewayModule {
}
