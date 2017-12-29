import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QueryGatewayActionsService } from './query-gateway-actions.service';
import { QueryGatewayService } from './query-gateway.service';

@NgModule({
    exports: [
        QueryGatewayActionsService,
        QueryGatewayService
    ],
    imports: [
        CommonModule
    ],
    declarations: []
})
export class QueryGatewayModule {
}
