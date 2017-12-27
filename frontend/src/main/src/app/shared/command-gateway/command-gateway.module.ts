import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommandGatewayActionsService } from './command-gateway-actions.service';
import { CommandGatewayEpicsService } from './command-gateway-epics.service';
import { CommandGatewayService } from './command-gateway.service';

@NgModule({
    providers: [CommandGatewayActionsService, CommandGatewayEpicsService, CommandGatewayService],
    imports: [
        CommonModule
    ],
    declarations: []
})
export class CommandGatewayModule {
}
