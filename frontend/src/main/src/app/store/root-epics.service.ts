import { Injectable } from '@angular/core';
import { CommandGatewayEpicsService } from '../shared/command-gateway/command-gateway-epics.service';
import { QueryGatewayEpicsService } from '../shared/query-gateway/query-gateway-epics.service';

@Injectable()
export class RootEpicsService {

    constructor(
        private commandBusEpics: CommandGatewayEpicsService,
        private queryGatewayEpics: QueryGatewayEpicsService) {
    }

    public createEpics() {
        return [
            ...this.commandBusEpics.createEpic(),
            ...this.queryGatewayEpics.createEpic()
        ];
    }
}
