import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommandBusActionsService } from './command-bus-actions.service';
import { CommandBusEpicsService } from './command-bus-epics.service';
import { CommandGatewayService } from './command-gateway.service';

@NgModule({
    providers: [CommandBusActionsService, CommandBusEpicsService, CommandGatewayService],
    imports: [
        CommonModule
    ],
    declarations: []
})
export class CommandGatewayModule {
}
