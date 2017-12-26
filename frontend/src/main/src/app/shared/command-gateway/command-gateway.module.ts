import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommandBusActionsService } from './command-bus-actions.service';
import { CommandBusEpicsService } from './command-bus-epics.service';
import { CommandBusService } from './command-bus.service';

@NgModule({
    providers: [CommandBusActionsService, CommandBusEpicsService, CommandBusService],
    imports: [
        CommonModule
    ],
    declarations: []
})
export class CommandGatewayModule {
}
